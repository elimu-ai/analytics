package ai.elimu.analytics;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import ai.elimu.analytics.language.SelectLanguageActivity;
import ai.elimu.analytics.task.TaskInitializer;
import ai.elimu.analytics.util.SharedPreferencesHelper;
import ai.elimu.model.v2.enums.Language;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Timber.i("onStart");
        super.onStart();

        Language language = SharedPreferencesHelper.getLanguage(getApplicationContext());
        Timber.i("language: " + language);
        if (language == null) {
            // Redirect to language selection

            Intent selectLanguageIntent = new Intent(getApplicationContext(), SelectLanguageActivity.class);
            startActivity(selectLanguageIntent);
            finish();
        } else {
            // Redirect to event list

            TaskInitializer.initializePeriodicWork(getApplicationContext());

            // If the database is not empty, redirect the user to the EventListActivity
            RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
            StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
            WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
            RoomDb.databaseWriteExecutor.execute(() -> {
                List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.loadAllOrderedByTimeDesc();
                Timber.i("wordLearningEvents.size(): " + wordLearningEvents.size());

                List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
                Timber.i("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
                if (!storyBookLearningEvents.isEmpty()) {
                    startActivity(new Intent(getApplicationContext(), EventListActivity.class));
                    finish();
                }
            });
        }
    }
}
