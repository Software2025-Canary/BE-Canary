package sw2025.canary.domain.message.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import sw2025.canary.domain.chat.domain.Chat

@Entity
class MessageCorrection(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val chat: Chat,
    @ManyToOne
    val message: Message,
    val content: String,
)
