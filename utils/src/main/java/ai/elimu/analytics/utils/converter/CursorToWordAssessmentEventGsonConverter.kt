package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

/**
 * Converts from [Cursor] to [WordAssessmentEventGson] to make it easier for 3rd-party apps
 * to interact with the database `Cursor` in the `:app` module.
 */
object CursorToWordAssessmentEventGsonConverter {
    
    private const val TAG = "CursorToWordAssessmentEventGsonConverter"

    // ⚠️ WARNING: To ensure backward compatibility, these bundle key values must never be renamed!
    const val BUNDLE_KEY_ID = "bundle_key_id"
    const val BUNDLE_KEY_ANDROID_ID = "bundle_key_android_id"
    const val BUNDLE_KEY_PACKAGE_NAME = "bundle_key_package_name"
    const val BUNDLE_KEY_TIMESTAMP = "bundle_key_timestamp"
    const val BUNDLE_KEY_MASTERY_SCORE = "bundle_key_mastery_score"
    const val BUNDLE_KEY_TIME_SPENT_MS = "bundle_key_time_spent_ms"
    const val BUNDLE_KEY_ADDITIONAL_DATA = "bundle_key_additional_data"
    const val BUNDLE_KEY_WORD_TEXT = "bundle_key_word_text"
    const val BUNDLE_KEY_WORD_ID = "bundle_key_word_id"
    // ⚠️ WARNING
    
    fun getWordAssessmentEventGson(cursor: Cursor): WordAssessmentEventGson {
        Log.i(TAG, "getWordAssessmentEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val wordAssessmentEventGson = WordAssessmentEventGson()

        val columnNameId = bundle.getString(BUNDLE_KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        wordAssessmentEventGson.id = id

        val columnNameAndroidId = bundle.getString(BUNDLE_KEY_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        wordAssessmentEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BUNDLE_KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        wordAssessmentEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BUNDLE_KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        wordAssessmentEventGson.timestamp = timestamp

        val columnNameMasteryScore = bundle.getString(BUNDLE_KEY_MASTERY_SCORE)
        val columnMasteryScore = cursor.getColumnIndexOrThrow(columnNameMasteryScore)
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: $masteryScore")
        wordAssessmentEventGson.masteryScore = masteryScore

        val columnNameTimeSpentMs = bundle.getString(BUNDLE_KEY_TIME_SPENT_MS)
        val columnTimeSpentMs = cursor.getColumnIndexOrThrow(columnNameTimeSpentMs)
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: $timeSpentMs")
        wordAssessmentEventGson.timeSpentMs = timeSpentMs

        val columnAdditionalData: Int = cursor.getColumnIndex(BUNDLE_KEY_ADDITIONAL_DATA)
        if (columnAdditionalData != -1) {
            val additionalData: String = cursor.getString(columnAdditionalData)
            Log.i(TAG, "additionalData: \"${additionalData}\"")
            wordAssessmentEventGson.additionalData = additionalData
        }

        val columnNameWordText = bundle.getString(BUNDLE_KEY_WORD_TEXT)
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        if (columnWordText != -1) {
            val wordText = cursor.getString(columnWordText)
            Log.i(TAG, "wordText: \"$wordText\"")
            wordAssessmentEventGson.wordText = wordText
        }

        val columnNameWordId = bundle.getString(BUNDLE_KEY_WORD_ID)
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        if (columnWordId != -1) {
            val wordId = cursor.getLong(columnWordId)
            Log.i(TAG, "wordId: $wordId")
            wordAssessmentEventGson.wordId = wordId
        }

        return wordAssessmentEventGson
    }
}
