package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class WordAssessmentEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra("packageName") ?: ""
        Timber.i("packageName: \"$packageName\"")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.getTime(): %s", timestamp.time)

        var wordId: Long? = null
        if (intent.hasExtra("wordId")) {
            wordId = intent.getLongExtra("wordId", 0)
        }
        Timber.i("wordId: $wordId")

        val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
        val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
        Timber.i("researchExperiment: ${researchExperiment} (${experimentGroup})")

        val wordText = intent.getStringExtra("wordText") ?: ""
        Timber.i("wordText: \"$wordText\"")

        val masteryScore = intent.getFloatExtra("masteryScore", 0f)
        Timber.i("masteryScore: $masteryScore")

        val timeSpentMs = intent.getLongExtra("timeSpentMs", 0)
        Timber.i("timeSpentMs: $timeSpentMs")

        val wordAssessmentEvent = WordAssessmentEvent()
        wordAssessmentEvent.androidId = androidId
        wordAssessmentEvent.packageName = packageName
        wordAssessmentEvent.time = timestamp
        wordAssessmentEvent.researchExperiment = researchExperiment
        wordAssessmentEvent.experimentGroup = experimentGroup
        wordAssessmentEvent.wordId = wordId
        wordAssessmentEvent.wordText = wordText
        wordAssessmentEvent.masteryScore = masteryScore
        wordAssessmentEvent.timeSpentMs = timeSpentMs

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
        RoomDb.databaseWriteExecutor.execute {
            wordAssessmentEventDao.insert(wordAssessmentEvent)
        }
    }
}
