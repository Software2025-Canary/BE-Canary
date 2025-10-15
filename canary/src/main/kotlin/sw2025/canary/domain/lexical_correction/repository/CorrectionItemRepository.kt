package sw2025.canary.domain.lexical_correction.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.lexical_correction.domain.CorrectionItem

interface CorrectionItemRepository: CrudRepository<CorrectionItem, Long> {
}