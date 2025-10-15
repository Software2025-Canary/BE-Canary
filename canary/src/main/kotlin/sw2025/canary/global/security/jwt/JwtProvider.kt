package sw2025.canary.global.security.jwt

import io.jsonwebtoken.*
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

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(userId: String, role: String): TokenResponse {
        val accessToken = generateAccessToken(userId, ACCESS_KEY, jwtProperties.accessExp)
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
                val role = getRole(token.token)

                val tokenResponse = generateToken(id, role)
                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
            } ?: throw InvalidJwtException
    }

    fun getRole(token: String) = getJws(token).body["role"].toString()

    private fun isRefreshToken(token: String?): Boolean {
        return REFRESH_KEY == getJws(token!!).header["typ"].toString()
    }

    private fun generateAccessToken(id: String, type: String, exp: Long): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    private fun generateRefreshToken(type: String, exp: Long): String =
        Jwts.builder()
            .setHeaderParam("typ", type)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }

    fun authentication(token: String): Authentication? {
        val body: Claims = getJws(token).body
        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getClaimsToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parseClaimsJws(token)
            .body
    }

    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token)
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