package org.literacyapp.analytics.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.literacyapp.analytics.model.BootCompletedEvent;

public class CustomDaoMaster extends DaoMaster {

    public CustomDaoMaster(Database db) {
        super(db);
        Log.i(getClass().getName(), "CustomDaoMaster");
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i(getClass().getName(), "Upgrading schema from version " + oldVersion + " to " + newVersion);

            if (oldVersion < 1000008) {
                DbMigrationHelper.migrate(db,
                        LetterLearningEventDao.class,
                        NumberLearningEventDao.class,
                        VideoLearningEventDao.class,
                        BootCompletedEventDao.class
                );
            }
        }
    }
}
