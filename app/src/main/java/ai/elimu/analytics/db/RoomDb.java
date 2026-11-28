package ai.elimu.analytics.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ai.elimu.analytics.dao.LetterSoundAssessmentEventDao;
import ai.elimu.analytics.dao.LetterSoundLearningEventDao;
import ai.elimu.analytics.dao.LiteracyAssessmentEventDao;
import ai.elimu.analytics.dao.LiteracyLearningEventDao;
import ai.elimu.analytics.dao.NumberAssessmentEventDao;
import ai.elimu.analytics.dao.NumberLearningEventDao;
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.VideoLearningEventDao;
import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.converter.LongCalendarConverter;
import ai.elimu.analytics.db.converter.StringArrayConverter;
import ai.elimu.analytics.db.converter.StringListConverter;
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent;
import ai.elimu.analytics.entity.LetterSoundLearningEvent;
import ai.elimu.analytics.entity.LiteracyAssessmentEvent;
import ai.elimu.analytics.entity.LiteracyLearningEvent;
import ai.elimu.analytics.entity.NumberAssessmentEvent;
import ai.elimu.analytics.entity.NumberLearningEvent;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.VideoLearningEvent;
import ai.elimu.analytics.entity.WordAssessmentEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import timber.log.Timber;

@Database(version = 3, entities = {
        LiteracyAssessmentEvent.class,
        LiteracyLearningEvent.class,

        LetterSoundAssessmentEvent.class,
        LetterSoundLearningEvent.class,

        WordAssessmentEvent.class,
        WordLearningEvent.class,

        NumberAssessmentEvent.class,
        NumberLearningEvent.class,

        StoryBookLearningEvent.class,

        VideoLearningEvent.class
})
@TypeConverters({
        LongCalendarConverter.class,
        StringArrayConverter.class,
        StringListConverter.class
})
public abstract class RoomDb extends RoomDatabase {
    public abstract LiteracyAssessmentEventDao literacyAssessmentEventDao();
    public abstract LiteracyLearningEventDao literacyLearningEventDao();

    public abstract LetterSoundAssessmentEventDao letterSoundAssessmentEventDao();
    public abstract LetterSoundLearningEventDao letterSoundLearningEventDao();

    public abstract WordAssessmentEventDao wordAssessmentEventDao();
    public abstract WordLearningEventDao wordLearningEventDao();

    public abstract NumberAssessmentEventDao numberAssessmentEventDao();
    public abstract NumberLearningEventDao numberLearningEventDao();

    public abstract StoryBookLearningEventDao storyBookLearningEventDao();

    public abstract VideoLearningEventDao videoLearningEventDao();

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
                            .addMigrations(
                                    // See https://developer.android.com/training/data-storage/room/migrating-db-versions
                                    MIGRATION_1_2,
                                    MIGRATION_2_3
                            )
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 2)");

            String sql = "CREATE TABLE IF NOT EXISTS `LiteracyLearningEvent` (`literacySkill` TEXT NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 3)");

            String sql = "CREATE TABLE IF NOT EXISTS `LiteracyAssessmentEvent` (`literacySkill` TEXT NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `masteryScore` REAL NOT NULL, `timeSpentMs` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };
}
