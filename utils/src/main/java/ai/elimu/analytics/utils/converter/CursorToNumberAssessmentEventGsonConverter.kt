package ai.elimu.analytics.utils.converter

import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.model.v2.gson.analytics.NumberAssessmentEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

/**
 * Converts from [Cursor] to [NumberAssessmentEventGson] to make it easier for 3rd-party apps
 * to interact with the database `Cursor` in the `:app` module.
 */
object CursorToNumberAssessmentEventGsonConverter {
    
    fun getGson(cursor: Cursor): NumberAssessmentEventGson {
        Log.i(this::class.simpleName, "getGson")

        Log.i(this::class.simpleName,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(this::class.simpleName, "bundle: ${bundle}")
        Log.i(this::class.simpleName, "bundle version_code: ${bundle.getInt("version_code")}")

        val numberAssessmentEventGson = NumberAssessmentEventGson()

        val columnNameId = bundle.getString(BundleKeys.KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(this::class.simpleName, "id: ${id}")
        numberAssessmentEventGson.id = id

        val columnNameAndroidId = bundle.getString(BundleKeys.KEY_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(this::class.simpleName, "androidId: \"${androidId}\"")
        numberAssessmentEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BundleKeys.KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(this::class.simpleName, "packageName: \"${packageName}\"")
        numberAssessmentEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BundleKeys.KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(this::class.simpleName, "timestamp.time: ${timestamp.time}")
        numberAssessmentEventGson.timestamp = timestamp

        val columnNameMasteryScore = bundle.getString(BundleKeys.KEY_MASTERY_SCORE)
        val columnMasteryScore = cursor.getColumnIndexOrThrow(columnNameMasteryScore)
        val masteryScore = cursor.getFloat(columnMasteryScore)
        Log.i(this::class.simpleName, "masteryScore: ${masteryScore}")
        numberAssessmentEventGson.masteryScore = masteryScore

        val columnNameTimeSpentMs = bundle.getString(BundleKeys.KEY_TIME_SPENT_MS)
        val columnTimeSpentMs = cursor.getColumnIndexOrThrow(columnNameTimeSpentMs)
        val timeSpentMs = cursor.getLong(columnTimeSpentMs)
        Log.i(this::class.simpleName, "timeSpentMs: ${timeSpentMs}")
        numberAssessmentEventGson.timeSpentMs = timeSpentMs

        val columnNameAdditionalData = bundle.getString(BundleKeys.KEY_ADDITIONAL_DATA)
        val columnAdditionalData: Int = cursor.getColumnIndex(columnNameAdditionalData)
        if (columnAdditionalData != -1) {
            val additionalData: String? = cursor.getString(columnAdditionalData)
            Log.i(this::class.simpleName, "additionalData: ${additionalData}")
            numberAssessmentEventGson.additionalData = additionalData
        }

        val columnNameNumberValue = bundle.getString(BundleKeys.KEY_NUMBER_VALUE)
        val columnNumberValue = cursor.getColumnIndex(columnNameNumberValue)
        if (columnNumberValue != -1) {
            val numberValue: Int = cursor.getInt(columnNumberValue)
            Log.i(this::class.simpleName, "numberValue: ${numberValue}")
            numberAssessmentEventGson.numberValue = numberValue
        }

        val columnNameNumberId = bundle.getString(BundleKeys.KEY_NUMBER_ID)
        val columnNumberId = cursor.getColumnIndex(columnNameNumberId)
        if (columnNumberId != -1) {
            val numberId = cursor.getLong(columnNumberId)
            Log.i(this::class.simpleName, "numberId: ${numberId}")
            numberAssessmentEventGson.numberId = numberId
        }

        return numberAssessmentEventGson
    }
}
