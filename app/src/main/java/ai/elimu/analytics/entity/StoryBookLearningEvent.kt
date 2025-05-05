package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class StoryBookLearningEvent : LearningEvent() {
    @JvmField
    var storyBookId: Long = 0L

    lateinit var learningEventType: LearningEventType
}
