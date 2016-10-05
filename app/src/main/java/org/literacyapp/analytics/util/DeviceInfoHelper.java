package org.literacyapp.analytics.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DeviceInfoHelper {

    public static String getDeviceModel(Context context) {
        String deviceModel = "";
        try {
            deviceModel = URLEncoder.encode(Build.MODEL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(DeviceInfoHelper.class.getName(), "Build.MODEL: " + Build.MODEL, e);
        }
        return deviceModel;
    }
}
