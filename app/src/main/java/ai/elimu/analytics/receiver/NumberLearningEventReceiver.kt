package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.createEventFromIntent
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class NumberLearningEventReceiver : BroadcastReceiver() {
    @SuppressLint("HardwareIds")
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val numberLearningEvent = AnalyticEventType.NUMBER_LEARNING
            .createEventFromIntent(context, intent)

        // Store in database
        numberLearningEvent.persistEvent(context)
    }
}
