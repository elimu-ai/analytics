package org.literacyapp.analytics.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;

import org.literacyapp.analytics.util.DisplayHelper;

import java.io.File;

/**
 * Service responsible for recording screenshots when the screen in switched on.
 */
public class ScreenshotJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(getClass().getName(), "onStartJob");

        // Detect if screen is active
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : displayManager.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                Log.i(getClass().getName(), "display.getState(): " + display.getState());
                File screenshotFile = DisplayHelper.captureScreenshot();
                Log.i(getClass().getName(), "screenshotFile.exists(): " + screenshotFile.exists());
                if (screenshotFile.exists()) {
                    // Reduce image size
                    File resizedScreenshotFile = DisplayHelper.resizeBitmap(screenshotFile, 320, false);
                    Log.i(getClass().getName(), "resizedScreenshotFile.exists(): " + resizedScreenshotFile.exists());
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
