package ai.elimu.analytics.utils.converter;

import android.database.Cursor;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;

public class CursorToLetterAssessmentEventGsonConverter {

    public static LetterAssessmentEventGson getLetterAssessmentEventGson(Cursor cursor) {
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"getLetterAssessmentEventGson");

        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"Arrays.toString(cursor.getColumnNames()): " + Arrays.toString(cursor.getColumnNames()));

        int columnId = cursor.getColumnIndex("id");
        Long id = cursor.getLong(columnId);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"id: " + id);

        int columnAndroidId = cursor.getColumnIndex("androidId");
        String androidId = cursor.getString(columnAndroidId);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"androidId: \"" + androidId + "\"");

        int columnPackageName = cursor.getColumnIndex("packageName");
        String packageName = cursor.getString(columnPackageName);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"packageName: \"" + packageName + "\"");

        int columnTime = cursor.getColumnIndex("time");
        Long timeAsLong = cursor.getLong(columnTime);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"timeAsLong: " + timeAsLong);
        Calendar timestamp = Calendar.getInstance();
        timestamp.setTimeInMillis(timeAsLong);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"time.getTime(): " + timestamp.getTime());

        int columnLetterId = cursor.getColumnIndex("letterId");
        Long letterId = cursor.getLong(columnLetterId);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"letterId: " + letterId);

        int columnLetterText = cursor.getColumnIndex("letterText");
        String letterText = cursor.getString(columnLetterText);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"letterText: \"" + letterText + "\"");

        int columnMasteryScore = cursor.getColumnIndex("masteryScore");
        Float masteryScore = cursor.getFloat(columnMasteryScore);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"masteryScore: " + masteryScore);

        int columnTimeSpentMs = cursor.getColumnIndex("timeSpentMs");
        Long timeSpentMs = cursor.getLong(columnTimeSpentMs);
        Log.i(CursorToLetterAssessmentEventGsonConverter.class.getName(),"timeSpentMs: " + masteryScore);

        LetterAssessmentEventGson letterAssessmentEventGson = new LetterAssessmentEventGson();
        letterAssessmentEventGson.setId(id);
        letterAssessmentEventGson.setAndroidId(androidId);
        letterAssessmentEventGson.setPackageName(packageName);
        letterAssessmentEventGson.setTimestamp(timestamp);
        letterAssessmentEventGson.setLetterId(letterId);
        letterAssessmentEventGson.setLetterText(letterText);
        letterAssessmentEventGson.setMasteryScore(masteryScore);
        letterAssessmentEventGson.setTimeSpentMs(timeSpentMs);

        return letterAssessmentEventGson;
    }
}
