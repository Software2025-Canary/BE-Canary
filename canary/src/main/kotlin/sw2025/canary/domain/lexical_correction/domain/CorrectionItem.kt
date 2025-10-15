package sw2025.canary.domain.lexical_correction.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import sw2025.canary.domain.lexical_correction.enum.CorrectionType

@Entity
class CorrectionItem (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val lexicalCorrection: LexicalCorrection,
    val start: Int,
    val end: Int,
    val suggestion: String,
    val reason: String,
    val type: CorrectionType,
)