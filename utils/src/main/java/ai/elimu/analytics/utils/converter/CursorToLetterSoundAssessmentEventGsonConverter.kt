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

    // ⚠️ WARNING: To ensure backward compatibility, these bundle key values must never be renamed!
    const val BUNDLE_KEY_ID = "bundle_key_id"
    const val BUNDLE_KEY_ANDROID_ID = "bundle_key_android_id"
    const val BUNDLE_KEY_PACKAGE_NAME = "bundle_key_package_name"
    const val BUNDLE_KEY_TIMESTAMP = "bundle_key_timestamp"
    const val BUNDLE_KEY_MASTERY_SCORE = "bundle_key_mastery_score"
    const val BUNDLE_KEY_TIME_SPENT_MS = "bundle_key_time_spent_ms"
    const val BUNDLE_KEY_ADDITIONAL_DATA = "bundle_key_additional_data"
    const val BUNDLE_KEY_LETTER_SOUND_LETTERS = "bundle_key_letter_sound_letters"
    const val BUNDLE_KEY_LETTER_SOUND_SOUNDS = "bundle_key_letter_sound_sounds"
    const val BUNDLE_KEY_LETTER_SOUND_ID = "bundle_key_letter_sound_id"
    // ⚠️ WARNING

    @JvmStatic
    fun getLetterSoundAssessmentEventGson(cursor: Cursor): LetterSoundAssessmentEventGson {
        Log.i(TAG, "getLetterSoundAssessmentEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val letterSoundAssessmentEventGson = LetterSoundAssessmentEventGson()

        val columnNameId = bundle.getString(BUNDLE_KEY_ID);
        val columnId: Int = cursor.getColumnIndexOrThrow(columnNameId)
        val id: Long = cursor.getLong(columnId)
        Log.i(TAG, "id: ${id}")
        letterSoundAssessmentEventGson.id = id

        val columnNameAndroidId = bundle.getString(BUNDLE_KEY_ANDROID_ID)
        val columnAndroidId: Int = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId: String = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"${androidId}\"")
        letterSoundAssessmentEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BUNDLE_KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName: String = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"${packageName}\"")
        letterSoundAssessmentEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BUNDLE_KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        Log.i(TAG, "timestampAsLong: ${timestampAsLong}")
        val timestamp: Calendar = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        letterSoundAssessmentEventGson.timestamp = timestamp

        val columnNameMasteryScore = bundle.getString(BUNDLE_KEY_MASTERY_SCORE)
        val columnMasteryScore = cursor.getColumnIndexOrThrow(columnNameMasteryScore)
        val masteryScore: Float = cursor.getFloat(columnMasteryScore)
        Log.i(TAG, "masteryScore: ${masteryScore}")
        letterSoundAssessmentEventGson.masteryScore = masteryScore

        val columnNameTimeSpentMs = bundle.getString(BUNDLE_KEY_TIME_SPENT_MS)
        val columnTimeSpentMs = cursor.getColumnIndexOrThrow(columnNameTimeSpentMs)
        val timeSpentMs: Long = cursor.getLong(columnTimeSpentMs)
        Log.i(TAG, "timeSpentMs: ${timeSpentMs}")
        letterSoundAssessmentEventGson.timeSpentMs = timeSpentMs

        val columnNameAdditionalData = bundle.getString(BUNDLE_KEY_ADDITIONAL_DATA)
        val columnAdditionalData = cursor.getColumnIndexOrThrow(columnNameAdditionalData)
        if (columnAdditionalData != -1) {
            val additionalData: String = cursor.getString(columnAdditionalData)
            Log.i(TAG, "additionalData: \"${additionalData}\"")
            letterSoundAssessmentEventGson.additionalData = additionalData
        }

        val columnNameLetterSoundLetters = bundle.getString(BUNDLE_KEY_LETTER_SOUND_LETTERS)
        val columnLetterSoundLetters: Int = cursor.getColumnIndex(columnNameLetterSoundLetters)
        if (columnLetterSoundLetters != -1) {
            val letterSoundLetters: String = cursor.getString(columnLetterSoundLetters)
            Log.i(TAG, "letterSoundLetters: \"${letterSoundLetters}\"")
            letterSoundAssessmentEventGson.letterSoundLetters = letterSoundLetters
        }

        val columnNameLetterSoundSounds = bundle.getString(BUNDLE_KEY_LETTER_SOUND_SOUNDS)
        val columnLetterSoundSounds: Int = cursor.getColumnIndex(columnNameLetterSoundSounds)
        if (columnLetterSoundSounds != -1) {
            val letterSoundSounds: String = cursor.getString(columnLetterSoundSounds)
            Log.i(TAG, "letterSoundSounds: \"${letterSoundSounds}\"")
            letterSoundAssessmentEventGson.letterSoundSounds = letterSoundSounds
        }

        val columnNameLetterSoundId = bundle.getString(BUNDLE_KEY_LETTER_SOUND_ID)
        val columnLetterSoundId: Int = cursor.getColumnIndex(columnNameLetterSoundId)
        if (columnLetterSoundId != -1) {
            val letterSoundId: Long = cursor.getLong(columnLetterSoundId)
            Log.i(TAG, "letterSoundId: ${letterSoundId}")
            letterSoundAssessmentEventGson.letterSoundId = letterSoundId
        }

        return letterSoundAssessmentEventGson
    }
}
