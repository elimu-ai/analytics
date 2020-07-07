package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.model.enums.analytics.LearningEventType;

public class StoryBookLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(getClass().getName(), "androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Log.i(getClass().getName(), "timestamp.getTime(): " + timestamp.getTime());

        Long storyBookId = intent.getLongExtra("storyBookId", 0);
        Log.i(getClass().getName(), "storyBookId: " + storyBookId);

        String storyBookTitle = intent.getStringExtra("storyBookTitle");
        Log.i(getClass().getName(), "storyBookTitle: \"" + storyBookTitle + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Log.i(getClass().getName(), "learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Log.i(getClass().getName(), "learningEventType: " + learningEventType);

        StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
        storyBookLearningEvent.setAndroidId(androidId);
        storyBookLearningEvent.setPackageName(packageName);
        storyBookLearningEvent.setTime(timestamp);
        storyBookLearningEvent.setStoryBookId(storyBookId);
//        storyBookLearningEvent.setStoryBookTitle(storyBookTitle);
        storyBookLearningEvent.setLearningEventType(learningEventType);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            storyBookLearningEventDao.insert(storyBookLearningEvent);
        });
    }
}
