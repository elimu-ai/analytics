package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import timber.log.Timber
import java.util.Calendar

class NumberLearningEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val bundle = intent.extras
        bundle?.let {
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
                Timber.i("${key}=${value}")
            }
        }

        try {
            val event = NumberLearningEvent()

            val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            event.androidId = androidId

            val packageName: String = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
            if (TextUtils.isEmpty(packageName)) {
                throw IllegalArgumentException("Missing packageName")
            }
            event.packageName = packageName

            val timestamp: Calendar = Calendar.getInstance()
            event.timestamp = timestamp

            val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
            event.researchExperiment = researchExperiment

            val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
            event.experimentGroup = experimentGroup

            val additionalData: String? = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
            event.additionalData = additionalData

            val numberValue: Int = intent.getIntExtra(BundleKeys.KEY_NUMBER_VALUE, Int.MIN_VALUE)
            if (numberValue == Int.MIN_VALUE) {
                throw IllegalArgumentException("Missing numberValue")
            }
            event.numberValue = numberValue

            val numberSymbol: String? = intent.getStringExtra(BundleKeys.KEY_NUMBER_SYMBOL)
            event.numberSymbol = numberSymbol

            val numberId: Long = intent.getLongExtra(BundleKeys.KEY_NUMBER_ID, 0)
            if (numberId > 0) {
                event.numberId = numberId
            }

            // Store the event in the database
            val roomDb = RoomDb.getDatabase(context)
            RoomDb.databaseWriteExecutor.execute {
                roomDb.numberLearningEventDao().insert(event)
            }
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            val errorMessage = e.message ?: e::class.simpleName
            results.putString("errorMessage", errorMessage)
        }
    }
}
