package ai.elimu.analytics.entity

import androidx.room.Entity
import java.util.Calendar

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LearningEvent.java
 */
@Entity
abstract class LearningEvent : BaseEntity() {
    @JvmField
    var androidId: String = ""

    @JvmField
    var packageName: String = ""

    @JvmField
    var time: Calendar = Calendar.getInstance()

    /**
     * Any additional data should be stored in the format of a JSON object.
     *
     * Example:
     * ```
     * {'video_playback_position_ms':27946}
     * ```
     */
    var additionalData: String? = null
}
