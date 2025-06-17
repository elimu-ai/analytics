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
    const val COLUMN_NAME_WORD_TEXT = "column_name_word_text"
    const val COLUMN_NAME_WORD_ID = "column_name_word_id"
    // ☝️ WARNING
    
    fun getWordAssessmentEventGson(cursor: Cursor): WordAssessmentEventGson {
        Log.i(TAG, "getWordAssessmentEventGson")

        Log.i(TAG,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val columnNameId = bundle.getString(COLUMN_NAME_ID)
        Log.i(TAG, "columnNameId: ${columnNameId}")
        val columnId = cursor.getColumnIndex(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnNameAndroidId = bundle.getString(COLUMN_NAME_ANDROID_ID)
        Log.i(TAG, "columnNameAndroidId: ${columnNameAndroidId}")
        val columnAndroidId = cursor.getColumnIndex(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")

        val columnNamePackageName = bundle.getString(COLUMN_NAME_PACKAGE_NAME)
        Log.i(TAG, "columnNamePackageName: ${columnNamePackageName}")
        val columnPackageName = cursor.getColumnIndex(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")

        val columnNameTimestamp = bundle.getString(COLUMN_NAME_TIMESTAMP)
        Log.i(TAG, "columnNameTimestamp: ${columnNameTimestamp}")
        val columnTime = cursor.getColumnIndex(columnNameTimestamp)
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(TAG, "timeAsLong: $timeAsLong")
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(TAG, "time.getTime(): " + timestamp.time)

        val columnNameWordId = bundle.getString(COLUMN_NAME_WORD_ID)
        Log.i(TAG, "columnNameWordId: ${columnNameWordId}")
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        val wordId = cursor.getLong(columnWordId)
        Log.i(TAG, "wordId: $wordId")

        val columnNameWordText = bundle.getString(COLUMN_NAME_WORD_TEXT)
        Log.i(TAG, "columnNameWordText: ${columnNameWordText}")
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        val wordText = cursor.getString(columnWordText)
        Log.i(TAG, "wordText: \"$wordText\"")

        val columnNameMasteryScore = bundle.getString(COLUMN_NAME_MASTERY_SCORE)
        Log.i(TAG, "columnNameMasteryScore: ${columnNameMasteryScore}")
        val columnMasteryScore = cursor.getColumnIndex(columnNameMasteryScore)
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: $masteryScore")

        val columnNameTimeSpentMs = bundle.getString(COLUMN_NAME_TIME_SPENT_MS)
        Log.i(TAG, "columnNameTimeSpentMs: ${columnNameTimeSpentMs}")
        val columnTimeSpentMs = cursor.getColumnIndex(columnNameTimeSpentMs)
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: $timeSpentMs")

        val wordAssessmentEventGson = WordAssessmentEventGson().apply {
            this.id = id
            this.androidId = androidId
            this.packageName = packageName
            this.timestamp = timestamp
            this.wordId = wordId
            this.wordText = wordText
            this.masteryScore = masteryScore
            this.timeSpentMs = timeSpentMs
        }

        return wordAssessmentEventGson
    }
}
