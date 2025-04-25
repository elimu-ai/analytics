package ai.elimu.analytics.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

import ai.elimu.analytics.BuildConfig;
import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;
import timber.log.Timber;

public class WordAssessmentEventProvider extends ContentProvider {

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider.word_assessment_event_provider";
    private static final String TABLE = "events";
    private static final int CODE_EVENTS = 1;
    private static final int CODE_EVENTS_BY_WORD_ID = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS);
        MATCHER.addURI(AUTHORITY, TABLE + "/by-word-id/#", CODE_EVENTS_BY_WORD_ID);
    }

    @Override
    public boolean onCreate() {
        Timber.i("onCreate");

        Uri eventsUri = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
        Timber.i("eventsUri: " + eventsUri);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Timber.i("query");

        Timber.i("uri: " + uri);
        Timber.i("projection: " + projection);
        Timber.i("selection: " + selection);
        Timber.i("selectionArgs: " + selectionArgs);
        Timber.i("sortOrder: " + sortOrder);

        Context context = getContext();
        Timber.i("context: " + context);
        if (context == null) {
            return null;
        }

        final int code = MATCHER.match(uri);
        Timber.i("code: " + code);
        if (code == CODE_EVENTS) {
            // Get the Room Cursor
            RoomDb roomDb = RoomDb.getDatabase(context);
            WordAssessmentEventDao wordAssessmentEventDao = roomDb.wordAssessmentEventDao();
            Cursor cursor = wordAssessmentEventDao.loadAllOrderedByTimeDesc();
            Timber.i("cursor: " + cursor);
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else if (code == CODE_EVENTS_BY_WORD_ID) {
            // Extract the Word ID from the URI
            List<String> pathSegments = uri.getPathSegments();
            Timber.i("pathSegments: " + pathSegments);
            String wordIdAsString = pathSegments.get(2);
            Long wordId = Long.valueOf(wordIdAsString);
            Timber.i("wordId: " + wordId);

            // Get the Room Cursor
            RoomDb roomDb = RoomDb.getDatabase(context);
            WordAssessmentEventDao wordAssessmentEventDao = roomDb.wordAssessmentEventDao();
            Cursor cursor = wordAssessmentEventDao.loadAllOrderedByTimeDesc(wordId);
            Timber.i("cursor: " + cursor);
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        Timber.i("getType");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Timber.i("insert");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Timber.i("delete");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Timber.i("update");

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
