package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.VideoLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class VideoLearningEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        try {
            val timestamp = Calendar.getInstance()
            Timber.i("timestamp.time: %s", timestamp.time)

            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            Timber.i("androidId: \"$androidId\"")

            val packageName = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
            Timber.i("packageName: \"$packageName\"")

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

            val videoTitle = intent.getStringExtra(BundleKeys.KEY_VIDEO_TITLE) ?: ""
            Timber.i("videoTitle: \"$videoTitle\"")

            val videoId = intent.getLongExtra(BundleKeys.KEY_VIDEO_ID, 0)
            Timber.i("videoId: $videoId")

            val videoLearningEvent = VideoLearningEvent()
            videoLearningEvent.time = timestamp
            videoLearningEvent.androidId = androidId
            videoLearningEvent.packageName = packageName
            videoLearningEvent.additionalData = additionalData
            videoLearningEvent.learningEventType = learningEventType
            videoLearningEvent.researchExperiment = researchExperiment
            videoLearningEvent.experimentGroup = experimentGroup
            videoLearningEvent.videoTitle = videoTitle
            videoLearningEvent.videoId = videoId

            // Store in database
            val roomDb = RoomDb.getDatabase(context)
            val videoLearningEventDao = roomDb.videoLearningEventDao()
            RoomDb.databaseWriteExecutor.execute {
                videoLearningEventDao.insert(videoLearningEvent)
            }
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            results.putString("errorClassName", e::class.simpleName);
        }
    }
}
