package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class StudentUpdatedReceiver extends BroadcastReceiver {

    public static final String PREF_STUDENT_ID = "pref_student_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        String studentId = intent.getStringExtra("studentId");
        Log.i(getClass().getName(), "studentId: " + studentId);

        if (!TextUtils.isEmpty(studentId)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String existingStudentId = sharedPreferences.getString(PREF_STUDENT_ID, null);
            Log.i(getClass().getName(), "existingStudentId: " + existingStudentId);

            sharedPreferences.edit().putString(PREF_STUDENT_ID, studentId).commit();
            Log.i(getClass().getName(), "Updated Student id to: " + studentId);

            // Store StudentUpdatedEvent
            // TODO
        }
    }
}
