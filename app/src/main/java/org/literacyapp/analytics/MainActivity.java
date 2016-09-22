package org.literacyapp.analytics;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.literacyapp.analytics.service.ScreenshotJobService;
import org.literacyapp.analytics.util.RootHelper;

public class MainActivity extends Activity {

    private Button mMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mMainButton = (Button) findViewById(R.id.main_button);
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();

        mMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(getClass().getName(), "mMainButton onClick");

                // Request root access
                boolean isRootPermissionGranted = RootHelper.runAsRoot(new String[] {
                        "echo \"Do I have root?\" >/system/sd/temporary.txt\n",
                        "exit\n"
                });
                Log.i(getClass().getName(), "isRootPermissionGranted: " + isRootPermissionGranted);
                if (!isRootPermissionGranted) {
                    Toast.makeText(getApplicationContext(), "Root permission was not granted. Please restart application.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Root permission was granted. Starting background job...", Toast.LENGTH_LONG).show();

                    // Initiate background job for recording screenshots
                    ComponentName componentName = new ComponentName(getApplicationContext(), ScreenshotJobService.class);
                    JobInfo.Builder builder = new JobInfo.Builder(0, componentName);
                    builder.setPeriodic(5 * 60 * 1000); // Every 5 minutes
                    JobInfo screenshotJobInfo = builder.build();

                    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(screenshotJobInfo);
                }

                finish();
            }
        });
    }
}
