package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.LetterLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterLearningEvent;
import ai.elimu.model.v2.enums.analytics.LearningEventType;

public class LetterLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Timber.i("androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        Long letterId = null;
        if (intent.hasExtra("letterId")) {
            letterId = intent.getLongExtra("letterId", 0);
        }
        Timber.i("letterId: " + letterId);

        String letterText = intent.getStringExtra("letterText");
        Timber.i("letterText: \"" + letterText + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Timber.i("learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

        LetterLearningEvent letterLearningEvent = new LetterLearningEvent();
        letterLearningEvent.setAndroidId(androidId);
        letterLearningEvent.setPackageName(packageName);
        letterLearningEvent.setTime(timestamp);
        letterLearningEvent.setLetterId(letterId);
        letterLearningEvent.setLetterText(letterText);
        letterLearningEvent.setLearningEventType(learningEventType);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        LetterLearningEventDao letterLearningEventDao = roomDb.letterLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            letterLearningEventDao.insert(letterLearningEvent);
        });
    }
}
