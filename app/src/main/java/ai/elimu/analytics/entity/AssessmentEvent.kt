package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment
import androidx.room.Entity
import java.util.Calendar

@Entity
abstract class AssessmentEvent : BaseEntity() {
    @JvmField
    var androidId: String = ""

    @JvmField
    var packageName: String = ""

    lateinit var time: Calendar

    /**
     * Any additional data should be stored in the format of a JSON object.
     *
     * Example:
     * ```
     * {'word_ids_presented':[1,2,3],'word_id_selected':[2]}
     * ```
     */
    var additionalData: String? = null

    var researchExperiment: ResearchExperiment? = null

    var experimentGroup: ExperimentGroup? = null
}
