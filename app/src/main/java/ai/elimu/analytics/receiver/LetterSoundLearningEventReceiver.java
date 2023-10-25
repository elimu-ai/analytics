package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.LetterSoundLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterSoundLearningEvent;
import timber.log.Timber;

public class LetterSoundLearningEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Timber.i("androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        Long letterSoundId = null;
        if (intent.hasExtra("letterSoundId")) {
            letterSoundId = intent.getLongExtra("letterSoundId", 0);
        }
        Timber.i("letterSoundId: " + letterSoundId);

        String[] letterSoundLetterTexts = intent.getStringArrayExtra("letterSoundLetterTexts");
        Timber.i("letterSoundLetterTexts: " + letterSoundLetterTexts);

        String[] letterSoundSoundValuesIpa = intent.getStringArrayExtra("letterSoundSoundValuesIpa");
        Timber.i("letterSoundSoundValuesIpa: " + letterSoundSoundValuesIpa);

        LetterSoundLearningEvent letterSoundLearningEvent = new LetterSoundLearningEvent();
        letterSoundLearningEvent.setAndroidId(androidId);
        letterSoundLearningEvent.setPackageName(packageName);
        letterSoundLearningEvent.setTime(timestamp);
        letterSoundLearningEvent.setLetterSoundId(letterSoundId);
        letterSoundLearningEvent.setLetterSoundLetterTexts(letterSoundLetterTexts);
        letterSoundLearningEvent.setLetterSoundSoundValuesIpa(letterSoundSoundValuesIpa);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        LetterSoundLearningEventDao letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            letterSoundLearningEventDao.insert(letterSoundLearningEvent);
        });
    }
}
