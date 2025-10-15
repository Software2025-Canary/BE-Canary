package sw2025.canary.domain.user.facade

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import sw2025.canary.domain.user.exception.UserNotFoundException
import sw2025.canary.domain.user.persistence.repository.UserRepository

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun currentUser() = userRepository.findById((SecurityContextHolder.getContext().authentication.name).toLong())
    fun findUserByIdOrThrow(userId: Long) = userRepository.findById(userId).orElseThrow{UserNotFoundException}
}