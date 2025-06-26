package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.enum.createEventFromIntent
import ai.elimu.analytics.util.toEventType
import ai.elimu.analytics.utils.IntentAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AnalyticsEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        Timber.i("intent.action: ${intent.action}")
        val bundle = intent.extras
        bundle?.let {
            Timber.i("bundle.keySet(): ${bundle.keySet().joinToString()}")
        }

        val intentActionType: String? = intent.getStringExtra("intent_action")
        Timber.i("intentActionType: ${intentActionType}")
        intentActionType?.let {
            val intentAction = IntentAction.valueOf(intentActionType)
            Timber.i("intentAction: ${intentAction}")

            // Convert from Intent data to Room entity
            val eventType = intentAction.toEventType()
            val event = eventType.createEventFromIntent(context, intent)

            // Store event in database
            event.persistEvent(context)
        }
    }
}
