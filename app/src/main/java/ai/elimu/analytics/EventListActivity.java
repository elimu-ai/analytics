package ai.elimu.analytics;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final EventListAdapter eventListAdapter = new EventListAdapter(this);
        recyclerView.setAdapter(eventListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Fetch all learning events from database, and update adapter
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
            Log.d(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
            eventListAdapter.setStoryBookLearningEvents(storyBookLearningEvents);
        });
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();
    }
}
