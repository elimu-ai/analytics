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

    // 👇 WARNING: To ensure backward compatibility, these column name values must never be renamed.
    const val COLUMN_NAME_ID = "column_name_id"
    const val COLUMN_NAME_ANDROID_ID = "column_name_android_id"
    const val COLUMN_NAME_PACKAGE_NAME = "column_name_package_name"
    const val COLUMN_NAME_TIMESTAMP = "column_name_timestamp"
    const val COLUMN_NAME_ADDITIONAL_DATA = "column_name_additional_data"
    const val COLUMN_NAME_LEARNING_EVENT_TYPE = "column_name_learning_event_type"
    const val COLUMN_NAME_WORD_TEXT = "column_name_word_text"
    const val COLUMN_NAME_WORD_ID = "column_name_word_id"
    // ☝️ WARNING
    
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(TAG, "getWordLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val wordLearningEventGson = WordLearningEventGson()

        val columnNameId = bundle.getString(COLUMN_NAME_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        wordLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(COLUMN_NAME_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        wordLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(COLUMN_NAME_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        wordLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(COLUMN_NAME_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        wordLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(COLUMN_NAME_ADDITIONAL_DATA)
        val columnAdditionalData = cursor.getColumnIndexOrThrow(columnNameAdditionalData)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: " + additionalData)
        wordLearningEventGson.additionalData = additionalData

        val columnNameWordId = bundle.getString(COLUMN_NAME_WORD_ID)
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        if (columnWordId != -1) {
            val wordId = cursor.getLong(columnWordId)
            Log.i(TAG, "wordId: $wordId")
            wordLearningEventGson.wordId = wordId
        }

        val columnNameWordText = bundle.getString(COLUMN_NAME_WORD_TEXT)
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        if (columnWordText != -1) {
            val wordText = cursor.getString(columnWordText)
            Log.i(TAG, "wordText: \"$wordText\"")
            wordLearningEventGson.wordText = wordText
        }

        val columnNameLearningEventType = bundle.getString(COLUMN_NAME_LEARNING_EVENT_TYPE)
        val columnLearningEventType = cursor.getColumnIndex(columnNameLearningEventType)
        if (columnLearningEventType != -1) {
            val learningEventTypeAsString = cursor.getString(columnLearningEventType)
            learningEventTypeAsString?.let {
                val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
                Log.i(TAG, "learningEventType: ${learningEventType}")
                wordLearningEventGson.learningEventType = learningEventType
            }
        }

        return wordLearningEventGson
    }
}
