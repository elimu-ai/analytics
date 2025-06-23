package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.WordLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class WordLearningEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
        Timber.i("packageName: \"$packageName\"")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.time: %s", timestamp.time)

        val additionalData = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
        Timber.i("additionalData: ${additionalData}")

        val learningEventTypeAsString = intent.getStringExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE)
        Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
        val learningEventType = runCatching {
            learningEventTypeAsString?.let {
                LearningEventType.valueOf(it)
            }
        }.getOrNull()

        Timber.i("learningEventType: $learningEventType")

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

        val wordLearningEvent = WordLearningEvent().apply {
            this.androidId = androidId
            this.packageName = packageName
            this.time = timestamp
            this.additionalData = additionalData
            this.learningEventType = learningEventType
            this.researchExperiment = researchExperiment
            this.experimentGroup = experimentGroup
            this.wordText = wordText
            this.wordId = wordId
        }

        // Store in database
        wordLearningEvent.persistEvent(context)
    }
}
