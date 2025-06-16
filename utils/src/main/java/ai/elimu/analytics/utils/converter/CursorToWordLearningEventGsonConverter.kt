package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

/**
 * Converts from [Cursor] to [WordLearningEventGson] to make it easier for 3rd-party apps
 * to interact with the database `Cursor` in the `:app` module.
 */
object CursorToWordLearningEventGsonConverter {
    
    private const val TAG = "CursorToWordLearningEventGsonConverter"
    
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(TAG, "getWordLearningEventGson")

        Log.i(
            TAG,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val columnNameId = bundle.getString("column_name_id")
        Log.i(TAG, "columnNameId: ${columnNameId}")
        val columnId = cursor.getColumnIndex(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnNameAndroidId = bundle.getString("column_name_android_id")
        Log.i(TAG, "columnNameAndroidId: ${columnNameAndroidId}")
        val columnAndroidId = cursor.getColumnIndex(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            TAG,
            "androidId: \"$androidId\""
        )

        val columnNamePackageName = bundle.getString("column_name_package_name")
        Log.i(TAG, "columnNamePackageName: ${columnNamePackageName}")
        val columnPackageName = cursor.getColumnIndex(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            TAG,
            "packageName: \"$packageName\""
        )

        val columnNameTimestamp = bundle.getString("column_name_timestamp")
        Log.i(TAG, "columnNameTimestamp: ${columnNameTimestamp}")
        val columnTime = cursor.getColumnIndex(columnNameTimestamp)
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

        val columnNameWordId = bundle.getString("column_name_word_id")
        Log.i(TAG, "columnNameWordId: ${columnNameWordId}")
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        val wordId = cursor.getLong(columnWordId)
        Log.i(
            TAG,
            "wordId: $wordId"
        )

        val columnNameWordText = bundle.getString("column_name_word_text")
        Log.i(TAG, "columnNameWordText: ${columnNameWordText}")
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        val wordText = cursor.getString(columnWordText)
        Log.i(
            TAG,
            "wordText: \"$wordText\""
        )

        val columnNameLearningEventType = bundle.getString("column_name_learning_event_type")
        Log.i(TAG, "columnNameLearningEventType: ${columnNameLearningEventType}")
        val columnLearningEventType = cursor.getColumnIndex(columnNameLearningEventType)
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        Log.i(TAG, "learningEventTypeAsString: ${learningEventTypeAsString}")
        var learningEventType: LearningEventType? = null
        learningEventTypeAsString?.let {
            learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
            Log.i(TAG, "learningEventType: ${learningEventType}")
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
