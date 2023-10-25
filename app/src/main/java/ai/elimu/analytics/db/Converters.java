package ai.elimu.analytics.db;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Calendar;

import ai.elimu.model.v2.enums.analytics.LearningEventType;

/**
 * See https://developer.android.com/training/data-storage/room/referencing-data
 */
public class Converters {

    @TypeConverter
    public static Calendar fromLong(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long toLong(Calendar calendar) {
        return calendar.getTimeInMillis();
    }


    @TypeConverter
    public static LearningEventType fromString(String value) {
        LearningEventType learningEventType = null;
        if (!TextUtils.isEmpty(value)) {
            learningEventType = LearningEventType.valueOf(value);
        }
        return learningEventType;
    }

    @TypeConverter
    public static String toString(LearningEventType learningEventType) {
        String value = learningEventType.toString();
        return value;
    }


    @TypeConverter
    public static String[] fromArrayString(String value) {
        return value.substring(1, value.length() - 1).split(", ");
    }

    @TypeConverter
    public static String toString(String[] array) {
        return Arrays.toString(array);
    }
}
