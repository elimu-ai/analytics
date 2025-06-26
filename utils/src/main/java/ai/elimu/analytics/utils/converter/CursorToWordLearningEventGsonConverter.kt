package ai.elimu.analytics.utils.converter

import ai.elimu.analytics.utils.BundleKeys
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
    
    fun getWordLearningEventGson(cursor: Cursor): WordLearningEventGson {
        Log.i(TAG, "getWordLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val wordLearningEventGson = WordLearningEventGson()

        val columnNameId = bundle.getString(BundleKeys.KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        wordLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(BundleKeys.KEY_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        wordLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BundleKeys.KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        wordLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BundleKeys.KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        wordLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(BundleKeys.KEY_ADDITIONAL_DATA)
        val columnAdditionalData = cursor.getColumnIndexOrThrow(columnNameAdditionalData)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: " + additionalData)
        wordLearningEventGson.additionalData = additionalData

        val columnNameWordText = bundle.getString(BundleKeys.KEY_WORD_TEXT)
        val columnWordText = cursor.getColumnIndex(columnNameWordText)
        if (columnWordText != -1) {
            val wordText = cursor.getString(columnWordText)
            Log.i(TAG, "wordText: \"$wordText\"")
            wordLearningEventGson.wordText = wordText
        }

        val columnNameWordId = bundle.getString(BundleKeys.KEY_WORD_ID)
        val columnWordId = cursor.getColumnIndex(columnNameWordId)
        if (columnWordId != -1) {
            val wordId = cursor.getLong(columnWordId)
            Log.i(TAG, "wordId: $wordId")
            wordLearningEventGson.wordId = wordId
        }

        return wordLearningEventGson
    }
}
