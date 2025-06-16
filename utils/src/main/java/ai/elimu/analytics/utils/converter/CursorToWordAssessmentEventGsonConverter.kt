package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToWordAssessmentEventGsonConverter {
    
    private const val TAG = "CursorToWordAssessmentEventGsonConverter"
    
    fun getWordAssessmentEventGson(cursor: Cursor): WordAssessmentEventGson {
        Log.i(TAG, "getWordAssessmentEventGson")

        Log.i(TAG,
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
        Log.i(TAG, "androidId: \"$androidId\"")

        val columnNamePackageName = bundle.getString("column_name_package_name")
        Log.i(TAG, "columnNamePackageName: ${columnNamePackageName}")
        val columnPackageName = cursor.getColumnIndex(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")

        val columnNameTimestamp = bundle.getString("column_name_timestamp")
        Log.i(TAG, "columnNameTimestamp: ${columnNameTimestamp}")
        val columnTime = cursor.getColumnIndex(columnNameTimestamp)
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(TAG, "timeAsLong: $timeAsLong")
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(TAG, "time.getTime(): " + timestamp.time)

        val columnNameWordId = bundle.getString("column_name_word_id")
        Log.i(TAG, "columnNameWordId: ${columnNameWordId}")
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        val wordId = cursor.getLong(columnWordId)
        Log.i(TAG, "wordId: $wordId")

        val columnNameWordText = bundle.getString("column_name_word_text")
        Log.i(TAG, "columnNameWordText: ${columnNameWordText}")
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        val wordText = cursor.getString(columnWordText)
        Log.i(TAG, "wordText: \"$wordText\"")

        val columnNameMasteryScore = bundle.getString("column_name_mastery_score")
        Log.i(TAG, "columnNameMasteryScore: ${columnNameMasteryScore}")
        val columnMasteryScore = cursor.getColumnIndex(columnNameMasteryScore)
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: $masteryScore")

        val columnNameTimeSpentMs = bundle.getString("column_name_time_spent_ms")
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
