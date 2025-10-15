package sw2025.canary.domain.message.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.message.domain.Message

interface MessageRepository: CrudRepository<Message, Long> {
}