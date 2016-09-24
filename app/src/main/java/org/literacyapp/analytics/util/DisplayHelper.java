package org.literacyapp.analytics.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class DisplayHelper {

    /**
     * Captures screenshot and stores it on the SD card.
     */
    public static File captureScreenshot() {
        Log.i(DisplayHelper.class.getName(), "captureScreenshot");

//        String screenshotsPath = Environment.getExternalStorageDirectory() + File.separator + ".literacyapp-analytics" + File.separator + "screenshots";
        String screenshotsPath = Environment.getExternalStorageDirectory() + File.separator + "literacyapp-analytics" + File.separator + "screenshots";
        File screenshotsDir = new File(screenshotsPath);
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd_HH:mm:ss", Calendar.getInstance());
        String fileName = "Screenshot_" + dateFormatted + ".png";
        File screenshotFile = new File(screenshotsDir, fileName);
        Log.i(DisplayHelper.class.getName(), "screenshotFile: " + screenshotFile);

        boolean isScreencapSuccess = RootHelper.runAsRoot(new String[] {
                "screencap " + screenshotFile.getAbsolutePath() + "\n"
        });
        Log.i(DisplayHelper.class.getName(), "isScreencapSuccess: " + isScreencapSuccess);
        return screenshotFile;
    }

    public static File resizeBitmap(File screenshotFile, int maxImageSize, boolean filter) {
        Log.i(DisplayHelper.class.getName(), "resizeBitmap");

        Bitmap bitmapOriginal = BitmapFactory.decodeFile(screenshotFile.getAbsolutePath());
        Log.i(DisplayHelper.class.getName(), "bitmapOriginal: " + bitmapOriginal.getWidth() + "px x " + bitmapOriginal.getHeight() + "px");

        float ratio = Math.min(
                (float) maxImageSize / bitmapOriginal.getWidth(),
                (float) maxImageSize / bitmapOriginal.getHeight()
        );
        int width = Math.round(ratio * bitmapOriginal.getWidth());
        int height = Math.round(ratio * bitmapOriginal.getHeight());

        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapOriginal, width, height, filter);
        Log.i(DisplayHelper.class.getName(), "bitmapScaled: " + bitmapScaled.getWidth() + "px x " + bitmapScaled.getHeight() + "px");

        // Save scaled bitmap as file
        String scaledScreenshotPath = screenshotFile.getAbsolutePath();
        scaledScreenshotPath = scaledScreenshotPath.replace(".png", "_" + maxImageSize + ".png");
        Log.i(DisplayHelper.class.getName(), "scaledScreenshotPath: " + scaledScreenshotPath);
        File scaledScreenshotFile = new File(scaledScreenshotPath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapScaled.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Log.i(DisplayHelper.class.getName(), "bytes.length: " + bytes.length);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(scaledScreenshotFile);
            IOUtils.write(bytes, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(DisplayHelper.class.getName(), null, e);
        } catch (IOException e) {
            Log.e(DisplayHelper.class.getName(), null, e);
        }
        return scaledScreenshotFile;
    }
}
