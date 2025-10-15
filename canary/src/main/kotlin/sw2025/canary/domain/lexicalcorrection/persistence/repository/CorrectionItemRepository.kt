package sw2025.canary.domain.lexicalcorrection.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.lexicalcorrection.domain.CorrectionItem

interface CorrectionItemRepository : CrudRepository<CorrectionItem, Long>
