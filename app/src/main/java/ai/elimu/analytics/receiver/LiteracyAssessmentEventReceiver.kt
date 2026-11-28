package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LiteracyAssessmentEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.content.LiteracySkill
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import timber.log.Timber
import java.util.Calendar

class LiteracyAssessmentEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val bundle = intent.extras
        bundle?.let {
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
                Timber.i("${key}=${value}")
            }
        }

        try {
            val event = LiteracyAssessmentEvent()

            val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            event.androidId = androidId

            val packageName: String = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
            if (TextUtils.isEmpty(packageName)) {
                throw IllegalArgumentException("Missing packageName")
            }
            event.packageName = packageName

            val timestamp: Calendar = Calendar.getInstance()
            event.timestamp = timestamp

            val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
            event.researchExperiment = researchExperiment

            val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
            event.experimentGroup = experimentGroup

            val additionalData: String? = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
            event.additionalData = additionalData

            val masteryScore: Float = intent.getFloatExtra(BundleKeys.KEY_MASTERY_SCORE, -1f)
            if ((masteryScore < 0) || (masteryScore > 1)) {
                throw IllegalArgumentException("Invalid masteryScore. Must be in the range [0.0, 1.0]")
            }
            event.masteryScore = masteryScore

            val timeSpentMs: Long = intent.getLongExtra(BundleKeys.KEY_TIME_SPENT_MS, 0)
            if (timeSpentMs <= 0) {
                throw IllegalArgumentException("Invalid timeSpentMs. Must be larger than 0")
            }
            event.timeSpentMs = timeSpentMs

            val literacySkillName: String = intent.getStringExtra(BundleKeys.KEY_LITERACY_SKILL) ?: ""
            if (TextUtils.isEmpty(literacySkillName)) {
                throw IllegalArgumentException("Missing literacySkill")
            }
            val literacySkill: LiteracySkill = LiteracySkill.valueOf(literacySkillName)
            event.literacySkill = literacySkill

            // Store the event in the database
            val roomDb = RoomDb.getDatabase(context)
            RoomDb.databaseWriteExecutor.execute {
                roomDb.literacyAssessmentEventDao().insert(event)
            }
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            val errorMessage = e.message ?: e::class.simpleName
            results.putString("errorMessage", errorMessage)
        }
    }
}
