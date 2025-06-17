package ai.elimu.analytics.utils.converter

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.analytics.VideoLearningEventGson
import android.database.Cursor
import android.util.Log
import java.util.Calendar

/**
 * Converts from [Cursor] to [VideoLearningEventGson] to make it easier for 3rd-party apps
 * to interact with the database `Cursor` in the `:app` module.
 */
object CursorToVideoLearningEventGsonConverter {
    
    private const val TAG = "CursorToVideoLearningEventGsonConverter"

    // 👇 WARNING: To ensure backward compatibility, these column name values must never be renamed.
    const val COLUMN_NAME_ID = "column_name_id"
    const val COLUMN_NAME_ANDROID_ID = "column_name_android_id"
    const val COLUMN_NAME_PACKAGE_NAME = "column_name_package_name"
    const val COLUMN_NAME_TIMESTAMP = "column_name_timestamp"
    const val COLUMN_NAME_ADDITIONAL_DATA = "column_name_additional_data"
    const val COLUMN_NAME_LEARNING_EVENT_TYPE = "column_name_learning_event_type"
    const val COLUMN_NAME_VIDEO_TITLE = "column_name_video_title"
    const val COLUMN_NAME_VIDEO_ID = "column_name_video_id"
    // ☝️ WARNING
    
    fun getVideoLearningEventGSON(cursor: Cursor): VideoLearningEventGson {
        Log.i(TAG, "getVideoLearningEventGson")

        Log.i(
            TAG,
            "Arrays.toString(cursor.getColumnNames()): " + cursor.columnNames.contentToString()
        )

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val columnNameId = bundle.getString(COLUMN_NAME_ID)
        Log.i(TAG, "columnNameId: ${columnNameId}")
        val columnId = cursor.getColumnIndex(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")

        val columnNameAndroidId = bundle.getString(COLUMN_NAME_ANDROID_ID)
        Log.i(TAG, "columnNameAndroidId: ${columnNameAndroidId}")
        val columnAndroidId = cursor.getColumnIndex(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(
            TAG,
            "androidId: \"$androidId\""
        )

        val columnNamePackageName = bundle.getString(COLUMN_NAME_PACKAGE_NAME)
        Log.i(TAG, "columnNamePackageName: ${columnNamePackageName}")
        val columnPackageName = cursor.getColumnIndex(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(
            TAG,
            "packageName: \"$packageName\""
        )

        val columnNameTimestamp = bundle.getString(COLUMN_NAME_TIMESTAMP)
        Log.i(TAG, "columnNameTimestamp: ${columnNameTimestamp}")
        val columnTime = cursor.getColumnIndex(columnNameTimestamp)
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

        val columnNameAdditionalData = bundle.getString(COLUMN_NAME_ADDITIONAL_DATA)
        Log.i(TAG, "columnNameAdditionalData: ${columnNameAdditionalData}")
        val columnAdditionalData = cursor.getColumnIndex(columnNameAdditionalData)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(
            TAG,
            "additionalData: $additionalData"
        )

        val columnNameVideoTitle = bundle.getString(COLUMN_NAME_VIDEO_TITLE)
        Log.i(TAG, "columnNameVideoTitle: ${columnNameVideoTitle}")
        val columnVideoTitle = cursor.getColumnIndex(columnNameVideoTitle)
        val videoTitle = cursor.getString(columnVideoTitle)
        Log.i(
            TAG,
            "videoTitle: \"$videoTitle\""
        )

        val columnNameLearningEventType = bundle.getString(COLUMN_NAME_LEARNING_EVENT_TYPE)
        Log.i(TAG, "columnNameLearningEventType: ${columnNameLearningEventType}")
        val columnLearningEventType = cursor.getColumnIndex(columnNameLearningEventType)
        val learningEventTypeAsString = cursor.getString(columnLearningEventType)
        val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
        Log.i(
            TAG,
            "learningEventType: $learningEventType"
        )

        val columnNameVideoId = bundle.getString(COLUMN_NAME_VIDEO_ID)
        Log.i(TAG, "columnNameVideoId: ${columnNameVideoId}")
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
