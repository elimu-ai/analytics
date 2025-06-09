package ai.elimu.analytics.receiver

import ai.elimu.analytics.entity.VideoLearningEvent
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class VideoLearningEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.time: %s", timestamp.time)

        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra("packageName") ?: ""
        Timber.i("packageName: \"$packageName\"")

        val learningEventTypeAsString = intent.getStringExtra("learningEventType") ?: ""
        Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
        val learningEventType = LearningEventType.valueOf(
            learningEventTypeAsString
        )
        Timber.i("learningEventType: $learningEventType")

        val videoId = intent.getLongExtra("videoId", 0)
        Timber.i("videoId: $videoId")

        val videoTitle = intent.getStringExtra("videoTitle") ?: ""
        Timber.i("videoTitle: \"$videoTitle\"")

        val videoLearningEvent = VideoLearningEvent()
        videoLearningEvent.timestamp = timestamp
        videoLearningEvent.androidId = androidId
        videoLearningEvent.packageName = packageName
        videoLearningEvent.learningEventType = learningEventType
        videoLearningEvent.videoId = videoId
        videoLearningEvent.videoTitle = videoTitle

        // TODO: Store in database
    }
}
