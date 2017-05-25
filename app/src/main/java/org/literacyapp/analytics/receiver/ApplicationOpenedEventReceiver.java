package org.literacyapp.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.literacyapp.analytics.AnalyticsApplication;
import org.literacyapp.analytics.dao.ApplicationOpenedEventDao;
import org.literacyapp.analytics.model.ApplicationOpenedEvent;
import org.literacyapp.analytics.util.DeviceInfoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ApplicationOpenedEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String studentId = sharedPreferences.getString(StudentUpdatedReceiver.PREF_STUDENT_ID, null);
        Log.i(getClass().getName(), "studentId: " + studentId);


        // Store in database
        ApplicationOpenedEvent applicationOpenedEvent = new ApplicationOpenedEvent();
        applicationOpenedEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        applicationOpenedEvent.setTime(Calendar.getInstance());
        applicationOpenedEvent.setPackageName(packageName);
        applicationOpenedEvent.setStudentId(studentId);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        ApplicationOpenedEventDao applicationOpenedEventDao = analyticsApplication.getDaoSession().getApplicationOpenedEventDao();
        long id = applicationOpenedEventDao.insert(applicationOpenedEvent);
        Log.i(getClass().getName(), "ApplicationOpenedEvent saved in database with id " + id);


        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:org.literacyapp|studentId:null
        String logLine = "id:" + id
                + "|deviceId:" + applicationOpenedEvent.getDeviceId()
                + "|time:" + applicationOpenedEvent.getTime().getTimeInMillis()
                + "|packageName:" + applicationOpenedEvent.getPackageName()
                + "|studentId:" + applicationOpenedEvent.getStudentId()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.literacyapp-analytics/events/device_" + applicationOpenedEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        if (!TextUtils.isEmpty(applicationOpenedEvent.getStudentId())) {
            // TODO: create one subfolder per student id (if not null)?
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "application_opened_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
