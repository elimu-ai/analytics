package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.NumberLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToNumberLearningEventGsonConverter {
    
    private const val TAG = "CursorToNumberLearningEventGsonConverter"

    // 👇 WARNING: To ensure backward compatibility, these column name values must never be renamed.
    const val COLUMN_NAME_ID = "column_name_id"
    const val COLUMN_NAME_ANDROID_ID = "column_name_android_id"
    const val COLUMN_NAME_PACKAGE_NAME = "column_name_package_name"
    const val COLUMN_NAME_TIMESTAMP = "column_name_timestamp"
    const val COLUMN_NAME_ADDITIONAL_DATA = "column_name_additional_data"
    const val COLUMN_NAME_LEARNING_EVENT_TYPE = "column_name_learning_event_type"
    const val COLUMN_NAME_NUMBER_VALUE = "column_name_number_value"
    const val COLUMN_NAME_NUMBER_SYMBOL = "column_name_number_symbol"
    const val COLUMN_NAME_NUMBER_ID = "column_name_number_id"
    // ☝️ WARNING
    
    fun getNumberLearningEventGson(cursor: Cursor): NumberLearningEventGson {
        Log.i(TAG, "getNumberLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val numberLearningEventGson = NumberLearningEventGson()

        val columnId = cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        numberLearningEventGson.id = id

        val columnAndroidId = cursor.getColumnIndexOrThrow(COLUMN_NAME_ANDROID_ID)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            TAG,
            "androidId: \"$androidId\""
        )
        numberLearningEventGson.androidId = androidId

        val columnPackageName = cursor.getColumnIndexOrThrow(COLUMN_NAME_PACKAGE_NAME)
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            TAG,
            "packageName: \"$packageName\""
        )
        numberLearningEventGson.packageName = packageName

        val columnTime = cursor.getColumnIndexOrThrow(COLUMN_NAME_TIMESTAMP)
        val timeAsLong = cursor.getLong(columnTime)
        Log.i(
            TAG,
            "timeAsLong: $timeAsLong"
        )
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(
            TAG,
            "time.getTime(): " + timestamp.time
        )
        numberLearningEventGson.timestamp = timestamp

        val columnAdditionalData = cursor.getColumnIndexOrThrow(COLUMN_NAME_ADDITIONAL_DATA)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: $additionalData")
        numberLearningEventGson.additionalData = additionalData

        val columnNumberId = cursor.getColumnIndex(COLUMN_NAME_NUMBER_ID)
        if (columnNumberId != -1) {
            val numberId = cursor.getLong(columnNumberId)
            Log.i(TAG, "numberId: $numberId")
            numberLearningEventGson.numberId = numberId
        }

        val columnNumberValue = cursor.getColumnIndex(COLUMN_NAME_NUMBER_VALUE)
        if (columnNumberValue != -1) {
            val numberValue = cursor.getInt(columnNumberValue)
            Log.i(TAG, "numberValue: \"$numberValue\"")
            numberLearningEventGson.numberValue = numberValue
        }

        val columnNumberSymbol = cursor.getColumnIndex(COLUMN_NAME_NUMBER_SYMBOL)
        if (columnNumberSymbol != -1) {
            val numberSymbol = cursor.getString(columnNumberSymbol)
            Log.i(TAG, "numberSymbol: \"$numberSymbol\"")
            numberLearningEventGson.symbol = numberSymbol
        }

        val columnLearningEventType = cursor.getColumnIndex(COLUMN_NAME_LEARNING_EVENT_TYPE)
        if (columnLearningEventType != -1) {
            val learningEventTypeAsString = cursor.getString(columnLearningEventType)
            Log.i(TAG, "learningEventTypeAsString: $learningEventTypeAsString")
            var learningEventType: LearningEventType? = null
            learningEventTypeAsString?.let {
                learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
            }
            Log.i(TAG, "learningEventType: $learningEventType")
            numberLearningEventGson.learningEventType = learningEventType
        }

        return numberLearningEventGson
    }
}
