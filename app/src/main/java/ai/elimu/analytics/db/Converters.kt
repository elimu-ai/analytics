package ai.elimu.analytics.db

import ai.elimu.model.v2.enums.analytics.LearningEventType
import android.text.TextUtils
import androidx.room.TypeConverter
import java.util.Calendar

/**
 * See [Referencing complex data using Room](https://developer.android.com/training/data-storage/room/referencing-data)
 */
object Converters {
    @JvmStatic
    @TypeConverter
    fun fromLong(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(value)
        return calendar
    }

    @JvmStatic
    @TypeConverter
    fun toLong(calendar: Calendar): Long {
        return calendar.getTimeInMillis()
    }


    @JvmStatic
    @TypeConverter
    fun fromString(value: String?): LearningEventType? {
        var learningEventType: LearningEventType? = null
        if (!TextUtils.isEmpty(value)) {
            learningEventType = LearningEventType.valueOf(value!!)
        }
        return learningEventType
    }

    @JvmStatic
    @TypeConverter
    fun toString(learningEventType: LearningEventType?): String? {
        return learningEventType?.toString()
    }


    @JvmStatic
    @TypeConverter
    fun fromArrayString(value: String): Array<String?> {
        return value.substring(1, value.length - 1).split(", ".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    @TypeConverter
    fun toString(array: Array<String?>?): String {
        return array.contentToString()
    }
}
