package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.LetterAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterAssessmentEvent;

public class LetterAssessmentEventReceiver extends BroadcastReceiver {

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

        float masteryScore = intent.getFloatExtra("masteryScore", 0);
        Timber.i("masteryScore: " + masteryScore);

        long timeSpentMs = intent.getLongExtra("timeSpentMs", 0);
        Timber.i("timeSpentMs: " + timeSpentMs);

        LetterAssessmentEvent letterAssessmentEvent = new LetterAssessmentEvent();
        letterAssessmentEvent.setAndroidId(androidId);
        letterAssessmentEvent.setPackageName(packageName);
        letterAssessmentEvent.setTime(timestamp);
        letterAssessmentEvent.setLetterId(letterId);
        letterAssessmentEvent.setLetterText(letterText);
        letterAssessmentEvent.setMasteryScore(masteryScore);
        letterAssessmentEvent.setTimeSpentMs(timeSpentMs);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        LetterAssessmentEventDao letterAssessmentEventDao = roomDb.letterAssessmentEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            letterAssessmentEventDao.insert(letterAssessmentEvent);
        });
    }
}
