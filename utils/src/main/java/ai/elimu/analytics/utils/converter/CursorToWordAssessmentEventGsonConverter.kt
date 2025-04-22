package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToWordAssessmentEventGsonConverter {
    fun getWordAssessmentEventGson(cursor: Cursor): WordAssessmentEventGson {
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "getWordAssessmentEventGson"
        )

        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val columnId = cursor.getColumnIndex("id")
        val id = cursor.getLong(columnId)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "id: $id"
        )

        val columnAndroidId = cursor.getColumnIndex("androidId")
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "androidId: \"$androidId\""
        )

        val columnPackageName = cursor.getColumnIndex("packageName")
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "packageName: \"$packageName\""
        )

        val columnTime = cursor.getColumnIndex("time")
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "timeAsLong: $timeAsLong"
        )
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "time.getTime(): " + timestamp.time
        )

        val columnWordId = cursor.getColumnIndex("wordId")
        val wordId = cursor.getLong(columnWordId)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "wordId: $wordId"
        )

        val columnWordText = cursor.getColumnIndex("wordText")
        val wordText = cursor.getString(columnWordText)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "wordText: \"$wordText\""
        )

        val columnMasteryScore = cursor.getColumnIndex("masteryScore")
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "masteryScore: $masteryScore"
        )

        val columnTimeSpentMs = cursor.getColumnIndex("timeSpentMs")
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(
            CursorToWordAssessmentEventGsonConverter::class.java.name,
            "timeSpentMs: $masteryScore"
        )

        val wordAssessmentEventGson = WordAssessmentEventGson()
        wordAssessmentEventGson.id = id
        wordAssessmentEventGson.androidId = androidId
        wordAssessmentEventGson.packageName = packageName
        wordAssessmentEventGson.timestamp = timestamp
        wordAssessmentEventGson.wordId = wordId
        wordAssessmentEventGson.wordText = wordText
        wordAssessmentEventGson.masteryScore = masteryScore
        wordAssessmentEventGson.timeSpentMs = timeSpentMs

        return wordAssessmentEventGson
    }
}
