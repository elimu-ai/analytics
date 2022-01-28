package ai.elimu.analytics.utils.converter;

import android.database.Cursor;

import java.util.Arrays;
import java.util.Calendar;

import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson;
import timber.log.Timber;

public class CursorToWordLearningEventGsonConverter {

    public static WordLearningEventGson getWordLearningEventGson(Cursor cursor) {
        Timber.i("getWordLearningEventGson");

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

        int columnLearningEventType = cursor.getColumnIndex("learningEventType");
        String learningEventTypeAsString = cursor.getString(columnLearningEventType);
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

        WordLearningEventGson wordLearningEventGson = new WordLearningEventGson();
        wordLearningEventGson.setId(id);
        wordLearningEventGson.setAndroidId(androidId);
        wordLearningEventGson.setPackageName(packageName);
        wordLearningEventGson.setTime(time);
        wordLearningEventGson.setWordId(wordId);
        wordLearningEventGson.setWordText(wordText);
        wordLearningEventGson.setLearningEventType(learningEventType);

        return wordLearningEventGson;
    }
}
