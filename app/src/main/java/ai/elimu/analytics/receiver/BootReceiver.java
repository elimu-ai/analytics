package ai.elimu.analytics.receiver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import ai.elimu.analytics.AnalyticsApplication;
import ai.elimu.analytics.dao.BootCompletedEventDao;
import ai.elimu.analytics.model.BootCompletedEvent;
import ai.elimu.analytics.service.ScreenshotJobService;
import ai.elimu.analytics.service.ServerSynchronizationJobService;
import ai.elimu.analytics.util.DeviceInfoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");


        // Store event in database
        BootCompletedEvent bootCompletedEvent = new BootCompletedEvent();
        bootCompletedEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        bootCompletedEvent.setTime(Calendar.getInstance());
        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        BootCompletedEventDao bootCompletedEventDao = analyticsApplication.getDaoSession().getBootCompletedEventDao();
        long id = bootCompletedEventDao.insert(bootCompletedEvent);
        Log.i(getClass().getName(), "BootCompletedEvent saved in database with id " + id);

        // Store event in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273
        String logLine = "id:" + id
                + "|deviceId:" + bootCompletedEvent.getDeviceId()
                + "|time:" + bootCompletedEvent.getTime().getTimeInMillis()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);
        String logsPath = Environment.getExternalStorageDirectory() + "/.elimu-ai/analytics/events/device_" + bootCompletedEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "boot_completed_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }


        // Initiate background job for synchronizing events with server
        // Note: This code block also exists in the MainActivity
        ComponentName componentName = new ComponentName(context, ServerSynchronizationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1, componentName);
        builder.setPeriodic(60 * 60 * 1000); // Every 60 minutes
        JobInfo serverSynchronizationJobInfo = builder.build();
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultId = jobScheduler.schedule(serverSynchronizationJobInfo);
        if (resultId == JobScheduler.RESULT_SUCCESS) {
            Log.i(getClass().getName(), "Server synchronization Job scheduled with id: " + serverSynchronizationJobInfo.getId());
        } else {
            Log.w(getClass().getName(), "Server synchronization Job scheduling failed. JobInfo id: " + serverSynchronizationJobInfo.getId());
        }


        // Initiate background job for recording screenshots
        // Note: This code block also exists in the MainActivity
        componentName = new ComponentName(context, ScreenshotJobService.class);
        builder = new JobInfo.Builder(2, componentName);
        builder.setPeriodic(5 * 60 * 1000); // Every 5 minutes
        JobInfo screenshotJobInfo = builder.build();
        resultId = jobScheduler.schedule(screenshotJobInfo);
        if (resultId == JobScheduler.RESULT_SUCCESS) {
            Log.i(getClass().getName(), "Screenshot Job scheduled with id: " + screenshotJobInfo.getId());
        } else {
            Log.w(getClass().getName(), "Screenshot Job scheduling failed. JobInfo id: " + serverSynchronizationJobInfo.getId());
        }
    }
}