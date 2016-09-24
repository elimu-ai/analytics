package org.literacyapp.analytics.util;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

public class DisplayHelper {

    /**
     * Captures screenshot and stores it on the SD card.
     */
    public static File captureScreenshot() {
        Log.i(DisplayHelper.class.getName(), "captureScreenshot");

        String screenshotsPath = Environment.getExternalStorageDirectory() + File.separator + ".literacyapp-analytics" + File.separator + "screenshots";
        File screenshotsDir = new File(screenshotsPath);
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdir();
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd_hh:mm:ss", Calendar.getInstance());
        String fileName = "Screenshot_" + dateFormatted + ".png";
        File screenshotFile = new File(screenshotsDir, fileName);
        Log.i(DisplayHelper.class.getName(), "screenshotFile: " + screenshotFile);

        boolean isScreencapSuccess = RootHelper.runAsRoot(new String[] {
                "screencap " + screenshotFile.getAbsolutePath() + "\n"
        });
        Log.i(DisplayHelper.class.getName(), "isScreencapSuccess: " + isScreencapSuccess);
        return screenshotFile;
    }
}
