package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToWordLearningEventGsonConverter {
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(CursorToWordLearningEventGsonConverter::class.java.name, "getWordLearningEventGson")

        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val columnId = cursor.getColumnIndex("id")
        val id = cursor.getLong(columnId)
        Log.i(CursorToWordLearningEventGsonConverter::class.java.name, "id: $id")

        val columnAndroidId = cursor.getColumnIndex("androidId")
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "androidId: \"$androidId\""
        )

        val columnPackageName = cursor.getColumnIndex("packageName")
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "packageName: \"$packageName\""
        )

        val columnTime = cursor.getColumnIndex("time")
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "timeAsLong: $timeAsLong"
        )
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "time.getTime(): " + timestamp.time
        )

        val columnWordId = cursor.getColumnIndex("wordId")
        val wordId = cursor.getLong(columnWordId)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "wordId: $wordId"
        )

        val columnWordText = cursor.getColumnIndex("wordText")
        val wordText = cursor.getString(columnWordText)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "wordText: \"$wordText\""
        )

        val columnLearningEventType = cursor.getColumnIndex("learningEventType")
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
        Log.i(
            CursorToWordLearningEventGsonConverter::class.java.name,
            "learningEventType: $learningEventType"
        )

        val wordLearningEventGson = WordLearningEventGson()
        wordLearningEventGson.id = id
        wordLearningEventGson.androidId = androidId
        wordLearningEventGson.packageName = packageName
        wordLearningEventGson.timestamp = timestamp
        wordLearningEventGson.wordId = wordId
        wordLearningEventGson.wordText = wordText
        wordLearningEventGson.learningEventType = learningEventType

        return wordLearningEventGson
    }
}
