package sw2025.canary.domain.chat.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.chat.domain.ChatReport

interface ChatReportRepository : CrudRepository<ChatReport, Long>
