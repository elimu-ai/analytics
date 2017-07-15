package ai.elimu.analytics.receiver;

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
import ai.elimu.analytics.AnalyticsApplication;
import ai.elimu.analytics.dao.ShapeLearningEventDao;
import ai.elimu.analytics.model.ShapeLearningEvent;
import ai.elimu.analytics.util.DeviceInfoHelper;
import ai.elimu.model.enums.content.Shape;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ShapeLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        String shapeValue = intent.getStringExtra("shape");
        Log.i(getClass().getName(), "shapeValue: " + shapeValue);
        Shape shape = Shape.valueOf(shapeValue);
        Log.i(getClass().getName(), "shape: " + shape);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String studentId = sharedPreferences.getString(StudentUpdatedReceiver.PREF_STUDENT_ID, null);
        Log.i(getClass().getName(), "studentId: " + studentId);

        // Store in database
        ShapeLearningEvent shapeLearningEvent = new ShapeLearningEvent();
        shapeLearningEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        shapeLearningEvent.setTime(Calendar.getInstance());
        shapeLearningEvent.setPackageName(packageName);
        shapeLearningEvent.setStudentId(studentId);
        shapeLearningEvent.setShape(shape);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        ShapeLearningEventDao shapeLearningEventDao = analyticsApplication.getDaoSession().getShapeLearningEventDao();
        long id = shapeLearningEventDao.insert(shapeLearningEvent);
        Log.i(getClass().getName(), "ShapeLearningEvent saved in database with id " + id);

        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:ai.elimu.handwriting|studentId:4113947bec18b7ad_1|shape:CIRCLE
        String logLine = "id:" + id
                + "|deviceId:" + shapeLearningEvent.getDeviceId()
                + "|time:" + shapeLearningEvent.getTime().getTimeInMillis()
                + "|packageName:" + shapeLearningEvent.getPackageName()
                + "|studentId:" + shapeLearningEvent.getStudentId()
                + "|shape:" + shapeLearningEvent.getShape()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.elimu-ai/analytics/events/device_" + shapeLearningEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        if (!TextUtils.isEmpty(shapeLearningEvent.getStudentId())) {
            // TODO: create one subfolder per student id (if not null)?
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "shape_learning_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
