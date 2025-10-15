package sw2025.canary.global.security.refresh.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.global.security.refresh.RefreshToken

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByToken(token: String): RefreshToken?
}
