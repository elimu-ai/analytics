package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.LetterSoundLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterSoundCorrespondenceLearningEvent;
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

        Long letterSoundCorrespondenceId = null;
        if (intent.hasExtra("letterSoundCorrespondenceId")) {
            letterSoundCorrespondenceId = intent.getLongExtra("letterSoundCorrespondenceId", 0);
        }
        Timber.i("letterSoundCorrespondenceId: " + letterSoundCorrespondenceId);

        String[] letterSoundCorrespondenceLetterTexts = null;
        if (intent.hasExtra("letterSoundCorrespondenceLetterTexts")) {
            letterSoundCorrespondenceLetterTexts = intent.getStringArrayExtra("letterSoundCorrespondenceLetterTexts");
        }
        Timber.i("letterSoundCorrespondenceLetterTexts: " + letterSoundCorrespondenceLetterTexts);

        String[] letterSoundCorrespondenceSoundValuesIpa = null;
        if (intent.hasExtra("letterSoundCorrespondenceSoundValuesIpa")) {
            letterSoundCorrespondenceSoundValuesIpa = intent.getStringArrayExtra("letterSoundCorrespondenceSoundValuesIpa");
        }
        Timber.i("letterSoundCorrespondenceSoundValuesIpa: " + letterSoundCorrespondenceSoundValuesIpa);

        LetterSoundCorrespondenceLearningEvent letterSoundLearningEvent = new LetterSoundCorrespondenceLearningEvent();
        letterSoundLearningEvent.setAndroidId(androidId);
        letterSoundLearningEvent.setPackageName(packageName);
        letterSoundLearningEvent.setTime(timestamp);
        letterSoundLearningEvent.setLetterSoundCorrespondenceId(letterSoundCorrespondenceId);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        LetterSoundLearningEventDao letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            letterSoundLearningEventDao.insert(letterSoundLearningEvent);
        });
    }
}
