package ai.elimu.analytics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the database is not empty, redirect the user to the EventListActivity
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
            Log.d(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
            if (!storyBookLearningEvents.isEmpty()) {
                startActivity(new Intent(getApplicationContext(), EventListActivity.class));
                finish();
            }
        });
    }
}
