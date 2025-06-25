package ai.elimu.analytics.receiver

import ai.elimu.analytics.db.persistEvent
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.createEventFromIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import timber.log.Timber

class LetterSoundLearningEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        try {
            // Convert from Intent to database entity
            val letterSoundLearningEvent = AnalyticEventType.LETTER_SOUND_LEARNING.createEventFromIntent(context, intent)

            // Store in database
            letterSoundLearningEvent.persistEvent(context)
        } catch (e: Exception) {
            Timber.e(e)
            val results: Bundle = getResultExtras(true)
            results.putString("errorClassName", e::class.simpleName);
        }
    }
}
