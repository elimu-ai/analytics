package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.NumberLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToNumberLearningEventGsonConverter {
    
    private const val TAG = "CursorToNumberLearningEventGsonConverter"

    // ⚠️ WARNING: To ensure backward compatibility, these bundle key values must never be renamed!
    const val BUNDLE_KEY_ID = "bundle_key_id"
    const val BUNDLE_KEY_ANDROID_ID = "bundle_key_android_id"
    const val BUNDLE_KEY_PACKAGE_NAME = "bundle_key_package_name"
    const val BUNDLE_KEY_TIMESTAMP = "bundle_key_timestamp"
    const val BUNDLE_KEY_ADDITIONAL_DATA = "bundle_key_additional_data"
    const val BUNDLE_KEY_LEARNING_EVENT_TYPE = "bundle_key_learning_event_type"
    const val BUNDLE_KEY_NUMBER_VALUE = "bundle_key_number_value"
    const val BUNDLE_KEY_NUMBER_SYMBOL = "bundle_key_number_symbol"
    const val BUNDLE_KEY_NUMBER_ID = "bundle_key_number_id"
    // ⚠️ WARNING
    
    fun getNumberLearningEventGson(cursor: Cursor): NumberLearningEventGson {
        Log.i(TAG, "getNumberLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val numberLearningEventGson = NumberLearningEventGson()

        val columnId = cursor.getColumnIndexOrThrow(BUNDLE_KEY_ID)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        numberLearningEventGson.id = id

        val columnAndroidId = cursor.getColumnIndexOrThrow(BUNDLE_KEY_ANDROID_ID)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        numberLearningEventGson.androidId = androidId

        val columnPackageName = cursor.getColumnIndexOrThrow(BUNDLE_KEY_PACKAGE_NAME)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        numberLearningEventGson.packageName = packageName

        val columnTimestamp = cursor.getColumnIndexOrThrow(BUNDLE_KEY_TIMESTAMP)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        numberLearningEventGson.timestamp = timestamp

        val columnAdditionalData = cursor.getColumnIndexOrThrow(BUNDLE_KEY_ADDITIONAL_DATA)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: $additionalData")
        numberLearningEventGson.additionalData = additionalData

        val columnLearningEventType = cursor.getColumnIndex(BUNDLE_KEY_LEARNING_EVENT_TYPE)
        if (columnLearningEventType != -1) {
            val learningEventTypeAsString = cursor.getString(columnLearningEventType)
            learningEventTypeAsString?.let {
                val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
                Log.i(TAG, "learningEventType: $learningEventType")
                numberLearningEventGson.learningEventType = learningEventType
            }
        }

        val columnNumberValue = cursor.getColumnIndex(BUNDLE_KEY_NUMBER_VALUE)
        if (columnNumberValue != -1) {
            val numberValue = cursor.getInt(columnNumberValue)
            Log.i(TAG, "numberValue: \"$numberValue\"")
            numberLearningEventGson.numberValue = numberValue
        }

        val columnNumberSymbol = cursor.getColumnIndex(BUNDLE_KEY_NUMBER_SYMBOL)
        if (columnNumberSymbol != -1) {
            val numberSymbol = cursor.getString(columnNumberSymbol)
            Log.i(TAG, "numberSymbol: \"$numberSymbol\"")
            numberLearningEventGson.numberSymbol = numberSymbol
        }

        val columnNumberId = cursor.getColumnIndex(BUNDLE_KEY_NUMBER_ID)
        if (columnNumberId != -1) {
            val numberId = cursor.getLong(columnNumberId)
            Log.i(TAG, "numberId: $numberId")
            numberLearningEventGson.numberId = numberId
        }

        return numberLearningEventGson
    }
}
