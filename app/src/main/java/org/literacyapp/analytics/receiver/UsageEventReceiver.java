package org.literacyapp.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.literacyapp.analytics.AnalyticsApplication;
import org.literacyapp.analytics.dao.UsageEventDao;
import org.literacyapp.analytics.model.UsageEvent;
import org.literacyapp.analytics.util.DeviceInfoHelper;
import org.literacyapp.analytics.util.VersionHelper;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class UsageEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        LiteracySkill literacySkill = null;
        String literacySkillExtra = intent.getStringExtra("literacySkill");
        Log.i(getClass().getName(), "literacySkillExtra: " + literacySkillExtra);
        if (!TextUtils.isEmpty(literacySkillExtra)) {
            literacySkill = LiteracySkill.valueOf(literacySkillExtra);
            Log.i(getClass().getName(), "literacySkill: " + literacySkill);
        }

        NumeracySkill numeracySkill = null;
        String numeracySkillExtra = intent.getStringExtra("numeracySkill");
        Log.i(getClass().getName(), "numeracySkillExtra: " + numeracySkillExtra);
        if (!TextUtils.isEmpty(numeracySkillExtra)) {
            numeracySkill = NumeracySkill.valueOf(numeracySkillExtra);
            Log.i(getClass().getName(), "numeracySkill: " + numeracySkill);
        }

        String letter = null;
        String letterExtra = intent.getStringExtra("letter");
        Log.i(getClass().getName(), "letterExtra: " + letterExtra);
        if (!TextUtils.isEmpty(letterExtra)) {
            letter = letterExtra;
            Log.i(getClass().getName(), "letter: " + letter);
        }

        Integer number = null;
        Integer numberIntExtra = intent.getIntExtra("number", Integer.MIN_VALUE);
        Log.i(getClass().getName(), "numberIntExtra: " + numberIntExtra);
        if (numberIntExtra > Integer.MIN_VALUE) {
            number = numberIntExtra;
            Log.i(getClass().getName(), "number: " + number);
        }

        String word = null;
        String wordExtra = intent.getStringExtra("word");
        Log.i(getClass().getName(), "wordExtra: " + wordExtra);
        if (!TextUtils.isEmpty(wordExtra)) {
            word = wordExtra;
            Log.i(getClass().getName(), "word: " + word);
        }

        // TODO: add task type, task result, duration, etc.


        // Store in database
        UsageEvent usageEvent = new UsageEvent();
        usageEvent.setTime(Calendar.getInstance());
        usageEvent.setDeviceId(DeviceInfoHelper.getDeviceId(context));
        // TODO: studentId
        usageEvent.setPackageName(packageName);
        usageEvent.setLiteracySkill(literacySkill);
        usageEvent.setNumeracySkill(numeracySkill);
        usageEvent.setLetter(letter);
        usageEvent.setNumber(number);
        usageEvent.setWord(word);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) context.getApplicationContext();
        UsageEventDao usageEventDao = analyticsApplication.getDaoSession().getUsageEventDao();
        long id = usageEventDao.insert(usageEvent);
        Log.i(getClass().getName(), "UsageEvent saved in database with id " + id);


        // Store in log file
        // Expected format: id:1|deviceId:4113947bec18b7ad|time:1481916197273|packageName:org.literacyapp|literacySkill:LETTER_IDENTIFICATION|numeracySkill:null|letter:a|number:null|word:null
        String logLine = "id:" + id
                + "|deviceId:" + usageEvent.getDeviceId()
                + "|studentId:" + usageEvent.getStudentId()
                + "|time:" + usageEvent.getTime().getTimeInMillis()
                + "|packageName:" + usageEvent.getPackageName()
                + "|literacySkill:" + usageEvent.getLiteracySkill()
                + "|numeracySkill:" + usageEvent.getNumeracySkill()
                + "|letter:" + usageEvent.getLetter()
                + "|number:" + usageEvent.getNumber()
                + "|word:" + usageEvent.getWord()
                + "\n";
        Log.i(getClass().getName(), "logLine: " + logLine);

        String logsPath = Environment.getExternalStorageDirectory() + "/.literacyapp-analytics/logs/" + VersionHelper.getAppVersionCode(context);
        File logsDir = new File(logsPath);
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        String dateFormatted = (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance());
        String fileName = "usage_events_" + dateFormatted + ".log";
        File logFile = new File(logsDir, fileName);
        Log.i(getClass().getName(), "logFile: " + logFile);
        try {
            FileUtils.writeStringToFile(logFile, logLine, "UTF-8", true);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
