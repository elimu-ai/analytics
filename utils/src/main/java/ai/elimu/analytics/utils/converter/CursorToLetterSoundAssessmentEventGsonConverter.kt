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

    // 👇 WARNING: To ensure backward compatibility, these column name values must never be renamed.
    const val COLUMN_NAME_ID = "column_name_id"
    const val COLUMN_NAME_ANDROID_ID = "column_name_android_id"
    const val COLUMN_NAME_PACKAGE_NAME = "column_name_package_name"
    const val COLUMN_NAME_TIMESTAMP = "column_name_timestamp"
    const val COLUMN_NAME_MASTERY_SCORE = "column_name_mastery_score"
    const val COLUMN_NAME_TIME_SPENT_MS = "column_name_time_spent_ms"
    const val COLUMN_NAME_LETTER_SOUND_LETTERS = "column_name_letter_sound_letters"
    const val COLUMN_NAME_LETTER_SOUND_SOUNDS = "column_name_letter_sound_sounds"
    const val COLUMN_NAME_LETTER_SOUND_ID = "column_name_letter_sound_id"
    // ☝️ WARNING

    @JvmStatic
    fun getLetterSoundAssessmentEventGson(cursor: Cursor): LetterSoundAssessmentEventGson {
        Log.i(TAG, "getLetterSoundAssessmentEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val letterSoundAssessmentEventGson = LetterSoundAssessmentEventGson()

        val columnId: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)
        val id: Long = cursor.getLong(columnId)
        Log.i(TAG, "id: ${id}")
        letterSoundAssessmentEventGson.id = id

        val columnAndroidId: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_ANDROID_ID)
        val androidId: String = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"${androidId}\"")
        letterSoundAssessmentEventGson.androidId = androidId

        val columnPackageName: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_PACKAGE_NAME)
        val packageName: String = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"${packageName}\"")
        letterSoundAssessmentEventGson.packageName = packageName

        val columnTimestamp: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_TIMESTAMP)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        Log.i(TAG, "timestampAsLong: ${timestampAsLong}")
        val timestamp: Calendar = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        letterSoundAssessmentEventGson.timestamp = timestamp

        val columnMasteryScore: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_MASTERY_SCORE)
        val masteryScore: Float = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: ${masteryScore}")
        letterSoundAssessmentEventGson.masteryScore = masteryScore

        val columnTimeSpentMs: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_TIME_SPENT_MS)
        val timeSpentMs: Long = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: ${timeSpentMs}")
        letterSoundAssessmentEventGson.timeSpentMs = timeSpentMs

//        val columnAdditionalData: Int = cursor.getColumnIndexOrThrow(COLUMN_NAME_ADDITIONAL_DATA)
//        val additionalData: String = cursor.getString(columnAdditionalData)
//        Log.i(TAG, "additionalData: ${additionalData}")
//        letterSoundAssessmentEventGson.additionalData = additionalData

        val columnLetterSoundLetters: Int = cursor.getColumnIndex(COLUMN_NAME_LETTER_SOUND_LETTERS)
        if (columnLetterSoundLetters != -1) {
            val letterSoundLetters: String = cursor.getString(columnLetterSoundLetters)
            Log.i(TAG, "letterSoundLetters: \"${letterSoundLetters}\"")
            letterSoundAssessmentEventGson.letterSoundLetters = letterSoundLetters
        }

        val columnLetterSoundSounds: Int = cursor.getColumnIndex(COLUMN_NAME_LETTER_SOUND_SOUNDS)
        if (columnLetterSoundSounds != -1) {
            val letterSoundSounds: String = cursor.getString(columnLetterSoundSounds)
            Log.i(TAG, "letterSoundSounds: \"${letterSoundSounds}\"")
            letterSoundAssessmentEventGson.letterSoundSounds = letterSoundSounds
        }

        val columnLetterSoundId: Int = cursor.getColumnIndex(COLUMN_NAME_LETTER_SOUND_ID)
        if (columnLetterSoundId != -1) {
            val letterSoundId: Long = cursor.getLong(columnLetterSoundId)
            Log.i(TAG, "letterSoundId: ${letterSoundId}")
            letterSoundAssessmentEventGson.letterSoundId = letterSoundId
        }

        return letterSoundAssessmentEventGson
    }
}
