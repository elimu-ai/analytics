package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class NumberLearningEventReceiver : BroadcastReceiver() {
    @SuppressLint("HardwareIds")
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val timestamp = Calendar.getInstance()
        Timber.i("timestamp.getTime(): %s", timestamp.time)

        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.i("androidId: \"$androidId\"")

        val packageName = intent.getStringExtra("packageName") ?: ""
        Timber.i("packageName: \"$packageName\"")

        val additionalData = intent.getStringExtra("additionalData")
        Timber.i("additionalData: ${additionalData}")

        val learningEventTypeAsString = intent.getStringExtra("learningEventType")
        Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
        val learningEventType = runCatching {
            learningEventTypeAsString?.let {
                LearningEventType.valueOf(it)
            }
        }.getOrNull()
        Timber.i("learningEventType: $learningEventType")

        val numberId = intent.getLongExtra("numberId", 0)
        Timber.i("numberId: $numberId")

        val numberValue = intent.getIntExtra("numberValue", 0)
        Timber.i("numberValue: \"$numberValue\"")

        val numberSymbol = intent.getStringExtra("numberSymbol")
        Timber.i("numberSymbol: \"$numberSymbol\"")

        val numberLearningEvent = NumberLearningEvent(numberValue).apply {
            this.time = timestamp
            this.androidId = androidId
            this.packageName = packageName
            this.additionalData = additionalData
            this.researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
            this.experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
            this.learningEventType = learningEventType
            this.numberId = numberId
            this.numberSymbol = numberSymbol
        }

        // Store in database
        val roomDb = RoomDb.getDatabase(context)
        val numberLearningEventDao = roomDb.numberLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            numberLearningEventDao.insert(numberLearningEvent)
        }
    }
}
