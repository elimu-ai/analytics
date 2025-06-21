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

    // 👇 WARNING: To ensure backward compatibility, these column name values must never be renamed.
    const val COLUMN_NAME_ID = "column_name_id"
    const val COLUMN_NAME_ANDROID_ID = "column_name_android_id"
    const val COLUMN_NAME_PACKAGE_NAME = "column_name_package_name"
    const val COLUMN_NAME_TIMESTAMP = "column_name_timestamp"
    const val COLUMN_NAME_MASTERY_SCORE = "column_name_mastery_score"
    const val COLUMN_NAME_TIME_SPENT_MS = "column_name_time_spent_ms"
    const val COLUMN_NAME_ADDITIONAL_DATA = "column_name_additional_data"
    const val COLUMN_NAME_WORD_TEXT = "column_name_word_text"
    const val COLUMN_NAME_WORD_ID = "column_name_word_id"
    // ☝️ WARNING
    
    fun getWordAssessmentEventGson(cursor: Cursor): WordAssessmentEventGson {
        Log.i(TAG, "getWordAssessmentEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val wordAssessmentEventGson = WordAssessmentEventGson()

        val columnNameId = bundle.getString(COLUMN_NAME_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        wordAssessmentEventGson.id = id

        val columnNameAndroidId = bundle.getString(COLUMN_NAME_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        wordAssessmentEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(COLUMN_NAME_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        wordAssessmentEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(COLUMN_NAME_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        wordAssessmentEventGson.timestamp = timestamp

        val columnNameMasteryScore = bundle.getString(COLUMN_NAME_MASTERY_SCORE)
        val columnMasteryScore = cursor.getColumnIndexOrThrow(columnNameMasteryScore)
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: $masteryScore")
        wordAssessmentEventGson.masteryScore = masteryScore

        val columnNameTimeSpentMs = bundle.getString(COLUMN_NAME_TIME_SPENT_MS)
        val columnTimeSpentMs = cursor.getColumnIndexOrThrow(columnNameTimeSpentMs)
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: $timeSpentMs")
        wordAssessmentEventGson.timeSpentMs = timeSpentMs

        val columnAdditionalData: Int = cursor.getColumnIndex(COLUMN_NAME_ADDITIONAL_DATA)
        if (columnAdditionalData != -1) {
            val additionalData: String = cursor.getString(columnAdditionalData)
            Log.i(TAG, "additionalData: \"${additionalData}\"")
            wordAssessmentEventGson.additionalData = additionalData
        }

        val columnNameWordText = bundle.getString(COLUMN_NAME_WORD_TEXT)
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        if (columnWordText != -1) {
            val wordText = cursor.getString(columnWordText)
            Log.i(TAG, "wordText: \"$wordText\"")
            wordAssessmentEventGson.wordText = wordText
        }

        val columnNameWordId = bundle.getString(COLUMN_NAME_WORD_ID)
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        if (columnWordId != -1) {
            val wordId = cursor.getLong(columnWordId)
            Log.i(TAG, "wordId: $wordId")
            wordAssessmentEventGson.wordId = wordId
        }

        return wordAssessmentEventGson
    }
}
