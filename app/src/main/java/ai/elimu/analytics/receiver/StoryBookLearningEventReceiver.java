package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.AnalyticsRoomDatabase;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class StoryBookLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        Long storyBookId = intent.getLongExtra("storyBookId", -1);
        Log.i(getClass().getName(), "storyBookId: " + storyBookId);

        StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
        storyBookLearningEvent.setTimestamp(Calendar.getInstance());
        storyBookLearningEvent.setStoryBookId(storyBookId);

        // Store in database
        AnalyticsRoomDatabase analyticsRoomDatabase = AnalyticsRoomDatabase.getDatabase(context);
        StoryBookLearningEventDao storyBookLearningEventDao = analyticsRoomDatabase.storyBookLearningEventDao();
        AnalyticsRoomDatabase.databaseWriteExecutor.execute(() -> {
            storyBookLearningEventDao.insert(storyBookLearningEvent);
        });
    }
}
