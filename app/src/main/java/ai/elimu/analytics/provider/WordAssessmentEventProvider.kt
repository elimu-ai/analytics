package ai.elimu.analytics.provider

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.utils.BundleKeys
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import timber.log.Timber
import androidx.core.net.toUri

class WordAssessmentEventProvider : ContentProvider() {
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

        val context = context
        Timber.i("context: $context")
        if (context == null) {
            return null
        }

        val code = MATCHER.match(uri)
        Timber.i("code: $code")
        when (code) {
            CODE_EVENTS -> {
                // Get the Room Cursor
                val roomDb = RoomDb.getDatabase(context)
                val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
                val cursor = wordAssessmentEventDao.loadAllOrderedByTimestampDesc()
                Timber.i("cursor: $cursor")
                cursor.setNotificationUri(context.contentResolver, uri)
                cursor.extras = prepareBundle()
                return cursor
            }
            CODE_EVENTS_BY_WORD_ID -> {
                // Extract the Word ID from the URI
                val pathSegments = uri.pathSegments
                Timber.i("pathSegments: $pathSegments")
                val wordIdAsString = pathSegments[2]
                val wordId = wordIdAsString.toLong()
                Timber.i("wordId: $wordId")

                // Get the Room Cursor
                val roomDb = RoomDb.getDatabase(context)
                val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
                val cursor = wordAssessmentEventDao.loadAllOrderedByTimestampDesc(wordId)
                Timber.i("cursor: $cursor")
                cursor.setNotificationUri(context.contentResolver, uri)
                cursor.extras = prepareBundle()
                return cursor
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    /**
     * Prepare database column names needed by the Cursor-to-Gson converter in the `:utils` module.
     */
    private fun prepareBundle(): Bundle {
        Timber.i("prepareBundle")
        val bundle = Bundle().apply {
            putInt("version_code", BuildConfig.VERSION_CODE)
            putString(BundleKeys.KEY_ID, WordAssessmentEvent::id.name)
            putString(BundleKeys.KEY_ANDROID_ID, WordAssessmentEvent::androidId.name)
            putString(BundleKeys.KEY_PACKAGE_NAME, WordAssessmentEvent::packageName.name)
            putString(BundleKeys.KEY_TIMESTAMP, WordAssessmentEvent::time.name)
            putString(BundleKeys.KEY_MASTERY_SCORE, WordAssessmentEvent::masteryScore.name)
            putString(BundleKeys.KEY_TIME_SPENT_MS, WordAssessmentEvent::timeSpentMs.name)
            putString(BundleKeys.KEY_ADDITIONAL_DATA, WordAssessmentEvent::additionalData.name)
            putString(BundleKeys.KEY_WORD_TEXT, WordAssessmentEvent::wordText.name)
            putString(BundleKeys.KEY_WORD_ID, WordAssessmentEvent::wordId.name)
        }
        return bundle
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
            BuildConfig.APPLICATION_ID + ".provider.word_assessment_event_provider"
        private const val TABLE = "events"
        private const val CODE_EVENTS = 1
        private const val CODE_EVENTS_BY_WORD_ID = 2

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS)
            MATCHER.addURI(AUTHORITY, "$TABLE/by-word-id/#", CODE_EVENTS_BY_WORD_ID)
        }
    }
}
