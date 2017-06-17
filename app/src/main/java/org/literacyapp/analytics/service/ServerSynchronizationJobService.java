package org.literacyapp.analytics.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.Log;

import org.literacyapp.analytics.util.EnvironmentSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Service responsible for uploading event files to server.
 *
 * This service is triggered in the @{link {@link org.literacyapp.analytics.receiver.BootReceiver}}
 */
public class ServerSynchronizationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(getClass().getName(), "onStartJob");

        String logsPath = Environment.getExternalStorageDirectory() + "/.literacyapp-analytics/events";
        File eventsDir = new File(logsPath);
        Log.i(getClass().getName(), "eventsDir: " + eventsDir);
        Log.i(getClass().getName(), "eventsDir.exists(): " + eventsDir.exists());
        if (eventsDir.exists()) {
            File[] deviceDirs = eventsDir.listFiles();
            for (int i = 0; i < deviceDirs.length; i++) {
                File deviceDir = deviceDirs[i];
                Log.i(getClass().getName(), "deviceDir: " + deviceDir);

                if (!deviceDir.getName().startsWith("device_")) {
                    continue;
                }

                File[] eventFiles = deviceDir.listFiles();
                for (File eventFile : eventFiles) {
                    Log.i(getClass().getName(), "eventFile: " + eventFile);
                    // Expected filename: "application_opened_events_yyyy-MM-dd.log"

                    if (eventFile.getName().startsWith("application_opened_events_")) {
                        // Skip files generated more than 7 days ago
                        String dateAsString = eventFile.getName().replace("application_opened_events_", "").replace(".log", "");
                        Log.i(getClass().getName(), "dateAsString: " + dateAsString);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = simpleDateFormat.parse(dateAsString);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            Log.i(getClass().getName(), "calendar.getTime(): " + calendar.getTime());

                            Calendar calendar7DaysAgo = Calendar.getInstance();
                            calendar7DaysAgo.setTime(calendar.getTime());
                            calendar7DaysAgo.add(Calendar.DAY_OF_MONTH, -7);
                            Log.i(getClass().getName(), "calendar7DaysAgo.getTime(): " + calendar7DaysAgo.getTime());

                            if (!calendar.before(calendar7DaysAgo)) {
                                Log.i(getClass().getName(), "Uploading to web server: " + eventFile);

                                new UploadApplicationOpenedEventsAsyncTask().execute(eventFile);
                            }
                        } catch (ParseException e) {
                            Log.e(getClass().getName(), null, e);
                        }
                    }
                }
            }
        }

        boolean asynchronousProcessing = false;
        return asynchronousProcessing;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(getClass().getName(), "onStopJob");

        boolean restartJob = false;
        return restartJob;
    }
}
