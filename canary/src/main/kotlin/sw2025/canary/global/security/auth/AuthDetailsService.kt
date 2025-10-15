package sw2025.canary.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userFacadeUseCase: UserFacadeUseCase
) : UserDetailsService {
    override fun loadUserByUsername(accountId: String): UserDetails {
        val user = userFacadeUseCase.getUserByAccountId(accountId)
        return AuthDetails(user.id)
    }
}