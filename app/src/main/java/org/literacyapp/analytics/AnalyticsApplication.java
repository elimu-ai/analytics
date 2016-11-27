package org.literacyapp.analytics;

import android.app.Application;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.literacyapp.analytics.dao.CustomDaoMaster;
import org.literacyapp.analytics.dao.DaoSession;

public class AnalyticsApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate();

        CustomDaoMaster.DevOpenHelper helper = new CustomDaoMaster.DevOpenHelper(this, "analytics-db");
        Database db = helper.getWritableDb();
        daoSession = new CustomDaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
