package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.StoryBookLearningEvent
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

        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra("packageName")
        Timber.i("packageName: \"$packageName\"")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.getTime(): " + timestamp.time)

        val storyBookId = intent.getLongExtra("storyBookId", 0)
        Timber.i("storyBookId: $storyBookId")

        val storyBookTitle = intent.getStringExtra("storyBookTitle")
        Timber.i("storyBookTitle: \"$storyBookTitle\"")

        val learningEventTypeAsString = intent.getStringExtra("learningEventType")
        Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
        val learningEventType = LearningEventType.valueOf(
            learningEventTypeAsString!!
        )
        Timber.i("learningEventType: $learningEventType")

        val storyBookLearningEvent = StoryBookLearningEvent()
        storyBookLearningEvent.androidId = androidId
        storyBookLearningEvent.packageName = packageName!!
        storyBookLearningEvent.time = timestamp
        storyBookLearningEvent.storyBookId = storyBookId
        //        storyBookLearningEvent.setStoryBookTitle(storyBookTitle);
        storyBookLearningEvent.learningEventType = learningEventType

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            storyBookLearningEventDao.insert(storyBookLearningEvent)
        }
    }
}
