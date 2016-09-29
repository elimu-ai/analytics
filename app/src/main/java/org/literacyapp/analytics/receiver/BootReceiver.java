package org.literacyapp.analytics.receiver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.literacyapp.analytics.service.ScreenshotJobService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        // Initiate background job for recording screenshots
        ComponentName componentName = new ComponentName(context, ScreenshotJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, componentName);
        builder.setPeriodic(5 * 60 * 1000); // Every 5 minutes
        JobInfo screenshotJobInfo = builder.build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(screenshotJobInfo);
    }
}
