package sw2025.canary.domain.lexicalcorrection.persistence.repository

import org.springframework.data.repository.CrudRepository
import sw2025.canary.domain.lexicalcorrection.domain.LexicalCorrection

interface LexicalCorrectionRepository : CrudRepository<LexicalCorrection, Long>
