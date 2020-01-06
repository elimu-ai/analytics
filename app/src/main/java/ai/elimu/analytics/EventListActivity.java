package ai.elimu.analytics;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final EventListAdapter eventListAdapter = new EventListAdapter(this);
        recyclerView.setAdapter(eventListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        AnalyticsRoomDatabase analyticsRoomDatabase = AnalyticsRoomDatabase.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = analyticsRoomDatabase.storyBookLearningEventDao();

        // Insert dummy value into database
        StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
        storyBookLearningEvent.setStoryBookId(123L);
        AnalyticsRoomDatabase.databaseWriteExecutor.execute(() -> {
            storyBookLearningEventDao.insert(storyBookLearningEvent);
        });

        // Fetch all learning events from database, and update adapter
        AnalyticsRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
            Log.d(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
            eventListAdapter.setStoryBookLearningEvents(storyBookLearningEvents);
        });
    }
}
