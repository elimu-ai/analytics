package ai.elimu.analytics.provider

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.WordLearningEvent
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import timber.log.Timber
import androidx.core.net.toUri

class WordLearningEventProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        Timber.i("onCreate")

        val eventsUri = ("content://$AUTHORITY/$TABLE").toUri()
        Timber.i("eventsUri: $eventsUri")

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        Timber.i("query")

        Timber.i("uri: $uri")
        Timber.i("projection: $projection")
        Timber.i("selection: $selection")
        Timber.i("selectionArgs: $selectionArgs")
        Timber.i("sortOrder: $sortOrder")

        val context = context
        Timber.i("context: $context")
        if (context == null) {
            return null
        }

        val code = MATCHER.match(uri)
        Timber.i("code: $code")
        if (code == CODE_EVENTS) {
            // Get the Room Cursor
            val roomDb = RoomDb.getDatabase(context)
            val wordLearningEventDao = roomDb.wordLearningEventDao()
            val cursor = wordLearningEventDao.loadAllOrderedByTime()
            Timber.i("cursor: $cursor")
            cursor.setNotificationUri(context.contentResolver, uri)
            val bundle = Bundle().apply {
                putInt("version_code", BuildConfig.VERSION_CODE)
                putString("column_name_id", WordLearningEvent::id.name)
                putString("column_name_android_id", WordLearningEvent::androidId.name)
                putString("column_name_package_name", WordLearningEvent::packageName.name)
                putString("column_name_timestamp", WordLearningEvent::time.name)
                putString("column_name_learning_event_type", WordLearningEvent::learningEventType.name)
                putString("column_name_additional_data", WordLearningEvent::additionalData.name)
                putString("column_name_word_text", WordLearningEvent::wordText.name)
                putString("column_name_word_id", WordLearningEvent::wordId.name)
            }
            cursor.extras = bundle
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        Timber.i("getType")

        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Timber.i("insert")

        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Timber.i("delete")

        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        Timber.i("update")

        throw UnsupportedOperationException("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY =
            BuildConfig.APPLICATION_ID + ".provider.word_learning_event_provider"
        private const val TABLE = "events"
        private const val CODE_EVENTS = 1

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS)
        }
    }
}
