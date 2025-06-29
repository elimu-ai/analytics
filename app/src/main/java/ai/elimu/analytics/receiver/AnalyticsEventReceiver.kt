package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.enum.createEventFromIntent
import ai.elimu.analytics.util.toEventType
import ai.elimu.analytics.utils.IntentAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import timber.log.Timber

class AnalyticsEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        Timber.i("intent.action: ${intent.action}")
        val bundle = intent.extras
        bundle?.let {
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
                Timber.i("${key}=${value}")
            }
        }

        try {
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
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            val errorMessage = e.message ?: e::class.simpleName
            results.putString("errorMessage", errorMessage)
        }
    }
}
