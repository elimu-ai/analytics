package ai.elimu.analytics.utils.converter;

import android.database.Cursor;

import java.util.Arrays;
import java.util.Calendar;

import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;
import timber.log.Timber;

public class CursorToWordAssessmentEventGsonConverter {

    public static WordAssessmentEventGson getWordAssessmentEventGson(Cursor cursor) {
        Timber.i("getWordAssessmentEventGson");

        Timber.i("Arrays.toString(cursor.getColumnNames()): " + Arrays.toString(cursor.getColumnNames()));

        int columnId = cursor.getColumnIndex("id");
        Long id = cursor.getLong(columnId);
        Timber.i("id: " + id);

        int columnAndroidId = cursor.getColumnIndex("androidId");
        String androidId = cursor.getString(columnAndroidId);
        Timber.i("androidId: \"" + androidId + "\"");

        int columnPackageName = cursor.getColumnIndex("packageName");
        String packageName = cursor.getString(columnPackageName);
        Timber.i("packageName: \"" + packageName + "\"");

        int columnTime = cursor.getColumnIndex("time");
        Long timeAsLong = cursor.getLong(columnTime);
        Timber.i("timeAsLong: " + timeAsLong);
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeAsLong);
        Timber.i("time.getTime(): " + time.getTime());

        int columnWordId = cursor.getColumnIndex("wordId");
        Long wordId = cursor.getLong(columnWordId);
        Timber.i("wordId: " + wordId);

        int columnWordText = cursor.getColumnIndex("wordText");
        String wordText = cursor.getString(columnWordText);
        Timber.i("wordText: \"" + wordText + "\"");

        int columnMasteryScore = cursor.getColumnIndex("masteryScore");
        Float masteryScore = cursor.getFloat(columnMasteryScore);
        Timber.i("masteryScore: " + masteryScore);

        int columnTimeSpentMs = cursor.getColumnIndex("timeSpentMs");
        Long timeSpentMs = cursor.getLong(columnTimeSpentMs);
        Timber.i("timeSpentMs: " + masteryScore);

        WordAssessmentEventGson wordAssessmentEventGson = new WordAssessmentEventGson();
        wordAssessmentEventGson.setId(id);
        wordAssessmentEventGson.setAndroidId(androidId);
        wordAssessmentEventGson.setPackageName(packageName);
        wordAssessmentEventGson.setTime(time);
        wordAssessmentEventGson.setWordId(wordId);
        wordAssessmentEventGson.setWordText(wordText);
        wordAssessmentEventGson.setMasteryScore(masteryScore);
        wordAssessmentEventGson.setTimeSpentMs(timeSpentMs);

        return wordAssessmentEventGson;
    }
}
