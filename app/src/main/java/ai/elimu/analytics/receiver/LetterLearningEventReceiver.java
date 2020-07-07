package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.entity.LetterLearningEvent;
import ai.elimu.model.enums.analytics.LearningEventType;

public class LetterLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(getClass().getName(), "androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Log.i(getClass().getName(), "timestamp.getTime(): " + timestamp.getTime());

        Long letterId = null;
        if (intent.hasExtra("letterId")) {
            letterId = intent.getLongExtra("letterId", 0);
        }
        Log.i(getClass().getName(), "letterId: " + letterId);

        String letterText = intent.getStringExtra("letterText");
        Log.i(getClass().getName(), "letterText: \"" + letterText + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Log.i(getClass().getName(), "learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Log.i(getClass().getName(), "learningEventType: " + learningEventType);

        LetterLearningEvent letterLearningEvent = new LetterLearningEvent();
        letterLearningEvent.setAndroidId(androidId);
        letterLearningEvent.setPackageName(packageName);
        letterLearningEvent.setTime(timestamp);
        letterLearningEvent.setLetterId(letterId);
        letterLearningEvent.setLetterText(letterText);
        letterLearningEvent.setLearningEventType(learningEventType);

//        // Store in database
//        RoomDb roomDb = RoomDb.getDatabase(context);
//        LetterLearningEventDao letterLearningEventDao = roomDb.letterLearningEventDao();
//        RoomDb.databaseWriteExecutor.execute(() -> {
//            letterLearningEventDao.insert(letterLearningEvent);
//        });
    }
}
