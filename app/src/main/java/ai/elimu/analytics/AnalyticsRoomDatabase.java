package ai.elimu.analytics;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StoryBookLearningEvent.class}, version = 3000000, exportSchema = false)
public abstract class AnalyticsRoomDatabase extends RoomDatabase {

    private static volatile AnalyticsRoomDatabase INSTANCE;

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
