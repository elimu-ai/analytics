package ai.elimu.analytics.utils.converter;

import android.database.Cursor;

import java.util.Arrays;
import java.util.Calendar;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;

public class CursorToLetterAssessmentEventGsonConverter {

    public static LetterAssessmentEventGson getLetterAssessmentEventGson(Cursor cursor) {
        Timber.i("getLetterAssessmentEventGson");

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

        int columnLetterId = cursor.getColumnIndex("letterId");
        Long letterId = cursor.getLong(columnLetterId);
        Timber.i("letterId: " + letterId);

        int columnLetterText = cursor.getColumnIndex("letterText");
        String letterText = cursor.getString(columnLetterText);
        Timber.i("letterText: \"" + letterText + "\"");

        int columnMasteryScore = cursor.getColumnIndex("masteryScore");
        Float masteryScore = cursor.getFloat(columnMasteryScore);
        Timber.i("masteryScore: " + masteryScore);

        int columnTimeSpentMs = cursor.getColumnIndex("timeSpentMs");
        Long timeSpentMs = cursor.getLong(columnTimeSpentMs);
        Timber.i("timeSpentMs: " + masteryScore);

        LetterAssessmentEventGson letterAssessmentEventGson = new LetterAssessmentEventGson();
        letterAssessmentEventGson.setId(id);
        letterAssessmentEventGson.setAndroidId(androidId);
        letterAssessmentEventGson.setPackageName(packageName);
        letterAssessmentEventGson.setTime(time);
        letterAssessmentEventGson.setLetterId(letterId);
        letterAssessmentEventGson.setLetterText(letterText);
        letterAssessmentEventGson.setMasteryScore(masteryScore);
        letterAssessmentEventGson.setTimeSpentMs(timeSpentMs);

        return letterAssessmentEventGson;
    }
}
