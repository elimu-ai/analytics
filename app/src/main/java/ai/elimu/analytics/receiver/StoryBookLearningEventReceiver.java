package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.AnalyticsRoomDatabase;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class StoryBookLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(getClass().getName(), "androidId: " + androidId);

        Calendar timestamp = Calendar.getInstance();
        Log.i(getClass().getName(), "timestamp.getTime(): " + timestamp.getTime());

        Long storyBookId = intent.getLongExtra("storyBookId", -1);
        Log.i(getClass().getName(), "storyBookId: " + storyBookId);

        StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
        storyBookLearningEvent.setAndroidId(androidId);
        storyBookLearningEvent.setTimestamp(timestamp);
        storyBookLearningEvent.setStoryBookId(storyBookId);

        // Store in database
        AnalyticsRoomDatabase analyticsRoomDatabase = AnalyticsRoomDatabase.getDatabase(context);
        StoryBookLearningEventDao storyBookLearningEventDao = analyticsRoomDatabase.storyBookLearningEventDao();
        AnalyticsRoomDatabase.databaseWriteExecutor.execute(() -> {
            storyBookLearningEventDao.insert(storyBookLearningEvent);
        });
    }
}
