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

        intent.getStringExtra("intent_action")?.let { action ->
            IntentAction.entries.firstOrNull { it.action == action }?.let { intentAction ->
                val event = intentAction.toEventType()
                .createEventFromIntent(context, intent)

                // Store in database
                event.persistEvent(context)
            } ?: run {
                Timber.w("Unrecognized intent action: $action")
            }

        }
    }
}
