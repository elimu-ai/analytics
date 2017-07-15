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
import ai.elimu.analytics.dao.VideoLearningEventDao;
import ai.elimu.analytics.model.VideoLearningEvent;
import ai.elimu.analytics.util.DeviceInfoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class VideoLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        Long videoId = intent.getLongExtra("videoId", -1);
        Log.i(getClass().getName(), "videoId: " + videoId);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String studentId = sharedPreferences.getString(StudentUpdatedReceiver.PREF_STUDENT_ID, null);
        Log.i(getClass().getName(), "studentId: " + studentId);

        // Store in database
        VideoLearningEvent videoLearningEvent = new VideoLearningEvent();
        videoLearningEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        videoLearningEvent.setTime(Calendar.getInstance());
        videoLearningEvent.setPackageName(packageName);
        videoLearningEvent.setStudentId(studentId);
        videoLearningEvent.setVideoId(videoId);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        VideoLearningEventDao videoLearningEventDao = analyticsApplication.getDaoSession().getVideoLearningEventDao();
        long id = videoLearningEventDao.insert(videoLearningEvent);
        Log.i(getClass().getName(), "VideoLearningEvent saved in database with id " + id);

        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:ai.elimu.handwriting|studentId:4113947bec18b7ad_1|videoId:25
        String logLine = "id:" + id
                + "|deviceId:" + videoLearningEvent.getDeviceId()
                + "|time:" + videoLearningEvent.getTime().getTimeInMillis()
                + "|packageName:" + videoLearningEvent.getPackageName()
                + "|studentId:" + videoLearningEvent.getStudentId()
                + "|videoId:" + videoLearningEvent.getVideoId()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.elimu-ai/analytics/events/device_" + videoLearningEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        if (!TextUtils.isEmpty(videoLearningEvent.getStudentId())) {
            // TODO: create one subfolder per student id (if not null)?
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "video_learning_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
