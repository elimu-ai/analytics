package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.createEventFromIntent
import ai.elimu.analytics.util.toAnalyticEvent
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.IntentAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AnalyticsEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        intent.getStringExtra(BundleKeys.KEY_INTENT_ACTION)?.let { action ->
            val event = IntentAction.entries.first { it.action == action }
                .toAnalyticEvent()
                .createEventFromIntent(context, intent)

            // Store in database
            event.persistEvent(context)
        }
    }
}
