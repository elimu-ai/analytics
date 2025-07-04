package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/WordLearningEvent.java
 */
@Entity
class WordLearningEvent : LearningEvent() {
    lateinit var wordText: String

    var wordId: Long? = null

    @Deprecated("Will be replaced by `additionalData`")
    var learningEventType: LearningEventType? = null
}
