package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToLetterAssessmentEventGsonConverter {

    private val TAG = CursorToLetterAssessmentEventGsonConverter::class.java.name

    @JvmStatic
    fun getLetterAssessmentEventGson(cursor: Cursor): LetterAssessmentEventGson {
        Log.i(TAG, "getLetterAssessmentEventGson")

        Log.i(TAG,"Arrays.toString(cursor.getColumnNames()): "
                + cursor.columnNames.contentToString())

        val columnId = cursor.getColumnIndex("id")
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnAndroidId = cursor.getColumnIndex("androidId")
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")

        val columnPackageName = cursor.getColumnIndex("packageName")
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")

        val columnTime = cursor.getColumnIndex("time")
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(TAG, "timeAsLong: $timeAsLong")
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(TAG, "time.getTime(): " + timestamp.time)

        val columnLetterId = cursor.getColumnIndex("letterId")
        val letterId = cursor.getLong(columnLetterId)
        Log.i(TAG, "letterId: $letterId")

        val columnLetterText = cursor.getColumnIndex("letterText")
        val letterText = cursor.getString(columnLetterText)
        Log.i(TAG, "letterText: \"$letterText\"")

        val columnMasteryScore = cursor.getColumnIndex("masteryScore")
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: $masteryScore")

        val columnTimeSpentMs = cursor.getColumnIndex("timeSpentMs")
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: $masteryScore")

        val letterAssessmentEventGson = LetterAssessmentEventGson().apply {
            this.id = id
            this.androidId = androidId
            this.packageName = packageName
            this.timestamp = timestamp
            this.letterId = letterId
            this.letterText = letterText
            this.masteryScore = masteryScore
            this.timeSpentMs = timeSpentMs
        }

        return letterAssessmentEventGson
    }
}
