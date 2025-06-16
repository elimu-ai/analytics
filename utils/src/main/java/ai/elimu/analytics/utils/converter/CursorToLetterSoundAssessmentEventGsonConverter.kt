package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.gson.analytics.LetterSoundAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

/**
 * Converts from [Cursor] to [LetterSoundAssessmentEventGson] to make it easier for 3rd-party apps 
 * to interact with the database `Cursor` in the `:app` module.
 */
object CursorToLetterSoundAssessmentEventGsonConverter {

    private val TAG = CursorToLetterSoundAssessmentEventGsonConverter::class.java.name

    @JvmStatic
    fun getLetterSoundAssessmentEventGson(cursor: Cursor): LetterSoundAssessmentEventGson {
        Log.i(TAG, "getLetterSoundAssessmentEventGson")

        Log.i(TAG,"Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString())

        val columnId: Int = cursor.getColumnIndex("id")
        val id: Long = cursor.getLong(columnId)
        Log.i(TAG, "id: ${id}")

        val columnAndroidId: Int = cursor.getColumnIndex("androidId")
        val androidId: String = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"${androidId}\"")

        val columnPackageName: Int = cursor.getColumnIndex("packageName")
        val packageName: String = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"${packageName}\"")

        val columnTimestamp: Int = cursor.getColumnIndex("time")
        val timestampAsLong = cursor.getLong(columnTimestamp)
        Log.i(TAG, "timestampAsLong: ${timestampAsLong}")
        val timestamp: Calendar = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)

        val columnLetterSoundLetters: Int = cursor.getColumnIndex("letterSoundLetters")
        val letterSoundLetters: String = cursor.getString(columnLetterSoundLetters)
        Log.i(TAG, "letterSoundLetters: \"${letterSoundLetters}\"")

        val columnLetterSoundSounds: Int = cursor.getColumnIndex("letterSoundSounds")
        val letterSoundSounds: String = cursor.getString(columnLetterSoundSounds)
        Log.i(TAG, "letterSoundSounds: \"${letterSoundSounds}\"")

        val columnLetterSoundId: Int = cursor.getColumnIndex("letterSoundId")
        val letterSoundId: Long = cursor.getLong(columnLetterSoundId)
        Log.i(TAG, "letterSoundId: ${letterSoundId}")

        val columnMasteryScore: Int = cursor.getColumnIndex("masteryScore")
        val masteryScore: Float = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: ${masteryScore}")

        val columnTimeSpentMs: Int = cursor.getColumnIndex("timeSpentMs")
        val timeSpentMs: Long = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: ${timeSpentMs}")

//        val columnAdditionalData: Int = cursor.getColumnIndex("additionalData")
//        val additionalData: String = cursor.getString(columnAdditionalData)
//        Log.i(TAG, "additionalData: ${additionalData}")

        val letterSoundAssessmentEventGson = LetterSoundAssessmentEventGson().apply {
            this.id = id
            this.androidId = androidId
            this.packageName = packageName
            this.timestamp = timestamp
            this.letterSoundLetters = letterSoundLetters
            this.letterSoundSounds = letterSoundSounds
            this.letterSoundId = letterSoundId
            this.masteryScore = masteryScore
            this.timeSpentMs = timeSpentMs
//            this.additionalData = additionalData
        }

        return letterSoundAssessmentEventGson
    }
}
