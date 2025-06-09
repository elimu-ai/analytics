package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class LetterSoundAssessmentEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"${androidId}\"")

        val packageName: String = intent.getStringExtra("packageName") ?: ""
        Timber.i("packageName: \"${packageName}\"")

        val timestamp: Calendar = Calendar.getInstance()
        Timber.i("timestamp.time: ${timestamp.time}")

        val letterSoundLetters: String = intent.getStringExtra("letterSoundLetters") ?: ""
        Timber.i("letterSoundLetters: \"${letterSoundLetters}\"")

        val letterSoundSounds: String = intent.getStringExtra("letterSoundSounds") ?: ""
        Timber.i("letterSoundSounds: \"${letterSoundSounds}\"")

        val letterSoundId: Long = intent.getLongExtra("letterSoundId", 0)
        Timber.i("letterSoundId: ${letterSoundId}")

        val masteryScore: Float = intent.getFloatExtra("masteryScore", 0f)
        Timber.i("masteryScore: ${masteryScore}")

        val timeSpentMs: Long = intent.getLongExtra("timeSpentMs", 0)
        Timber.i("timeSpentMs: ${timeSpentMs}")

        val additionalData: String? = intent.getStringExtra("additionalData")
        Timber.i("additionalData: \"${additionalData}\"")

        val letterSoundAssessmentEvent = LetterSoundAssessmentEvent()
        letterSoundAssessmentEvent.timestamp = timestamp
        letterSoundAssessmentEvent.androidId = androidId
        letterSoundAssessmentEvent.packageName = packageName
        letterSoundAssessmentEvent.letterSoundLetters = letterSoundLetters
        letterSoundAssessmentEvent.letterSoundSounds = letterSoundSounds
        letterSoundAssessmentEvent.letterSoundId = letterSoundId
        letterSoundAssessmentEvent.masteryScore = masteryScore
        letterSoundAssessmentEvent.timeSpentMs = timeSpentMs
//        letterSoundAssessmentEvent.additionalData = additionalData

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val letterSoundAssessmentEventDao = roomDb.letterSoundAssessmentEventDao()
        RoomDb.databaseWriteExecutor.execute {
            letterSoundAssessmentEventDao.insert(letterSoundAssessmentEvent)
        }
    }
}
