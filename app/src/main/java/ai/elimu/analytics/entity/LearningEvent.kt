package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment
import androidx.room.Entity
import java.util.Calendar

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LearningEvent.java
 */
@Entity
abstract class LearningEvent : BaseEntity() {
    lateinit var androidId: String

    lateinit var packageName: String

    @JvmField
    var timestamp: Calendar = Calendar.getInstance()

    /**
     * Any additional data should be stored in the format of a JSON object.
     *
     * Example:
     * ```
     * {'video_playback_position_ms':27946}
     * ```
     */
    var additionalData: String? = null

    var researchExperiment: ResearchExperiment? = null

    var experimentGroup: ExperimentGroup? = null
}
