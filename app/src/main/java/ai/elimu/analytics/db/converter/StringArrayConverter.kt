package ai.elimu.analytics.db.converter

import androidx.room.TypeConverter

class StringArrayConverter {
    @TypeConverter
    fun fromString(value: String): Array<String?> {
        return value.substring(1, value.length - 1).split(", ".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    @TypeConverter
    fun toString(array: Array<String?>?): String {
        return array.contentToString()
    }
}
