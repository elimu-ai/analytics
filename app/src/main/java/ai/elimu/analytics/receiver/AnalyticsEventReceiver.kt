package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.createEventFromIntent
import ai.elimu.analytics.util.toAnalyticEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.IntentAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import timber.log.Timber

class AnalyticsEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        try {
            intent.getStringExtra(BundleKeys.KEY_INTENT_ACTION)?.let { action ->
                IntentAction.entries.firstOrNull { it.action == action }?.let { intentAction ->
                    val event = intentAction.toAnalyticEvent()
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
            results.putString("errorClassName", e::class.simpleName);
        }
    }
}
