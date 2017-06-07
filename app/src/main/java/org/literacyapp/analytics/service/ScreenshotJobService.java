package org.literacyapp.analytics.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;

import org.literacyapp.analytics.util.CameraHelper;
import org.literacyapp.analytics.util.DeviceInfoHelper;
import org.literacyapp.analytics.util.DisplayHelper;

import java.io.File;

/**
 * Service responsible for recording screenshots when the screen in switched on.
 *
 * This service is triggered in the @{link {@link org.literacyapp.analytics.receiver.BootReceiver}}
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

                // Capture screenshot
                File screenshotFile = DisplayHelper.captureScreenshot();
                Log.i(getClass().getName(), "screenshotFile.exists(): " + screenshotFile.exists());
                if (screenshotFile.exists()) {
                    // Reduce image size
                    File resizedScreenshotFile = DisplayHelper.resizeBitmap(screenshotFile, 640, false);
                    Log.i(getClass().getName(), "resizedScreenshotFile.exists(): " + resizedScreenshotFile.exists());
                }

                // Take picture with front camera
                String picturePath = screenshotFile.getAbsolutePath().replace("_screenshot", "_picture");
                Log.i(getClass().getName(), "picturePath: " + picturePath);
                if ("KFFOWI".equals(DeviceInfoHelper.getDeviceModel(getApplicationContext()))) {
                    File pictureFile = new CameraHelper().takePicture(getApplicationContext(), picturePath);
                    Log.i(getClass().getName(), "pictureFile.exists(): " + pictureFile.exists());
                } else {
                    // TODO
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
