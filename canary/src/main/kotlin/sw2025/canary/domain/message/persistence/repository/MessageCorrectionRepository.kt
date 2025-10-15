package sw2025.canary.domain.message.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.message.domain.MessageCorrection

interface MessageCorrectionRepository: CrudRepository<MessageCorrection, Long> {
}