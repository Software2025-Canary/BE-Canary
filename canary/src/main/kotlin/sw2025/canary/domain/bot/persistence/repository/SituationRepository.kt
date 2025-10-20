package sw2025.canary.domain.bot.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.bot.domain.Situation

interface SituationRepository : CrudRepository<Situation, Long>
