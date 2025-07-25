package ai.elimu.analytics.utils.converter

import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.model.v2.gson.analytics.NumberLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToNumberLearningEventGsonConverter {
    
    private const val TAG = "CursorToNumberLearningEventGsonConverter"
    
    fun getNumberLearningEventGson(cursor: Cursor): NumberLearningEventGson {
        Log.i(TAG, "getNumberLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val numberLearningEventGson = NumberLearningEventGson()

        val columnNameId = bundle.getString(BundleKeys.KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        numberLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(BundleKeys.KEY_ANDROID_ID)
        val columnAndroidId: Int = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        numberLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BundleKeys.KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        numberLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BundleKeys.KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        numberLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(BundleKeys.KEY_ADDITIONAL_DATA)
        val columnAdditionalData: Int = cursor.getColumnIndex(columnNameAdditionalData)
        if (columnAdditionalData != -1) {
            val additionalData: String? = cursor.getString(columnAdditionalData)
            Log.i(TAG, "additionalData: $additionalData")
            numberLearningEventGson.additionalData = additionalData
        }

        val columnNameNumberValue = bundle.getString(BundleKeys.KEY_NUMBER_VALUE)
        val columnNumberValue = cursor.getColumnIndex(columnNameNumberValue)
        if (columnNumberValue != -1) {
            val numberValue = cursor.getInt(columnNumberValue)
            Log.i(TAG, "numberValue: \"$numberValue\"")
            numberLearningEventGson.numberValue = numberValue
        }

        val columnNameNumberSymbol = bundle.getString(BundleKeys.KEY_NUMBER_SYMBOL)
        val columnNumberSymbol = cursor.getColumnIndex(columnNameNumberSymbol)
        if (columnNumberSymbol != -1) {
            val numberSymbol = cursor.getString(columnNumberSymbol)
            Log.i(TAG, "numberSymbol: \"$numberSymbol\"")
            numberLearningEventGson.numberSymbol = numberSymbol
        }

        val columnNameNumberId = bundle.getString(BundleKeys.KEY_NUMBER_ID)
        val columnNumberId = cursor.getColumnIndex(columnNameNumberId)
        if (columnNumberId != -1) {
            val numberId = cursor.getLong(columnNumberId)
            Log.i(TAG, "numberId: $numberId")
            numberLearningEventGson.numberId = numberId
        }

        return numberLearningEventGson
    }
}
