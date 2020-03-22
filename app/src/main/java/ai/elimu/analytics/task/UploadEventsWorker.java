package ai.elimu.analytics.task;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import ai.elimu.analytics.BaseApplication;
import ai.elimu.analytics.rest.StoryBookLearningEventService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadEventsWorker extends Worker {

    public UploadEventsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(getClass().getName(), "doWork");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File storyBookLearningEventsDir = new File(filesDir, "storybook-learning-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + storyBookLearningEventsDir);
        File[] files = storyBookLearningEventsDir.listFiles();
        Arrays.sort(files);
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                StoryBookLearningEventService storyBookLearningEventService = retrofit.create(StoryBookLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/csv"), file);
                Call<ResponseBody> call = storyBookLearningEventService.uploadCsvFile(requestBody);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);

                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }

            }
        }

        return Result.success();
    }
}
