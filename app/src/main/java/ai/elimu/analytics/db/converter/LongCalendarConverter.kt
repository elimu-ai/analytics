package ai.elimu.analytics.db.converter

import androidx.room.TypeConverter
import java.util.Calendar

class LongCalendarConverter {
    @TypeConverter
    fun fromLong(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(value)
        return calendar
    }

    @TypeConverter
    fun toLong(calendar: Calendar): Long {
        return calendar.getTimeInMillis()
    }
}
