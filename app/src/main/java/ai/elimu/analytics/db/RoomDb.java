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
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent;
import ai.elimu.analytics.entity.LetterSoundLearningEvent;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.VideoLearningEvent;
import ai.elimu.analytics.entity.WordAssessmentEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import timber.log.Timber;

@Database(version = 12, entities = {LetterSoundAssessmentEvent.class, LetterSoundLearningEvent.class, WordLearningEvent.class, WordAssessmentEvent.class, StoryBookLearningEvent.class, VideoLearningEvent.class})
@TypeConverters({Converters.class})
public abstract class RoomDb extends RoomDatabase {
    public abstract LetterSoundAssessmentEventDao letterSoundAssessmentEventDao();
    public abstract LetterSoundLearningEventDao letterSoundLearningEventDao();
    public abstract WordLearningEventDao wordLearningEventDao();
    public abstract WordAssessmentEventDao wordAssessmentEventDao();
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
                            .addMigrations(
                                    // See https://developer.android.com/training/data-storage/room/migrating-db-versions
                                    MIGRATION_1_2,
                                    MIGRATION_2_3,
                                    MIGRATION_3_4,
                                    MIGRATION_4_5,
                                    MIGRATION_5_6,
                                    MIGRATION_6_7,
                                    MIGRATION_7_8,
                                    MIGRATION_8_9,
                                    MIGRATION_9_10,
                                    MIGRATION_10_11,
                                    MIGRATION_11_12
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

            String sql = "CREATE TABLE IF NOT EXISTS `WordLearningEvent` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `wordId` INTEGER, `wordText` TEXT NOT NULL, `learningEventType` TEXT NOT NULL)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 3)");

            String sql = "CREATE TABLE IF NOT EXISTS `WordAssessmentEvent` (`wordId` INTEGER, `wordText` TEXT NOT NULL, `masteryScore` REAL NOT NULL, `timeSpentMs` INTEGER NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 4)");

            String sql = "CREATE TABLE IF NOT EXISTS `LetterLearningEvent` (`letterId` INTEGER, `letterText` TEXT NOT NULL, `learningEventType` TEXT NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 5)");

            String sql = "CREATE TABLE IF NOT EXISTS `LetterAssessmentEvent` (`letterId` INTEGER, `letterText` TEXT NOT NULL, `masteryScore` REAL NOT NULL, `timeSpentMs` INTEGER NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 6)");

            String sql = "CREATE TABLE IF NOT EXISTS `LetterSoundLearningEvent` (`letterSoundId` INTEGER, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 7)");

            String sql = "CREATE TABLE IF NOT EXISTS `VideoLearningEvent` (`videoId` INTEGER, `videoTitle` TEXT NOT NULL, `learningEventType` TEXT NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 8)");

            String sql = "DROP TABLE `LetterLearningEvent`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 9)");

            String sql = "DROP TABLE `LetterAssessmentEvent`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 10)");

            String sql = "CREATE TABLE IF NOT EXISTS `LetterSoundAssessmentEvent` (`letterSoundLetters` TEXT NOT NULL, `letterSoundSounds` TEXT NOT NULL, `letterSoundId` INTEGER, `masteryScore` REAL NOT NULL, `timeSpentMs` INTEGER NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 11)");

            String sql = "ALTER TABLE `StoryBookLearningEvent` ADD COLUMN `storyBookTitle` TEXT NOT NULL DEFAULT ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 12)");

            String sql = "ALTER TABLE `LetterSoundAssessmentEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `LetterSoundLearningEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordAssessmentEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordLearningEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `StoryBookLearningEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `VideoLearningEvent` RENAME COLUMN `time` TO `timestamp`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };
}
