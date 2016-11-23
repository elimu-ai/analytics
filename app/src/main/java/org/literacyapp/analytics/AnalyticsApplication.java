package org.literacyapp.analytics;

import android.app.Application;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.literacyapp.analytics.dao.DaoMaster100003;
import org.literacyapp.analytics.dao.DaoSession;

public class AnalyticsApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate();

        DaoMaster100003.DevOpenHelper helper = new DaoMaster100003.DevOpenHelper(this, "analytics-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster100003(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
