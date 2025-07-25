package ai.elimu.analytics.provider

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.utils.BundleKeys
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import timber.log.Timber
import androidx.core.net.toUri

class NumberLearningEventProvider : ContentProvider() {
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
        if (code == CODE_EVENTS) {
            // Get the Room Cursor
            val roomDb = RoomDb.getDatabase(context)
            val numberLearningEventDao = roomDb.numberLearningEventDao()
            val cursor = numberLearningEventDao.loadAllOrderedByTimestampDesc()
            Timber.i("cursor: $cursor")
            cursor.setNotificationUri(context.contentResolver, uri)
            cursor.extras = prepareBundle()
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    /**
     * Prepare database column names needed by the Cursor-to-Gson converter in the `:utils` module.
     */
    private fun prepareBundle(): Bundle {
        Timber.i("prepareBundle")
        val bundle = Bundle().apply {
            putInt("version_code", BuildConfig.VERSION_CODE)
            putString(BundleKeys.KEY_ID, NumberLearningEvent::id.name)
            putString(BundleKeys.KEY_ANDROID_ID, NumberLearningEvent::androidId.name)
            putString(BundleKeys.KEY_PACKAGE_NAME, NumberLearningEvent::packageName.name)
            putString(BundleKeys.KEY_TIMESTAMP, NumberLearningEvent::timestamp.name)
            putString(BundleKeys.KEY_ADDITIONAL_DATA, NumberLearningEvent::additionalData.name)
            putString(BundleKeys.KEY_NUMBER_VALUE, NumberLearningEvent::numberValue.name)
            putString(BundleKeys.KEY_NUMBER_SYMBOL, NumberLearningEvent::numberSymbol.name)
            putString(BundleKeys.KEY_NUMBER_ID, NumberLearningEvent::numberId.name)
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
            BuildConfig.APPLICATION_ID + ".provider.number_learning_event_provider"
        private const val TABLE = "events"
        private const val CODE_EVENTS = 1

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS)
        }
    }
}
