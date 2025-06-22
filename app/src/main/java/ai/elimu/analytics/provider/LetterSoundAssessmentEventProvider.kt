package ai.elimu.analytics.provider

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import ai.elimu.analytics.utils.converter.CursorToLetterSoundAssessmentEventGsonConverter
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import timber.log.Timber
import androidx.core.net.toUri

class LetterSoundAssessmentEventProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = BuildConfig.APPLICATION_ID + ".provider.letter_sound_assessment_event_provider"
        private const val TABLE = "events"
        private const val CODE_EVENTS = 1
        private const val CODE_EVENTS_BY_LETTER_SOUND_ID = 2

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS)
            MATCHER.addURI(AUTHORITY, "${TABLE}/by-letter-sound-id/#", CODE_EVENTS_BY_LETTER_SOUND_ID)
        }
    }

    override fun onCreate(): Boolean {
        Timber.i("onCreate")

        val eventsUri = ("content://${AUTHORITY}/${TABLE}").toUri()
        Timber.i("eventsUri: ${eventsUri}")

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

        Timber.i("uri: ${uri}")
        Timber.i("projection: ${projection}")
        Timber.i("selection: ${selection}")
        Timber.i("selectionArgs: ${selectionArgs}")
        Timber.i("sortOrder: ${sortOrder}")

        val context = context
        Timber.i("context: ${context}")
        if (context == null) {
            return null
        }

        val code = MATCHER.match(uri)
        Timber.i("code: ${code}")
        when (code) {
            CODE_EVENTS -> {
                // Get the Room Cursor
                val roomDb = RoomDb.getDatabase(context)
                val letterSoundAssessmentEventDao = roomDb.letterSoundAssessmentEventDao()
                val cursor = letterSoundAssessmentEventDao.loadAllCursor()
                Timber.i("cursor: ${cursor}")
                cursor.setNotificationUri(context.contentResolver, uri)
                val bundle = Bundle().apply {
                    putInt("version_code", BuildConfig.VERSION_CODE)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ID, LetterSoundAssessmentEvent::id.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ANDROID_ID, LetterSoundAssessmentEvent::androidId.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_PACKAGE_NAME, LetterSoundAssessmentEvent::packageName.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_TIMESTAMP, LetterSoundAssessmentEvent::time.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_MASTERY_SCORE, LetterSoundAssessmentEvent::masteryScore.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_TIME_SPENT_MS, LetterSoundAssessmentEvent::timeSpentMs.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ADDITIONAL_DATA, LetterSoundAssessmentEvent::additionalData.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_LETTERS, LetterSoundAssessmentEvent::letterSoundLetters.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_SOUNDS, LetterSoundAssessmentEvent::letterSoundSounds.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_ID, LetterSoundAssessmentEvent::letterSoundId.name)
                }
                cursor.extras = bundle
                return cursor
            }
            CODE_EVENTS_BY_LETTER_SOUND_ID -> {
                // Extract the LetterSound ID from the URI
                val pathSegments = uri.pathSegments
                Timber.i("pathSegments: ${pathSegments}")
                val letterSoundIdAsString = pathSegments[2]
                val letterSoundId: Long = letterSoundIdAsString.toLong()
                Timber.i("letterSoundId: ${letterSoundId}")

                // Get the Room Cursor
                val roomDb = RoomDb.getDatabase(context)
                val letterSoundAssessmentEventDao = roomDb.letterSoundAssessmentEventDao()
                val cursor = letterSoundAssessmentEventDao.loadAllCursor(letterSoundId)
                Timber.i("cursor: ${cursor}")
                cursor.setNotificationUri(context.contentResolver, uri)
                val bundle = Bundle().apply {
                    putInt("version_code", BuildConfig.VERSION_CODE)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ID, LetterSoundAssessmentEvent::id.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ANDROID_ID, LetterSoundAssessmentEvent::androidId.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_PACKAGE_NAME, LetterSoundAssessmentEvent::packageName.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_TIMESTAMP, LetterSoundAssessmentEvent::time.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_MASTERY_SCORE, LetterSoundAssessmentEvent::masteryScore.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_TIME_SPENT_MS, LetterSoundAssessmentEvent::timeSpentMs.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_ADDITIONAL_DATA, LetterSoundAssessmentEvent::additionalData.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_LETTERS, LetterSoundAssessmentEvent::letterSoundLetters.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_SOUNDS, LetterSoundAssessmentEvent::letterSoundSounds.name)
                    putString(CursorToLetterSoundAssessmentEventGsonConverter.BUNDLE_KEY_LETTER_SOUND_ID, LetterSoundAssessmentEvent::letterSoundId.name)
                }
                cursor.extras = bundle
                return cursor
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: ${uri}")
            }
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
}
