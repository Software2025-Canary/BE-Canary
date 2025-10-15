package sw2025.canary.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import sw2025.canary.global.security.auth.AuthDetailsService
import sw2025.canary.global.security.dto.TokenResponse
import sw2025.canary.global.security.jwt.exception.ExpiredTokenException
import sw2025.canary.global.security.jwt.exception.InvalidJwtException
import sw2025.canary.global.security.refresh.RefreshToken
import sw2025.canary.global.security.refresh.repository.RefreshTokenRepository
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(userId: Long): TokenResponse {
        val accessToken = generateAccessToken(userId.toString(), ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken = generateRefreshToken( REFRESH_KEY, jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(userId, refreshToken, jwtProperties.refreshExp)
        )
        return TokenResponse(accessToken, refreshToken)
    }

    fun reIssue(refreshToken: String): TokenResponse {
        if (!isRefreshToken(refreshToken)) {
            throw InvalidJwtException
        }

        refreshTokenRepository.findByToken(refreshToken)
            ?.let { token ->
                val id = token.id

                val tokenResponse = generateToken(id)
                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
            } ?: throw InvalidJwtException
    }

    private fun isRefreshToken(token: String?): Boolean {
        return REFRESH_KEY == getJws(token!!).get("type", String::class.java)
    }

    private fun generateAccessToken(id: String, type: String, exp: Long): String =
        Jwts.builder()
            .subject(id)
            .claim("type", type)
            .signWith(secretKey)
            .issuedAt(Date()) // 발행 시간 설정
            .expiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

    private fun generateRefreshToken(type: String, exp: Long): String =
        Jwts.builder()
            .claim("type", type)
            .signWith(secretKey)
            .issuedAt(Date()) // 발행 시간 설정
            .expiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length).trim()
            }
        }

    fun authentication(token: String): Authentication? {
        val userDetails: UserDetails = getDetails(getJws(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getJws(token: String): Claims {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredTokenException) {
            throw ExpiredTokenException
        } catch (e: Exception) {
            throw InvalidJwtException
        }
    }

    private fun getDetails(body: Claims): UserDetails {
        return authDetailsService.loadUserByUsername(body.subject)
    }
}