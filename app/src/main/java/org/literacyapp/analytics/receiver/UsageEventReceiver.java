package org.literacyapp.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.literacyapp.analytics.AnalyticsApplication;
import org.literacyapp.analytics.dao.UsageEventDao;
import org.literacyapp.analytics.model.UsageEvent;
import org.literacyapp.analytics.util.DeviceInfoHelper;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class UsageEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        String literacySkillExtra = intent.getStringExtra("literacySkill");
        Log.i(getClass().getName(), "literacySkillExtra: " + literacySkillExtra);
        if (!TextUtils.isEmpty(literacySkillExtra)) {
            LiteracySkill literacySkill = LiteracySkill.valueOf(literacySkillExtra);
            Log.i(getClass().getName(), "literacySkill: " + literacySkill);
        }

        String numeracySkillExtra = intent.getStringExtra("numeracySkill");
        Log.i(getClass().getName(), "numeracySkillExtra: " + numeracySkillExtra);
        if (!TextUtils.isEmpty(numeracySkillExtra)) {
            NumeracySkill numeracySkill = NumeracySkill.valueOf(numeracySkillExtra);
            Log.i(getClass().getName(), "numeracySkill: " + numeracySkill);
        }


        // TODO: add content, task type, task result, duration, etc.


        // Store in database
        UsageEvent usageEvent = new UsageEvent();
        usageEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        usageEvent.setPackageName(packageName);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        UsageEventDao usageEventDao = analyticsApplication.getDaoSession().getUsageEventDao();
        long id = usageEventDao.insert(usageEvent);
        Log.i(getClass().getName(), "UsageEvent saved in database with id " + id);


        // Store in log file
        // Expected format: id|deviceId|packageName\n
        String logLine = id + "|" + usageEvent.getDeviceId() + "|" + usageEvent.getPackageName() + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.literacyapp-analytics/logs";
        File logsDir = new File(logsPath);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "usage_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "screenshotFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
