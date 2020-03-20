package ai.elimu.analytics.task;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadEventsWorker extends Worker {

    public UploadEventsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(getClass().getName(), "doWork");

        // Upload CSV file to the server
        // TODO

        return Result.success();
    }
}
