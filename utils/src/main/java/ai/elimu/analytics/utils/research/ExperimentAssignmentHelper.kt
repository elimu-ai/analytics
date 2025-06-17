package ai.elimu.analytics.utils.research

import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment
import android.content.Context
import android.provider.Settings

object ExperimentAssignmentHelper {
    val CURRENT_EXPERIMENT = ResearchExperiment.EXP_0_WORD_EMOJIS

    fun getExperimentGroup(context: Context): ExperimentGroup {
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val hash = "${CURRENT_EXPERIMENT}_${deviceId}".hashCode()
        return if (Math.abs(hash) % 2 == 0) {
            ExperimentGroup.CONTROL
        } else {
            ExperimentGroup.TREATMENT
        }
    }
}
