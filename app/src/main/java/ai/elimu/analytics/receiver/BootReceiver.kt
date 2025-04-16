package ai.elimu.analytics.receiver

import ai.elimu.analytics.task.TaskInitializer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive")

        TaskInitializer.initializePeriodicWork(context)
    }
}
