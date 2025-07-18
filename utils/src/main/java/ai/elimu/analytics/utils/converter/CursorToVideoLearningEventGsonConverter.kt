package ai.elimu.analytics.utils.converter

import ai.elimu.analytics.utils.BundleKeys
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
    
    fun getVideoLearningEventGSON(cursor: Cursor): VideoLearningEventGson {
        Log.i(TAG, "getVideoLearningEventGson")

        Log.i(TAG,"cursor.columnNames.contentToString(): " + cursor.columnNames.contentToString())

        val bundle = cursor.extras
        Log.i(TAG, "bundle: ${bundle}")
        Log.i(TAG, "bundle version_code: ${bundle.getInt("version_code")}")

        val videoLearningEventGson = VideoLearningEventGson()

        val columnNameId = bundle.getString(BundleKeys.KEY_ID)
        val columnId = cursor.getColumnIndexOrThrow(columnNameId)
        val id = cursor.getLong(columnId)
        Log.i(TAG, "id: $id")
        videoLearningEventGson.id = id

        val columnNameAndroidId = bundle.getString(BundleKeys.KEY_ANDROID_ID)
        val columnAndroidId = cursor.getColumnIndexOrThrow(columnNameAndroidId)
        val androidId = cursor.getString(columnAndroidId)
        Log.i(TAG, "androidId: \"$androidId\"")
        videoLearningEventGson.androidId = androidId

        val columnNamePackageName = bundle.getString(BundleKeys.KEY_PACKAGE_NAME)
        val columnPackageName = cursor.getColumnIndexOrThrow(columnNamePackageName)
        val packageName = cursor.getString(columnPackageName)
        Log.i(TAG, "packageName: \"$packageName\"")
        videoLearningEventGson.packageName = packageName

        val columnNameTimestamp = bundle.getString(BundleKeys.KEY_TIMESTAMP)
        val columnTimestamp = cursor.getColumnIndexOrThrow(columnNameTimestamp)
        val timestampAsLong = cursor.getLong(columnTimestamp)
        val timestamp = Calendar.getInstance()
        timestamp.timeInMillis = timestampAsLong
        Log.i(TAG, "timestamp.time: " + timestamp.time)
        videoLearningEventGson.timestamp = timestamp

        val columnNameAdditionalData = bundle.getString(BundleKeys.KEY_ADDITIONAL_DATA)
        val columnAdditionalData: Int = cursor.getColumnIndex(columnNameAdditionalData)
        if (columnAdditionalData != -1) {
            val additionalData: String? = cursor.getString(columnAdditionalData)
            Log.i(TAG, "additionalData: $additionalData")
            videoLearningEventGson.additionalData = additionalData
        }

        val columnNameVideoTitle = bundle.getString(BundleKeys.KEY_VIDEO_TITLE)
        val columnVideoTitle = cursor.getColumnIndex(columnNameVideoTitle)
        if (columnVideoTitle != -1) {
            val videoTitle = cursor.getString(columnVideoTitle)
            Log.i(TAG, "videoTitle: \"$videoTitle\"")
            videoLearningEventGson.videoTitle = videoTitle
        }

        val columnNameVideoId = bundle.getString(BundleKeys.KEY_VIDEO_ID)
        val columnVideoId = cursor.getColumnIndex(columnNameVideoId)
        if (columnVideoId != -1) {
            val videoId = cursor.getLong(columnVideoId)
            Log.i(TAG, "videoId: \"$videoId\"")
            videoLearningEventGson.videoId = videoId
        }

        return videoLearningEventGson
    }
}
