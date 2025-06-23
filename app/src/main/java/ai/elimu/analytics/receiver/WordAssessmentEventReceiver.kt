package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.createEventFromIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class WordAssessmentEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        val wordAssessmentEvent = AnalyticEventType.WORD_ASSESSMENT
            .createEventFromIntent(context, intent)

        // Store in database
        wordAssessmentEvent.persistEvent(context)
    }
}
