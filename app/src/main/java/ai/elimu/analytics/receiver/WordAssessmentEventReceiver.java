package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.WordAssessmentEvent;

public class WordAssessmentEventReceiver extends BroadcastReceiver {

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

        float masteryScore = intent.getFloatExtra("masteryScore", 0);
        Timber.i("masteryScore: " + masteryScore);

        long timeSpentMs = intent.getLongExtra("timeSpentMs", 0);
        Timber.i("timeSpentMs: " + timeSpentMs);

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
