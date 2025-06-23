package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import timber.log.Timber
import java.util.Calendar

class NumberLearningEventReceiver : BroadcastReceiver() {
    @SuppressLint("HardwareIds")
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

            val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
            val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
            Timber.i("researchExperiment: ${researchExperiment} (${experimentGroup})")

            val learningEventTypeAsString = intent.getStringExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE)
            Timber.i("learningEventTypeAsString: \"$learningEventTypeAsString\"")
            val learningEventType = runCatching {
                learningEventTypeAsString?.let {
                    LearningEventType.valueOf(it)
                }
            }.getOrNull()
            Timber.i("learningEventType: $learningEventType")

            val numberValue = intent.getIntExtra(BundleKeys.KEY_NUMBER_VALUE, 0)
            Timber.i("numberValue: \"$numberValue\"")

            val numberSymbol = intent.getStringExtra(BundleKeys.KEY_NUMBER_SYMBOL)
            Timber.i("numberSymbol: \"$numberSymbol\"")

            val numberId = intent.getLongExtra(BundleKeys.KEY_NUMBER_ID, 0)
            Timber.i("numberId: $numberId")

            val numberLearningEvent = NumberLearningEvent(numberValue).apply {
                this.time = timestamp
                this.androidId = androidId
                this.packageName = packageName
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.learningEventType = learningEventType
                this.numberSymbol = numberSymbol
                this.numberId = numberId
            }

            // Store in database
            val roomDb = RoomDb.getDatabase(context)
            val numberLearningEventDao = roomDb.numberLearningEventDao()
            RoomDb.databaseWriteExecutor.execute {
                numberLearningEventDao.insert(numberLearningEvent)
            }
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            results.putString("errorClassName", e::class.simpleName);
        }
    }
}
