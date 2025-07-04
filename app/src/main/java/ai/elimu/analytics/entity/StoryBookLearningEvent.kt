package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/StoryBookLearningEvent.java
 */
@Entity
class StoryBookLearningEvent : LearningEvent() {
    lateinit var storyBookTitle: String

    var storyBookId: Long = 0L

    @Deprecated("Will be replaced by `additionalData`")
    var learningEventType: LearningEventType? = null
}
