package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.NumberLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToNumberLearningEventGsonConverter {
    
    private const val TAG = "CursorToNumberLearningEventGsonConverter"
    
    fun getNumberLearningEventGson(cursor: Cursor): NumberLearningEventGson {
        Log.i(TAG, "getNumberLearningEventGson")

        Log.i(
            TAG,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val columnId = cursor.getColumnIndex("id")
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnAndroidId = cursor.getColumnIndex("androidId")
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            TAG,
            "androidId: \"$androidId\""
        )

        val columnPackageName = cursor.getColumnIndex("packageName")
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            TAG,
            "packageName: \"$packageName\""
        )

        val columnTime = cursor.getColumnIndex("time")
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

        val columnAdditionalData = cursor.getColumnIndex("additionalData")
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: $additionalData")

        val columnNumberId = cursor.getColumnIndex("numberId")
        val numberId = cursor.getLong(columnNumberId)
        Log.i(TAG, "numberId: $numberId")

        val columnNumberValue = cursor.getColumnIndex("numberValue")
        val numberValue = cursor.getInt(columnNumberValue)
        Log.i(TAG, "numberValue: \"$numberValue\"")

        val columnNumberSymbol = cursor.getColumnIndex("numberSymbol")
        val numberSymbol = cursor.getString(columnNumberSymbol)
        Log.i(TAG, "numberSymbol: \"$numberSymbol\"")

        val columnLearningEventType = cursor.getColumnIndex("learningEventType")
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        Log.i(TAG, "learningEventTypeAsString: $learningEventTypeAsString")
        var learningEventType: LearningEventType? = null
        learningEventTypeAsString?.let {
            learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
            Log.i(TAG, "learningEventType: $learningEventType")
        }

        val numberLearningEventGson = NumberLearningEventGson().apply {
            this.id = id
            this.androidId = androidId
            this.packageName = packageName
            this.timestamp = timestamp
            this.numberId = numberId
            this.symbol = numberSymbol
            this.numberValue = numberValue
            this.learningEventType = learningEventType
            this.additionalData = additionalData
        }

        return numberLearningEventGson
    }
}
