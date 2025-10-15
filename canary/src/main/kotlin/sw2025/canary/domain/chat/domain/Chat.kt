package sw2025.canary.domain.chat.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import sw2025.canary.domain.bot.domain.Bot
import sw2025.canary.domain.user.domain.User

@Entity
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val user: User,
    @ManyToOne
    val bot: Bot,
)
