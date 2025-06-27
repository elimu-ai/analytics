package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class VideoLearningEvent : LearningEvent() {
    var videoId: Long? = null

    lateinit var videoTitle: String

    @Deprecated("Will be replaced by `additionalData`")
    var learningEventType: LearningEventType? = null
}
