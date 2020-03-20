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

        // Initiate periodic upload of events to the server
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UploadEventsWorker.class, 24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                "upload_events_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
        );
    }
}
