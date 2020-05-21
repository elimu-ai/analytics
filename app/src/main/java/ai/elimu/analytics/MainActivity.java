package ai.elimu.analytics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import ai.elimu.analytics.task.TaskInitializer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the database is not empty, redirect the user to the EventListActivity
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.loadAllOrderedByTimeDesc();
            Log.i(getClass().getName(), "wordLearningEvents.size(): " + wordLearningEvents.size());

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

        TaskInitializer.initializePeriodicWork(getApplicationContext());
    }
}
