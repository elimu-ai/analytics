package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.WordAssessmentEvent;

public class WordAssessmentEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(getClass().getName(), "androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Log.i(getClass().getName(), "timestamp.getTime(): " + timestamp.getTime());

        Long wordId = null;
        if (intent.hasExtra("wordId")) {
            wordId = intent.getLongExtra("wordId", 0);
        }
        Log.i(getClass().getName(), "wordId: " + wordId);

        String wordText = intent.getStringExtra("wordText");
        Log.i(getClass().getName(), "wordText: \"" + wordText + "\"");

        float masteryScore = intent.getFloatExtra("masteryScore", 0);
        Log.i(getClass().getName(), "masteryScore: " + masteryScore);

        long timeSpentMs = intent.getLongExtra("timeSpentMs", 0);
        Log.i(getClass().getName(), "timeSpentMs: " + timeSpentMs);

        WordAssessmentEvent wordAssessmentEvent = new WordAssessmentEvent();
        wordAssessmentEvent.setAndroidId(androidId);
        wordAssessmentEvent.setPackageName(packageName);
        wordAssessmentEvent.setTime(timestamp);
        wordAssessmentEvent.setWordId(wordId);
        wordAssessmentEvent.setWordText(wordText);
        wordAssessmentEvent.setMasteryScore(masteryScore);
        wordAssessmentEvent.setTimeSpentMs(timeSpentMs);

        // Store in database
        RoomDb roomDb = RoomDb.getDatabase(context);
        WordAssessmentEventDao wordAssessmentEventDao = roomDb.wordAssessmentEventDao();
        RoomDb.databaseWriteExecutor.execute(() -> {
            wordAssessmentEventDao.insert(wordAssessmentEvent);
        });
    }
}
