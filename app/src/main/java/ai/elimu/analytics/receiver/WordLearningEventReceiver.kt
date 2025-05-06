package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.WordLearningEvent
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

        val wordText = intent.getStringExtra("wordText") ?: ""
        Timber.i("wordText: \"$wordText\"")

        val learningEventTypeAsString = intent.getStringExtra("learningEventType") ?: ""
        Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
        val learningEventType = LearningEventType.valueOf(
            learningEventTypeAsString
        )
        Timber.i("learningEventType: $learningEventType")

        val wordLearningEvent = WordLearningEvent().apply {
            this.androidId = androidId
            this.packageName = packageName
            this.time = timestamp
            this.wordId = wordId
            this.wordText = wordText
            this.learningEventType = learningEventType
        }

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val wordLearningEventDao = roomDb.wordLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            wordLearningEventDao.insert(wordLearningEvent)
        }
    }
}
