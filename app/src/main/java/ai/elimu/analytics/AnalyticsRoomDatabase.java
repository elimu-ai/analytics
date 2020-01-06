package ai.elimu.analytics;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {StoryBookLearningEvent.class}, version = 3000000, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class AnalyticsRoomDatabase extends RoomDatabase {

    public abstract StoryBookLearningEventDao storyBookLearningEventDao();

    private static volatile AnalyticsRoomDatabase INSTANCE;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static AnalyticsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnalyticsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    AnalyticsRoomDatabase.class,
                                    "analytics_database"
                            ).build();
                }
            }
        }
        return INSTANCE;
    }
}
