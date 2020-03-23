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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

@Database(entities = {StoryBookLearningEvent.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RoomDb extends RoomDatabase {

    public abstract StoryBookLearningEventDao storyBookLearningEventDao();

    private static volatile RoomDb INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static RoomDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    RoomDb.class,
                                    "analytics_db"
                            )
//                            .addMigrations(
//                                    // See https://developer.android.com/training/data-storage/room/migrating-db-versions
//                                    MIGRATION_1_2
//                            )
                            .build();
                }
            }
        }

        return INSTANCE;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            Log.i(getClass().getName(), "migrate (" + database.getVersion() + " --> 2)");
//
//            String sql = "...";
//            Log.i(getClass().getName(), "sql: " + sql);
//            database.execSQL(sql);
//        }
//    };
}
