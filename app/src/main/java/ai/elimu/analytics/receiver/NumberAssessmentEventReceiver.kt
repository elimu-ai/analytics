package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.NumberAssessmentEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import timber.log.Timber
import java.util.Calendar

class NumberAssessmentEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val bundle = intent.extras
        bundle?.let {
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
                Timber.i("${key}: ${value}")
            }
        }

        val event = NumberAssessmentEvent()

        val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"${androidId}\"")
        event.androidId = androidId

        val packageName: String = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
        Timber.i("packageName: \"${packageName}\"")
        if (TextUtils.isEmpty(androidId)) {
            throw IllegalArgumentException("Missing packageName")
        }
        event.packageName = packageName

        val timestamp: Calendar = Calendar.getInstance()
        Timber.i("timestamp.time: ${timestamp.time}")
        event.time = timestamp

        val additionalData: String? = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
        Timber.i("additionalData: ${additionalData}")
        event.additionalData = additionalData

        val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
        Timber.i("researchExperiment: ${researchExperiment}")
        event.researchExperiment = researchExperiment

        val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
        Timber.i("experimentGroup: ${experimentGroup}")
        event.experimentGroup = experimentGroup

        val masteryScore: Float = intent.getFloatExtra(BundleKeys.KEY_MASTERY_SCORE, -1f)
        Timber.i("masteryScore: ${masteryScore}")
        if ((masteryScore < 0) || (masteryScore > 1)) {
            throw IllegalArgumentException("Invalid masteryScore. Must be in the range [0.0, 1.0]")
        }
        event.masteryScore = masteryScore

        val timeSpentMs: Long = intent.getLongExtra(BundleKeys.KEY_TIME_SPENT_MS, 0)
        Timber.i("timeSpentMs: ${timeSpentMs}")
        if (timeSpentMs <= 0) {
            throw IllegalArgumentException("Invalid timeSpentMs. Must be larger than 0")
        }
        event.timeSpentMs = timeSpentMs

        val numberValue: Int = intent.getIntExtra(BundleKeys.KEY_NUMBER_VALUE, Int.MIN_VALUE)
        Timber.i("numberValue: ${numberValue}")
        if (numberValue == Int.MIN_VALUE) {
            throw IllegalArgumentException("Missing numberValue")
        }
        event.numberValue = numberValue

        val numberId: Long = intent.getLongExtra(BundleKeys.KEY_NUMBER_ID, 0)
        Timber.i("numberId: ${numberId}")
        if (numberId > 0) {
            event.numberId = numberId
        }

        // Store the event in the database
        val roomDb = RoomDb.getDatabase(context)
        RoomDb.databaseWriteExecutor.execute {
            roomDb.numberAssessmentEventDao().insert(event)
        }
    }
}
