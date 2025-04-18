package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LetterSoundLearningEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class LetterSoundLearningEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra("packageName")
        Timber.i("packageName: \"$packageName\"")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.getTime(): " + timestamp.time)

        var letterSoundId: Long? = null
        if (intent.hasExtra("letterSoundId")) {
            letterSoundId = intent.getLongExtra("letterSoundId", 0)
        }
        Timber.i("letterSoundId: $letterSoundId")

        val letterSoundLetterTexts = intent.getStringArrayExtra("letterSoundLetterTexts")
        Timber.i("letterSoundLetterTexts: $letterSoundLetterTexts")

        val letterSoundSoundValuesIpa = intent.getStringArrayExtra("letterSoundSoundValuesIpa")
        Timber.i("letterSoundSoundValuesIpa: $letterSoundSoundValuesIpa")

        val letterSoundLearningEvent = LetterSoundLearningEvent()
        letterSoundLearningEvent.androidId = androidId
        letterSoundLearningEvent.packageName = packageName!!
        letterSoundLearningEvent.time = timestamp
        letterSoundLearningEvent.letterSoundId = letterSoundId
        letterSoundLearningEvent.letterSoundLetterTexts = letterSoundLetterTexts!!
        letterSoundLearningEvent.letterSoundSoundValuesIpa = letterSoundSoundValuesIpa!!

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            letterSoundLearningEventDao.insert(letterSoundLearningEvent)
        }
    }
}
