package ai.elimu.analytics.task;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import ai.elimu.analytics.BaseApplication;
import ai.elimu.analytics.BuildConfig;
import ai.elimu.analytics.rest.LetterAssessmentEventService;
import ai.elimu.analytics.rest.LetterLearningEventService;
import ai.elimu.analytics.rest.StoryBookLearningEventService;
import ai.elimu.analytics.rest.WordAssessmentEventService;
import ai.elimu.analytics.rest.WordLearningEventService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

        if (!"debug".equals(BuildConfig.BUILD_TYPE)) {
            uploadLetterLearningEvents();
            uploadLetterAssessmentEvents();
            uploadWordLearningEvents();
            uploadWordAssessmentEvents();
            uploadStoryBookLearningEvents();
        }

        return Result.success();
    }

    private void uploadLetterLearningEvents() {
        Log.i(getClass().getName(), "uploadLetterLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File letterLearningEventsDir = new File(filesDir, "letter-learning-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + letterLearningEventsDir);
        File[] files = letterLearningEventsDir.listFiles();
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                LetterLearningEventService letterLearningEventService = retrofit.create(LetterLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = letterLearningEventService.uploadCsvFile(part);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);
                    Log.i(getClass().getName(), "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Log.i(getClass().getName(), "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Log.e(getClass().getName(), "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
        }
    }

    private void uploadLetterAssessmentEvents() {
        Log.i(getClass().getName(), "uploadLetterAssessmentEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File letterAssessmentEventsDir = new File(filesDir, "letter-assessment-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + letterAssessmentEventsDir);
        File[] files = letterAssessmentEventsDir.listFiles();
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                LetterAssessmentEventService letterAssessmentEventService = retrofit.create(LetterAssessmentEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = letterAssessmentEventService.uploadCsvFile(part);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);
                    Log.i(getClass().getName(), "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Log.i(getClass().getName(), "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Log.e(getClass().getName(), "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
        }
    }

    private void uploadWordLearningEvents() {
        Log.i(getClass().getName(), "uploadWordLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File wordLearningEventsDir = new File(filesDir, "word-learning-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + wordLearningEventsDir);
        File[] files = wordLearningEventsDir.listFiles();
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                WordLearningEventService wordLearningEventService = retrofit.create(WordLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = wordLearningEventService.uploadCsvFile(part);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);
                    Log.i(getClass().getName(), "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Log.i(getClass().getName(), "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Log.e(getClass().getName(), "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
        }
    }

    private void uploadWordAssessmentEvents() {
        Log.i(getClass().getName(), "uploadWordAssessmentEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File wordAssessmentEventsDir = new File(filesDir, "word-assessment-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + wordAssessmentEventsDir);
        File[] files = wordAssessmentEventsDir.listFiles();
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                WordAssessmentEventService wordAssessmentEventService = retrofit.create(WordAssessmentEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = wordAssessmentEventService.uploadCsvFile(part);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);
                    Log.i(getClass().getName(), "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Log.i(getClass().getName(), "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Log.e(getClass().getName(), "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
        }
    }
    
    private void uploadStoryBookLearningEvents() {
        Log.i(getClass().getName(), "uploadStoryBookLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File storyBookLearningEventsDir = new File(filesDir, "storybook-learning-events");
        Log.i(getClass().getName(), "Uploading CSV files from " + storyBookLearningEventsDir);
        File[] files = storyBookLearningEventsDir.listFiles();
        if (files != null) {
            Log.i(getClass().getName(), "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(getClass().getName(), "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                StoryBookLearningEventService storyBookLearningEventService = retrofit.create(StoryBookLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = storyBookLearningEventService.uploadCsvFile(part);
                Log.i(getClass().getName(), "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Log.i(getClass().getName(), "response: " + response);
                    Log.i(getClass().getName(), "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Log.i(getClass().getName(), "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Log.e(getClass().getName(), "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
        }
    }
}
