package sw2025.canary.domain.user.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.user.domain.User

interface UserRepository: CrudRepository<User, Long> {
}