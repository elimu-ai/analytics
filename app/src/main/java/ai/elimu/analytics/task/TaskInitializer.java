package ai.elimu.analytics.task;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class TaskInitializer {

    public static void initializePeriodicWork(Context context) {
        Timber.i("initializePeriodicWork");

        // Periodically export events to CSV files
        PeriodicWorkRequest exportEventsToCsvWorkRequest = new PeriodicWorkRequest
//                .Builder(ExportEventsToCsvWorker.class, 1, TimeUnit.HOURS)
                .Builder(ExportEventsToCsvWorker.class, 15, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "export_events_to_csv",
                ExistingPeriodicWorkPolicy.KEEP,
                exportEventsToCsvWorkRequest
        );

        // Periodically upload events (CSV files) to the server
        Constraints uploadEventsConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest uploadEventsWorkRequest = new PeriodicWorkRequest
//                .Builder(UploadEventsWorker.class, 12, TimeUnit.HOURS)
                .Builder(UploadEventsWorker.class, 3, TimeUnit.HOURS)
                .setConstraints(uploadEventsConstraints)
                .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "upload_events",
                ExistingPeriodicWorkPolicy.KEEP,
                uploadEventsWorkRequest
        );
    }
}
