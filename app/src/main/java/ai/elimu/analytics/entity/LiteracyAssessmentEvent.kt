package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.content.LiteracySkill
import androidx.room.Entity

/**
 * An assessment of any type of literacy skill.
 */
@Entity
class LiteracyAssessmentEvent : AssessmentEvent() {
    lateinit var literacySkill: LiteracySkill
}
