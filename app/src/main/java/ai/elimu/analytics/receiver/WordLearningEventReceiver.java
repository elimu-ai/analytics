package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.WordLearningEvent;
import ai.elimu.model.v2.enums.analytics.LearningEventType;

public class WordLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Timber.i("androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        Long wordId = null;
        if (intent.hasExtra("wordId")) {
            wordId = intent.getLongExtra("wordId", 0);
        }
        Timber.i("wordId: " + wordId);

        String wordText = intent.getStringExtra("wordText");
        Timber.i("wordText: \"" + wordText + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Timber.i("learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

        WordLearningEvent wordLearningEvent = new WordLearningEvent();
        wordLearningEvent.setAndroidId(androidId);
        wordLearningEvent.setPackageName(packageName);
        wordLearningEvent.setTime(timestamp);
        wordLearningEvent.setWordId(wordId);
        wordLearningEvent.setWordText(wordText);
        wordLearningEvent.setLearningEventType(learningEventType);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            wordLearningEventDao.insert(wordLearningEvent);
        });
    }
}
