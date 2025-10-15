package sw2025.canary.domain.lexical_correction.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.lexical_correction.domain.LexicalCorrection

interface LexicalCorrectionRepository: CrudRepository<LexicalCorrection, Long> {
}