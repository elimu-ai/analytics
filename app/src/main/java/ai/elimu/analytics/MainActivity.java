package ai.elimu.analytics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.task.ExportEventsToCsvWorker;
import ai.elimu.analytics.task.UploadEventsWorker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the database is not empty, redirect the user to the EventListActivity
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
            Log.i(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
            if (!storyBookLearningEvents.isEmpty()) {
                startActivity(new Intent(getApplicationContext(), EventListActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();

        // Periodically export events to CSV files
        PeriodicWorkRequest exportEventsToCsvWorkRequest = new PeriodicWorkRequest.Builder(ExportEventsToCsvWorker.class, 1, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                "export_events_to_csv",
                ExistingPeriodicWorkPolicy.KEEP,
                exportEventsToCsvWorkRequest
        );

        // Periodically upload events (CSV files) to the server
        Constraints uploadEventsConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest uploadEventsWorkRequest = new PeriodicWorkRequest.Builder(UploadEventsWorker.class, 12, TimeUnit.HOURS)
                .setConstraints(uploadEventsConstraints)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                "upload_events",
                ExistingPeriodicWorkPolicy.KEEP,
                uploadEventsWorkRequest
        );
    }
}
