package ai.elimu.analytics.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ai.elimu.analytics.BuildConfig;
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

@Database(entities = {StoryBookLearningEvent.class}, version = BuildConfig.VERSION_CODE, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class AnalyticsRoomDatabase extends RoomDatabase {

    public abstract StoryBookLearningEventDao storyBookLearningEventDao();

    private static volatile AnalyticsRoomDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AnalyticsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnalyticsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    AnalyticsRoomDatabase.class,
                                    "analytics_database"
                            )
                            .addMigrations(
                                    MIGRATION_3000000_3000001,
                                    MIGRATION_3000001_3000002,
                                    MIGRATION_3000002_3000004
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_3000000_3000001 = new Migration(3000000, 3000001) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i(getClass().getName(), "migrate (3000000 --> 3000001)");

            Calendar timestamp = Calendar.getInstance();
            String sql = "ALTER TABLE StoryBookLearningEvent ADD COLUMN timestamp INTEGER NOT NULL DEFAULT " + timestamp.getTimeInMillis();
            Log.i(getClass().getName(), "sql: " + sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_3000001_3000002 = new Migration(3000001, 3000002) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i(getClass().getName(), "migrate (3000001 --> 3000002)");

            String sql = "DELETE FROM StoryBookLearningEvent";
            Log.i(getClass().getName(), "sql: " + sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_3000002_3000004 = new Migration(3000003, 3000004) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i(getClass().getName(), "migrate (3000002 --> 3000004)");

            String sql = "ALTER TABLE StoryBookLearningEvent ADD COLUMN androidId TEXT NOT NULL DEFAULT 'asdf1234'";
            Log.i(getClass().getName(), "sql: " + sql);
            database.execSQL(sql);
        }
    };
}
