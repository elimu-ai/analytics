package ai.elimu.analytics.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
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
import ai.elimu.analytics.dao.NumberAssessmentEventDao;
import ai.elimu.analytics.dao.NumberLearningEventDao;
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.VideoLearningEventDao;
import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.converter.Converters;
import ai.elimu.analytics.db.migration.AutoMigrationSpecFrom22To23;
import ai.elimu.analytics.db.converter.StringListConverter;
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent;
import ai.elimu.analytics.entity.LetterSoundLearningEvent;
import ai.elimu.analytics.entity.NumberAssessmentEvent;
import ai.elimu.analytics.entity.NumberLearningEvent;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.VideoLearningEvent;
import ai.elimu.analytics.entity.WordAssessmentEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import timber.log.Timber;

@Database(version = 25, entities = {
        LetterSoundAssessmentEvent.class,
        LetterSoundLearningEvent.class,

        WordAssessmentEvent.class,
        WordLearningEvent.class,

        NumberAssessmentEvent.class,
        NumberLearningEvent.class,

        StoryBookLearningEvent.class,

        VideoLearningEvent.class
}, autoMigrations = {@AutoMigration(from = 22, to = 23, spec = AutoMigrationSpecFrom22To23.class)})
@TypeConverters({Converters.class, StringListConverter.class})
public abstract class RoomDb extends RoomDatabase {
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
                                    MIGRATION_2_3,
                                    MIGRATION_3_4,
                                    MIGRATION_4_5,
                                    MIGRATION_5_6,
                                    MIGRATION_6_7,
                                    MIGRATION_7_8,
                                    MIGRATION_8_9,
                                    MIGRATION_9_10,
                                    MIGRATION_10_11,
                                    MIGRATION_11_12,
                                    MIGRATION_12_13,
                                    MIGRATION_13_14,
                                    MIGRATION_14_15,
                                    MIGRATION_15_16,
                                    MIGRATION_16_17,
                                    MIGRATION_17_18,
                                    MIGRATION_18_19,
                                    MIGRATION_19_20,
                                    MIGRATION_20_21,
                                    MIGRATION_21_22,
                                    MIGRATION_23_24,
                                    MIGRATION_24_25
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

            String sql = "ALTER TABLE `LetterSoundLearningEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordLearningEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `StoryBookLearningEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `VideoLearningEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 13)");

            database.execSQL("CREATE TABLE IF NOT EXISTS `WordLearningEvent_tmp` (`wordId` INTEGER, `wordText` TEXT NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `additionalData` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO WordLearningEvent_tmp(wordId, wordText, learningEventType, androidId, packageName, time, additionalData, id) SELECT wordId, wordText, learningEventType, androidId, packageName, time, additionalData, id FROM WordLearningEvent");
            database.execSQL("DROP TABLE WordLearningEvent");
            database.execSQL("ALTER TABLE WordLearningEvent_tmp RENAME TO WordLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `StoryBookLearningEvent_tmp` (`storyBookTitle` TEXT NOT NULL, `storyBookId` INTEGER NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `additionalData` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO StoryBookLearningEvent_tmp(storyBookTitle, storyBookId, learningEventType, androidId, packageName, time, additionalData, id) SELECT storyBookTitle, storyBookId, learningEventType, androidId, packageName, time, additionalData, id FROM StoryBookLearningEvent");
            database.execSQL("DROP TABLE StoryBookLearningEvent");
            database.execSQL("ALTER TABLE StoryBookLearningEvent_tmp RENAME TO StoryBookLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `VideoLearningEvent_tmp` (`videoId` INTEGER, `videoTitle` TEXT NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `additionalData` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO VideoLearningEvent_tmp(videoId, videoTitle, learningEventType, androidId, packageName, time, additionalData, id) SELECT videoId, videoTitle, learningEventType, androidId, packageName, time, additionalData, id FROM VideoLearningEvent");
            database.execSQL("DROP TABLE VideoLearningEvent");
            database.execSQL("ALTER TABLE VideoLearningEvent_tmp RENAME TO VideoLearningEvent");
        }
    };

    private static final Migration MIGRATION_13_14 = new Migration(13, 14) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 14)");

            String sql = "CREATE TABLE IF NOT EXISTS `NumberLearningEvent` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `numberId` INTEGER, `numberValue` INTEGER NOT NULL, `learningEventType` TEXT, `numberSymbol` TEXT, `additionalData` TEXT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_14_15 = new Migration(14, 15) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 15)");


            String sql = "ALTER TABLE `LetterSoundLearningEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordLearningEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `StoryBookLearningEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `VideoLearningEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `NumberLearningEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            sql = "ALTER TABLE `LetterSoundLearningEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordLearningEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `StoryBookLearningEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `VideoLearningEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `NumberLearningEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_15_16 = new Migration(15, 16) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 16)");


            String sql = "ALTER TABLE `LetterSoundAssessmentEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordAssessmentEvent` ADD COLUMN `researchExperiment` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            sql = "ALTER TABLE `LetterSoundAssessmentEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordAssessmentEvent` ADD COLUMN `experimentGroup` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_16_17 = new Migration(16, 17) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 17)");

            String sql = "ALTER TABLE `LetterSoundAssessmentEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `WordAssessmentEvent` ADD COLUMN `additionalData` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_17_18 = new Migration(17, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 18)");


            // packageName:

            String sql = "DELETE FROM `LetterSoundAssessmentEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `LetterSoundLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `LetterSoundLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `NumberLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `StoryBookLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `VideoLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `WordAssessmentEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `WordLearningEvent` WHERE `packageName` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            // androidId:

            sql = "DELETE FROM `LetterSoundAssessmentEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `LetterSoundLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `LetterSoundLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `NumberLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `StoryBookLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `VideoLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `WordAssessmentEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "DELETE FROM `WordLearningEvent` WHERE `androidId` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            // storyBookTitle

            sql = "DELETE FROM `StoryBookLearningEvent` WHERE `storyBookTitle` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            // videoTitle

            sql = "DELETE FROM `VideoLearningEvent` WHERE `videoTitle` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);


            // wordText

            sql = "DELETE FROM `WordAssessmentEvent` WHERE `wordText` = ''";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_18_19 = new Migration(18, 19) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 19)");

            String sql = "CREATE TABLE IF NOT EXISTS `NumberAssessmentEvent` (`masteryScore` REAL NOT NULL, `timeSpentMs` INTEGER NOT NULL, `numberValue` INTEGER NOT NULL, `numberId` INTEGER, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    private static final Migration MIGRATION_19_20 = new Migration(19, 20) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 20)");

            String sql = "UPDATE `NumberAssessmentEvent` SET `numberId` = NULL WHERE `numberId` = 0";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    /**
     * Rename [LearningEvent] columns from "time" to "timestamp"
     */
    private static final Migration MIGRATION_20_21 = new Migration(20, 21) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 21)");

            database.execSQL("CREATE TABLE IF NOT EXISTS `LetterSoundLearningEvent_tmp` (`letterSoundId` INTEGER, `letterSoundLetterTexts` TEXT NOT NULL, `letterSoundSoundValuesIpa` TEXT NOT NULL, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO LetterSoundLearningEvent_tmp(letterSoundId, letterSoundLetterTexts, letterSoundSoundValuesIpa, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT letterSoundId, letterSoundLetterTexts, letterSoundSoundValuesIpa, androidId, packageName, time, additionalData, researchExperiment, experimentGroup, id FROM LetterSoundLearningEvent");
            database.execSQL("DROP TABLE LetterSoundLearningEvent");
            database.execSQL("ALTER TABLE LetterSoundLearningEvent_tmp RENAME TO LetterSoundLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `WordLearningEvent_tmp` (`wordId` INTEGER, `wordText` TEXT NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO WordLearningEvent_tmp(wordId, wordText, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT wordId, wordText, learningEventType, androidId, packageName, time, additionalData, researchExperiment, experimentGroup, id FROM WordLearningEvent");
            database.execSQL("DROP TABLE WordLearningEvent");
            database.execSQL("ALTER TABLE WordLearningEvent_tmp RENAME TO WordLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `NumberLearningEvent_tmp` (`numberValue` INTEGER NOT NULL, `numberId` INTEGER, `numberSymbol` TEXT, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO NumberLearningEvent_tmp(numberValue, numberId, numberSymbol, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT numberValue, numberId, numberSymbol, learningEventType, androidId, packageName, time, additionalData, researchExperiment, experimentGroup, id FROM NumberLearningEvent");
            database.execSQL("DROP TABLE NumberLearningEvent");
            database.execSQL("ALTER TABLE NumberLearningEvent_tmp RENAME TO NumberLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `StoryBookLearningEvent_tmp` (`storyBookTitle` TEXT NOT NULL, `storyBookId` INTEGER NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO StoryBookLearningEvent_tmp(storyBookTitle, storyBookId, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT storyBookTitle, storyBookId, learningEventType, androidId, packageName, time, additionalData, researchExperiment, experimentGroup, id FROM StoryBookLearningEvent");
            database.execSQL("DROP TABLE StoryBookLearningEvent");
            database.execSQL("ALTER TABLE StoryBookLearningEvent_tmp RENAME TO StoryBookLearningEvent");

            database.execSQL("CREATE TABLE IF NOT EXISTS `VideoLearningEvent_tmp` (`videoId` INTEGER, `videoTitle` TEXT NOT NULL, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO VideoLearningEvent_tmp(videoId, videoTitle, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT videoId, videoTitle, learningEventType, androidId, packageName, time, additionalData, researchExperiment, experimentGroup, id FROM VideoLearningEvent");
            database.execSQL("DROP TABLE VideoLearningEvent");
            database.execSQL("ALTER TABLE VideoLearningEvent_tmp RENAME TO VideoLearningEvent");
        }
    };

    /**
     * Change from `storyBookId INTEGER NOT NULL` to `storyBookId INTEGER`
     */
    private static final Migration MIGRATION_21_22 = new Migration(21, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 22)");

            database.execSQL("CREATE TABLE IF NOT EXISTS `StoryBookLearningEvent_tmp` (`storyBookTitle` TEXT NOT NULL, `storyBookId` INTEGER, `learningEventType` TEXT, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `additionalData` TEXT, `researchExperiment` TEXT, `experimentGroup` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)");
            database.execSQL("INSERT INTO StoryBookLearningEvent_tmp(storyBookTitle, storyBookId, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id) SELECT storyBookTitle, storyBookId, learningEventType, androidId, packageName, timestamp, additionalData, researchExperiment, experimentGroup, id FROM StoryBookLearningEvent");
            database.execSQL("DROP TABLE StoryBookLearningEvent");
            database.execSQL("ALTER TABLE StoryBookLearningEvent_tmp RENAME TO StoryBookLearningEvent");
        }
    };

    /**
     * Add two new columns to LetterSoundLearningEvent:
     *  - letterSoundLetters
     *  - letterSoundSounds
     */
    private static final Migration MIGRATION_23_24 = new Migration(23, 24) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 24)");

            String sql = "DELETE FROM `LetterSoundLearningEvent`";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `LetterSoundLearningEvent` ADD COLUMN `letterSoundLetters` TEXT NOT NULL";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `LetterSoundLearningEvent` ADD COLUMN `letterSoundSounds` TEXT NOT NULL";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };

    /**
     * Add a new columns to NumberAssessmentEvent:
     *  - numberSymbol
     */
    private static final Migration MIGRATION_24_25 = new Migration(24, 25) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Timber.i("migrate (" + database.getVersion() + " --> 25)");

            String sql = "ALTER TABLE `NumberAssessmentEvent` ADD COLUMN `numberSymbol` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);

            sql = "ALTER TABLE `NumberAssessmentEvent` ADD COLUMN `numberSymbol` TEXT";
            Timber.i("sql: %s", sql);
            database.execSQL(sql);
        }
    };
}
