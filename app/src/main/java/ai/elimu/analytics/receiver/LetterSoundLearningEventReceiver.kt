package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LetterSoundLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class LetterSoundLearningEventReceiver : BroadcastReceiver() {
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

        val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
        val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
        Timber.i("researchExperiment: ${researchExperiment} (${experimentGroup})")

        val letterSoundLetters = intent.getStringArrayExtra(BundleKeys.KEY_LETTER_SOUND_LETTER_TEXTS) ?: emptyArray()
        Timber.i("letterSoundLetters: $letterSoundLetters")

        val letterSoundSounds = intent.getStringArrayExtra(BundleKeys.KEY_LETTER_SOUND_SOUND_VALUES_IPA) ?: emptyArray()
        Timber.i("letterSoundSounds: $letterSoundSounds")

        var letterSoundId: Long? = null
        if (intent.hasExtra(BundleKeys.KEY_LETTER_SOUND_ID)) {
            letterSoundId = intent.getLongExtra(BundleKeys.KEY_LETTER_SOUND_ID, 0)
        }
        Timber.i("letterSoundId: $letterSoundId")

        val letterSoundLearningEvent = LetterSoundLearningEvent()
        letterSoundLearningEvent.androidId = androidId
        letterSoundLearningEvent.packageName = packageName
        letterSoundLearningEvent.time = timestamp
        letterSoundLearningEvent.additionalData = additionalData
        letterSoundLearningEvent.researchExperiment = researchExperiment
        letterSoundLearningEvent.experimentGroup = experimentGroup
        letterSoundLearningEvent.letterSoundLetterTexts = letterSoundLetters
        letterSoundLearningEvent.letterSoundSoundValuesIpa = letterSoundSounds
        letterSoundLearningEvent.letterSoundId = letterSoundId

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            letterSoundLearningEventDao.insert(letterSoundLearningEvent)
        }
    }
}
