package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToWordLearningEventGsonConverter {
    
    private const val TAG = "CursorToWordLearningEventGsonConverter"
    
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(TAG, "getWordLearningEventGson")

        Log.i(
            TAG,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val columnId = cursor.getColumnIndex("id")
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnAndroidId = cursor.getColumnIndex("androidId")
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            TAG,
            "androidId: \"$androidId\""
        )

        val columnPackageName = cursor.getColumnIndex("packageName")
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            TAG,
            "packageName: \"$packageName\""
        )

        val columnTime = cursor.getColumnIndex("time")
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(
            TAG,
            "timeAsLong: $timeAsLong"
        )
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(
            TAG,
            "time.getTime(): " + timestamp.time
        )

        // TODO: add column `additionalData`
        // Depends on https://github.com/elimu-ai/analytics/issues/313

        val columnWordId = cursor.getColumnIndex("wordId")
        val wordId = cursor.getLong(columnWordId)
        Log.i(
            TAG,
            "wordId: $wordId"
        )

        val columnWordText = cursor.getColumnIndex("wordText")
        val wordText = cursor.getString(columnWordText)
        Log.i(
            TAG,
            "wordText: \"$wordText\""
        )

        val columnLearningEventType = cursor.getColumnIndex("learningEventType")
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        var learningEventType: LearningEventType? = null
        if (learningEventTypeAsString.isNotBlank()) {
            learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
            Log.i(TAG, "learningEventType: $learningEventType")
        }

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
