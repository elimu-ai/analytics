package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ai.elimu.analytics.task.TaskInitializer;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        TaskInitializer.initializePeriodicWork(context);
    }
}
