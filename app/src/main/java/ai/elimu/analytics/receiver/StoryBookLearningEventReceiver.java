package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.model.v2.enums.analytics.LearningEventType;

public class StoryBookLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Timber.i("androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        Long storyBookId = intent.getLongExtra("storyBookId", 0);
        Timber.i("storyBookId: " + storyBookId);

        String storyBookTitle = intent.getStringExtra("storyBookTitle");
        Timber.i("storyBookTitle: \"" + storyBookTitle + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Timber.i("learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

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
