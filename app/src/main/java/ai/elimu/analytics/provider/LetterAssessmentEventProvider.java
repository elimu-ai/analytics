package ai.elimu.analytics.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import ai.elimu.analytics.BuildConfig;
import ai.elimu.analytics.dao.LetterAssessmentEventDao;
import ai.elimu.analytics.db.RoomDb;

public class LetterAssessmentEventProvider extends ContentProvider {

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider.letter_assessment_event_provider";
    private static final String TABLE = "events";
    private static final int CODE_EVENTS = 1;
    private static final int CODE_EVENTS_BY_LETTER_ID = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(AUTHORITY, TABLE, CODE_EVENTS);
        MATCHER.addURI(AUTHORITY, TABLE + "/by-letter-id/#", CODE_EVENTS_BY_LETTER_ID);
    }

    @Override
    public boolean onCreate() {
        Log.i(getClass().getName(), "onCreate");

        Uri eventsUri = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
        Log.i(getClass().getName(), "eventsUri: " + eventsUri);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(getClass().getName(), "query");

        Log.i(getClass().getName(), "uri: " + uri);
        Log.i(getClass().getName(), "projection: " + projection);
        Log.i(getClass().getName(), "selection: " + selection);
        Log.i(getClass().getName(), "selectionArgs: " + selectionArgs);
        Log.i(getClass().getName(), "sortOrder: " + sortOrder);

        Context context = getContext();
        Log.i(getClass().getName(), "context: " + context);
        if (context == null) {
            return null;
        }

        final int code = MATCHER.match(uri);
        Log.i(getClass().getName(), "code: " + code);
        if (code == CODE_EVENTS) {
            // Get the Room Cursor
            RoomDb roomDb = RoomDb.getDatabase(context);
            LetterAssessmentEventDao letterAssessmentEventDao = roomDb.letterAssessmentEventDao();
            Cursor cursor = letterAssessmentEventDao.loadAllOrderedByTimeDesc();
            Log.i(getClass().getName(), "cursor: " + cursor);
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else if (code == CODE_EVENTS_BY_LETTER_ID) {
            // Extract the Letter ID from the URI
            List<String> pathSegments = uri.getPathSegments();
            Log.i(getClass().getName(), "pathSegments: " + pathSegments);
            String letterIdAsString = pathSegments.get(2);
            Long letterId = Long.valueOf(letterIdAsString);
            Log.i(getClass().getName(), "letterId: " + letterId);

            // Get the Room Cursor
            RoomDb roomDb = RoomDb.getDatabase(context);
            LetterAssessmentEventDao letterAssessmentEventDao = roomDb.letterAssessmentEventDao();
            Cursor cursor = letterAssessmentEventDao.loadAllOrderedByTimeDesc(letterId);
            Log.i(getClass().getName(), "cursor: " + cursor);
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        Log.i(getClass().getName(), "getType");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(getClass().getName(), "insert");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(getClass().getName(), "delete");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(getClass().getName(), "update");

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
