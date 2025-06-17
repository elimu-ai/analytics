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

    var researchExperiment: ResearchExperiment? = null

    var experimentGroup: ExperimentGroup? = null
}
