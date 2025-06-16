package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.VideoLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

object CursorToVideoLearningEventGsonConverter {
    
    private const val TAG = "CursorToVideoLearningEventGsonConverter"
    
    fun getVideoLearningEventGSON(cursor: Cursor): VideoLearningEventGson {
        Log.i(TAG, "getVideoLearningEventGson")

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
        Log.i(
            TAG,
            "additionalData: $additionalData"
        )

        val columnVideoTitle = cursor.getColumnIndex("videoTitle")
        val videoTitle = cursor.getString(columnVideoTitle)
        Log.i(
            TAG,
            "videoTitle: \"$videoTitle\""
        )

        val columnLearningEventType = cursor.getColumnIndex("learningEventType")
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
        Log.i(
            TAG,
            "learningEventType: $learningEventType"
        )

        val columnVideoId = cursor.getColumnIndex("videoId")
        val videoId = cursor.getLong(columnVideoId)
        Log.i(
            TAG,
            "videoId: \"$videoId\""
        )

        val videoLearningEventGson = VideoLearningEventGson().apply {
            this.id = id
            this.androidId = androidId
            this.packageName = packageName
            this.timestamp = timestamp
            this.additionalData = additionalData
            this.videoTitle = videoTitle
            this.learningEventType = learningEventType
            this.videoId = videoId
        }

        return videoLearningEventGson
    }
}
