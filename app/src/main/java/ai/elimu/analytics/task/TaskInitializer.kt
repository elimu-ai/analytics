package ai.elimu.analytics.task

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import timber.log.Timber
import java.util.concurrent.TimeUnit

object TaskInitializer {
    fun initializePeriodicWork(context: Context) {
        Timber.i("initializePeriodicWork")

        // Periodically export events to CSV files
        val exportEventsToCsvWorkRequest = PeriodicWorkRequest.Builder(
            ExportEventsToCsvWorker::class.java, 15, TimeUnit.MINUTES
        )
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "export_events_to_csv",
            ExistingPeriodicWorkPolicy.KEEP,
            exportEventsToCsvWorkRequest
        )

        // Periodically upload events (CSV files) to the server
        val uploadEventsConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadEventsWorkRequest = PeriodicWorkRequest.Builder(
            UploadEventsWorker::class.java, 3, TimeUnit.HOURS
        )
            .setConstraints(uploadEventsConstraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "upload_events",
            ExistingPeriodicWorkPolicy.KEEP,
            uploadEventsWorkRequest
        )
    }
}
