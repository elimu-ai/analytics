package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

//import ai.elimu.analytics.dao.WordLearningEventDao;
//import ai.elimu.analytics.db.RoomDb;
//import ai.elimu.analytics.entity.WordLearningEvent;
import ai.elimu.model.enums.analytics.LearningEventType;

public class WordLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(getClass().getName(), "androidId: " + androidId);

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        Calendar timestamp = Calendar.getInstance();
        Log.i(getClass().getName(), "timestamp.getTime(): " + timestamp.getTime());

        Long wordId = intent.getLongExtra("wordId", 0);
        Log.i(getClass().getName(), "wordId: " + wordId);

        String wordText = intent.getStringExtra("wordText");
        Log.i(getClass().getName(), "wordText: \"" + wordText + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Log.i(getClass().getName(), "learningEventTypeAsString: " + learningEventTypeAsString);
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Log.i(getClass().getName(), "learningEventType: " + learningEventType);

//        WordLearningEvent wordLearningEvent = new WordLearningEvent();
//        wordLearningEvent.setAndroidId(androidId);
//        wordLearningEvent.setPackageName(packageName);
//        wordLearningEvent.setTime(timestamp);
//        wordLearningEvent.setWordId(wordId);
//        wordLearningEvent.setLearningEventType(learningEventType);
//
//        // Store in database
//        RoomDb roomDb = RoomDb.getDatabase(context);
//        WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
//        RoomDb.databaseWriteExecutor.execute(() -> {
//            wordLearningEventDao.insert(wordLearningEvent);
//        });
    }
}
