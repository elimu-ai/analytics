package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class WordAssessmentEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        try {
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            Timber.i("androidId: \"$androidId\"")

            val packageName = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
            Timber.i("packageName: \"$packageName\"")

            val timestamp = Calendar.getInstance()
            Timber.i("timestamp.time: %s", timestamp.time)

            val masteryScore = intent.getFloatExtra(BundleKeys.KEY_MASTERY_SCORE, 0f)
            Timber.i("masteryScore: $masteryScore")

            val timeSpentMs = intent.getLongExtra(BundleKeys.KEY_TIME_SPENT_MS, 0)
            Timber.i("timeSpentMs: $timeSpentMs")

            val additionalData: String? = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
            Timber.i("additionalData: \"${additionalData}\"")

            val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
            val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
            Timber.i("researchExperiment: ${researchExperiment} (${experimentGroup})")

            val wordText = intent.getStringExtra(BundleKeys.KEY_WORD_TEXT) ?: ""
            Timber.i("wordText: \"$wordText\"")

            var wordId: Long? = null
            if (intent.hasExtra(BundleKeys.KEY_WORD_ID)) {
                wordId = intent.getLongExtra(BundleKeys.KEY_WORD_ID, 0)
            }
            Timber.i("wordId: $wordId")

            val wordAssessmentEvent = WordAssessmentEvent()
            wordAssessmentEvent.androidId = androidId
            wordAssessmentEvent.packageName = packageName
            wordAssessmentEvent.time = timestamp
            wordAssessmentEvent.masteryScore = masteryScore
            wordAssessmentEvent.timeSpentMs = timeSpentMs
            wordAssessmentEvent.additionalData = additionalData
            wordAssessmentEvent.researchExperiment = researchExperiment
            wordAssessmentEvent.experimentGroup = experimentGroup
            wordAssessmentEvent.wordText = wordText
            wordAssessmentEvent.wordId = wordId

            // Store in database
            val roomDb = RoomDb.getDatabase(context)
            val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
            RoomDb.databaseWriteExecutor.execute {
                wordAssessmentEventDao.insert(wordAssessmentEvent)
            }
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            results.putString("errorClassName", e::class.simpleName);
        }
    }
}
