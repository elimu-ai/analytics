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

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val videoLearningEventGson = VideoLearningEventGson()

        val columnNameId = bundle.getString(COLUMN_NAME_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        videoLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(COLUMN_NAME_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        videoLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(COLUMN_NAME_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        videoLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(COLUMN_NAME_TIMESTAMP)
        val columnTime = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timeAsLong = cursor.getLong(columnTime)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timeAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        videoLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(COLUMN_NAME_ADDITIONAL_DATA)
        val columnAdditionalData = cursor.getColumnIndexOrThrow(columnNameAdditionalData)
        val additionalData = cursor.getString(columnAdditionalData)
        Log.i(TAG, "additionalData: $additionalData")
        videoLearningEventGson.additionalData = additionalData

        val columnNameVideoTitle = bundle.getString(COLUMN_NAME_VIDEO_TITLE)
        val columnVideoTitle = cursor.getColumnIndex(columnNameVideoTitle)
        if (columnVideoTitle != -1) {
            val videoTitle = cursor.getString(columnVideoTitle)
            Log.i(TAG, "videoTitle: \"$videoTitle\"")
            videoLearningEventGson.videoTitle = videoTitle
        }

        val columnNameLearningEventType = bundle.getString(COLUMN_NAME_LEARNING_EVENT_TYPE)
        val columnLearningEventType = cursor.getColumnIndex(columnNameLearningEventType)
        if (columnLearningEventType != -1) {
            val learningEventTypeAsString = cursor.getString(columnLearningEventType)
            learningEventTypeAsString?.let {
                val learningEventType = LearningEventType.valueOf(learningEventTypeAsString)
                Log.i(TAG, "learningEventType: $learningEventType")
                videoLearningEventGson.learningEventType = learningEventType
            }
        }

        val columnNameVideoId = bundle.getString(COLUMN_NAME_VIDEO_ID)
        val columnVideoId = cursor.getColumnIndex(columnNameVideoId)
        if (columnVideoId != -1) {
            val videoId = cursor.getLong(columnVideoId)
            Log.i(TAG, "videoId: \"$videoId\"")
            videoLearningEventGson.videoId = videoId
        }

        return videoLearningEventGson
    }
}
