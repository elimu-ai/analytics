package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.dao.LetterAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterAssessmentEvent;

public class LetterAssessmentEventReceiver extends BroadcastReceiver {

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

        float masteryScore = intent.getFloatExtra("masteryScore", 0);
        Log.i(getClass().getName(), "masteryScore: " + masteryScore);

        long timeSpentMs = intent.getLongExtra("timeSpentMs", 0);
        Log.i(getClass().getName(), "timeSpentMs: " + timeSpentMs);

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
