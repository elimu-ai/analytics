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
import ai.elimu.analytics.dao.LetterLearningEventDao;
import ai.elimu.analytics.model.LetterLearningEvent;
import ai.elimu.analytics.util.DeviceInfoHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class LetterLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        String letter = null;
        String letterExtra = intent.getStringExtra("letter");
        Log.i(getClass().getName(), "letterExtra: " + letterExtra);
        if (!TextUtils.isEmpty(letterExtra)) {
            letter = letterExtra;
            Log.i(getClass().getName(), "letter: " + letter);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String studentId = sharedPreferences.getString(StudentUpdatedReceiver.PREF_STUDENT_ID, null);
        Log.i(getClass().getName(), "studentId: " + studentId);

        // Store in database
        LetterLearningEvent letterLearningEvent = new LetterLearningEvent();
        letterLearningEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        letterLearningEvent.setTime(Calendar.getInstance());
        letterLearningEvent.setPackageName(packageName);
        letterLearningEvent.setStudentId(studentId);
        letterLearningEvent.setLetter(letter);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        LetterLearningEventDao letterLearningEventDao = analyticsApplication.getDaoSession().getLetterLearningEventDao();
        long id = letterLearningEventDao.insert(letterLearningEvent);
        Log.i(getClass().getName(), "LetterLearningEvent saved in database with id " + id);

        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:ai.elimu.handwriting|studentId:4113947bec18b7ad_1|letter:a
        String logLine = "id:" + id
                + "|deviceId:" + letterLearningEvent.getDeviceId()
                + "|time:" + letterLearningEvent.getTime().getTimeInMillis()
                + "|packageName:" + letterLearningEvent.getPackageName()
                + "|studentId:" + letterLearningEvent.getStudentId()
                + "|letter:" + letterLearningEvent.getLetter()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.elimu-ai/analytics/events/device_" + letterLearningEvent.getDeviceId();
        File logsDir = new File(logsPath);
        Log.i(getClass().getName(), "logsDir: " + logsDir);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        if (!TextUtils.isEmpty(letterLearningEvent.getStudentId())) {
            // TODO: create one subfolder per student id (if not null)?
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "letter_learning_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
