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

    // ⚠️ WARNING: To ensure backward compatibility, these bundle key values must never be renamed!
    const val BUNDLE_KEY_ID = "bundle_key_id"
    const val BUNDLE_KEY_ANDROID_ID = "bundle_key_android_id"
    const val BUNDLE_KEY_PACKAGE_NAME = "bundle_key_package_name"
    const val BUNDLE_KEY_TIMESTAMP = "bundle_key_timestamp"
    const val BUNDLE_KEY_ADDITIONAL_DATA = "bundle_key_additional_data"
    const val BUNDLE_KEY_LEARNING_EVENT_TYPE = "bundle_key_learning_event_type"
    const val BUNDLE_KEY_WORD_TEXT = "bundle_key_word_text"
    const val BUNDLE_KEY_WORD_ID = "bundle_key_word_id"
    // ⚠️ WARNING
    
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(TAG, "getWordLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val wordLearningEventGson = WordLearningEventGson()

        val columnNameId = bundle.getString(BUNDLE_KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        wordLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(BUNDLE_KEY_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        wordLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BUNDLE_KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        wordLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BUNDLE_KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        wordLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(BUNDLE_KEY_ADDITIONAL_DATA)
        val columnAdditionalData = cursor.getColumnIndexOrThrow(columnNameAdditionalData)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: " + additionalData)
        wordLearningEventGson.additionalData = additionalData

        val columnNameLearningEventType = bundle.getString(BUNDLE_KEY_LEARNING_EVENT_TYPE)
        val columnLearningEventType = cursor.getColumnIndex(columnNameLearningEventType)
        if (columnLearningEventType != -1) {
            val learningEventTypeAsString = cursor.getString(columnLearningEventType)
            learningEventTypeAsString?.let {
                val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
                Log.i(TAG, "learningEventType: ${learningEventType}")
                wordLearningEventGson.learningEventType = learningEventType
            }
        }

        val columnNameWordText = bundle.getString(BUNDLE_KEY_WORD_TEXT)
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        if (columnWordText != -1) {
            val wordText = cursor.getString(columnWordText)
            Log.i(TAG, "wordText: \"$wordText\"")
            wordLearningEventGson.wordText = wordText
        }

        val columnNameWordId = bundle.getString(BUNDLE_KEY_WORD_ID)
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        if (columnWordId != -1) {
            val wordId = cursor.getLong(columnWordId)
            Log.i(TAG, "wordId: $wordId")
            wordLearningEventGson.wordId = wordId
        }

        return wordLearningEventGson
    }
}
