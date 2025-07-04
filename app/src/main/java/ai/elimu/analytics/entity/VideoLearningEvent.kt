package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/VideoLearningEvent.java
 */
@Entity
class VideoLearningEvent : LearningEvent() {
    lateinit var videoTitle: String

    /**
     * This field might not be included, e.g. if the event occurred in a 3rd-party app that did not
     * load the content from the elimu.ai Content Provider. In that case, this field will be {@code null}.
     */
    var videoId: Long? = null

    @Deprecated("Will be replaced by `additionalData`")
    var learningEventType: LearningEventType? = null
}
