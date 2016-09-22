package org.literacyapp.analytics.util;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

public class DisplayHelper {

    public static void captureScreenshot(Context context) {
        Log.i(DisplayHelper.class.getName(), "captureScreenshot");
        String screenshotsPath = context.getFilesDir() + File.separator + "screenshots";
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
        Log.i(DisplayHelper.class.getName(), "screenshotFile.exists(): " + screenshotFile.exists());

        // Reduce image size
        // TODO
    }
}
