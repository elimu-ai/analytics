package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.StoryBookLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class StoryBookLearningEventReceiver : BroadcastReceiver() {
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

        val storyBookTitle: String = intent.getStringExtra(BundleKeys.KEY_STORYBOOK_TITLE)
                ?: throw IllegalArgumentException("storyBookTitle must be provided")
        Timber.i("storyBookTitle: \"$storyBookTitle\"")

        val storyBookId = intent.getLongExtra(BundleKeys.KEY_STORYBOOK_ID, 0)
        Timber.i("storyBookId: $storyBookId")

        val storyBookLearningEvent = StoryBookLearningEvent()
        storyBookLearningEvent.androidId = androidId
        storyBookLearningEvent.packageName = packageName
        storyBookLearningEvent.time = timestamp
        storyBookLearningEvent.additionalData = additionalData
        storyBookLearningEvent.learningEventType = learningEventType
        storyBookLearningEvent.researchExperiment = researchExperiment
        storyBookLearningEvent.experimentGroup = experimentGroup
        storyBookLearningEvent.storyBookTitle = storyBookTitle
        storyBookLearningEvent.storyBookId = storyBookId

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            storyBookLearningEventDao.insert(storyBookLearningEvent)
        }
    }
}
