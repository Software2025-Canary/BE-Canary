package sw2025.canary.domain.bot.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.bot.domain.Bot

interface BotRepository: CrudRepository<Bot, Long> {
}