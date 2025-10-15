package sw2025.canary.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import sw2025.canary.domain.user.facade.UserFacade

@Component
class AuthDetailsService(
    private val userFacade: UserFacade,
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val user = userFacade.findUserByIdOrThrow(id.toLong())
        return AuthDetails(user.id.toString())
    }
}
