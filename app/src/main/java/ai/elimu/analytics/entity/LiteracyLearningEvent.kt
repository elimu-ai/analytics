package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.content.LiteracySkill
import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LiteracyLearningEvent.java
 */
@Entity
class LiteracyLearningEvent : LearningEvent() {
    lateinit var literacySkill: LiteracySkill
}
