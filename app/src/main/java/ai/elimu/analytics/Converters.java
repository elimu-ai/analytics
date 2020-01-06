package ai.elimu.analytics;

import androidx.room.TypeConverter;

import java.util.Calendar;

/**
 * See https://developer.android.com/training/data-storage/room/referencing-data
 */
public class Converters {

    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long toTimestamp(Calendar calendar) {
        return calendar.getTimeInMillis();
    }
}
