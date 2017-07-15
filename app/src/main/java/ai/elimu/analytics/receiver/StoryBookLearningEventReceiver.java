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
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.model.StoryBookLearningEvent;
import ai.elimu.analytics.util.DeviceInfoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class StoryBookLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        Long storyBookId = intent.getLongExtra("storyBookId", -1);
        Log.i(getClass().getName(), "storyBookId: " + storyBookId);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String studentId = sharedPreferences.getString(StudentUpdatedReceiver.PREF_STUDENT_ID, null);
        Log.i(getClass().getName(), "studentId: " + studentId);

        // Store in database
        StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
        storyBookLearningEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        storyBookLearningEvent.setTime(Calendar.getInstance());
        storyBookLearningEvent.setPackageName(packageName);
        storyBookLearningEvent.setStudentId(studentId);
        storyBookLearningEvent.setStoryBookId(storyBookId);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        StoryBookLearningEventDao storyBookLearningEventDao = analyticsApplication.getDaoSession().getStoryBookLearningEventDao();
        long id = storyBookLearningEventDao.insert(storyBookLearningEvent);
        Log.i(getClass().getName(), "StoryBookLearningEvent saved in database with id " + id);

        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:ai.elimu.handwriting|studentId:4113947bec18b7ad_1|storyBookId:25
        String logLine = "id:" + id
                + "|deviceId:" + storyBookLearningEvent.getDeviceId()
                + "|time:" + storyBookLearningEvent.getTime().getTimeInMillis()
                + "|packageName:" + storyBookLearningEvent.getPackageName()
                + "|studentId:" + storyBookLearningEvent.getStudentId()
                + "|storyBookId:" + storyBookLearningEvent.getStoryBookId()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.elimu-ai/analytics/events/device_" + storyBookLearningEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        if (!TextUtils.isEmpty(storyBookLearningEvent.getStudentId())) {
            // TODO: create one subfolder per student id (if not null)?
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "storybook_learning_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
