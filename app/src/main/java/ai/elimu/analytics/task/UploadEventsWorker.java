package ai.elimu.analytics.task;

import android.content.Context;

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
        Timber.i( "doWork");

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
        Timber.i( "uploadLetterLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File letterLearningEventsDir = new File(filesDir, "letter-learning-events");
        Timber.i( "Uploading CSV files from " + letterLearningEventsDir);
        File[] files = letterLearningEventsDir.listFiles();
        if (files != null) {
            Timber.i( "files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Timber.i( "file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                LetterLearningEventService letterLearningEventService = retrofit.create(LetterLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = letterLearningEventService.uploadCsvFile(part);
                Timber.i( "call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Timber.i( "response: " + response);
                    Timber.i( "response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Timber.i( "bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Timber.e( "errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
    }

    private void uploadLetterAssessmentEvents() {
        Timber.i("uploadLetterAssessmentEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File letterAssessmentEventsDir = new File(filesDir, "letter-assessment-events");
        Timber.i("Uploading CSV files from " + letterAssessmentEventsDir);
        File[] files = letterAssessmentEventsDir.listFiles();
        if (files != null) {
            Timber.i("files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Timber.i("file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                LetterAssessmentEventService letterAssessmentEventService = retrofit.create(LetterAssessmentEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = letterAssessmentEventService.uploadCsvFile(part);
                Timber.i("call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Timber.i("response: " + response);
                    Timber.i("response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Timber.i("bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Timber.e("errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
    }

    private void uploadWordLearningEvents() {
        Timber.i("uploadWordLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File wordLearningEventsDir = new File(filesDir, "word-learning-events");
        Timber.i("Uploading CSV files from " + wordLearningEventsDir);
        File[] files = wordLearningEventsDir.listFiles();
        if (files != null) {
            Timber.i("files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Timber.i("file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                WordLearningEventService wordLearningEventService = retrofit.create(WordLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = wordLearningEventService.uploadCsvFile(part);
                Timber.i("call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Timber.i("response: " + response);
                    Timber.i("response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Timber.i("bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Timber.e("errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
    }

    private void uploadWordAssessmentEvents() {
        Timber.i("uploadWordAssessmentEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File wordAssessmentEventsDir = new File(filesDir, "word-assessment-events");
        Timber.i("Uploading CSV files from " + wordAssessmentEventsDir);
        File[] files = wordAssessmentEventsDir.listFiles();
        if (files != null) {
            Timber.i("files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Timber.i("file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                WordAssessmentEventService wordAssessmentEventService = retrofit.create(WordAssessmentEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = wordAssessmentEventService.uploadCsvFile(part);
                Timber.i("call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Timber.i("response: " + response);
                    Timber.i("response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Timber.i("bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Timber.e("errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
    }
    
    private void uploadStoryBookLearningEvents() {
        Timber.i("uploadStoryBookLearningEvents");

        // Upload CSV files to the server
        File filesDir = getApplicationContext().getFilesDir();
        File storyBookLearningEventsDir = new File(filesDir, "storybook-learning-events");
        Timber.i("Uploading CSV files from " + storyBookLearningEventsDir);
        File[] files = storyBookLearningEventsDir.listFiles();
        if (files != null) {
            Timber.i("files.length: " + files.length);
            Arrays.sort(files);
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Timber.i("file.getName(): " + file.getName());

                BaseApplication baseApplication = (BaseApplication) getApplicationContext();
                Retrofit retrofit = baseApplication.getRetrofit();
                StoryBookLearningEventService storyBookLearningEventService = retrofit.create(StoryBookLearningEventService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = storyBookLearningEventService.uploadCsvFile(part);
                Timber.i("call.request(): " + call.request());
                try {
                    Response<ResponseBody> response = call.execute();
                    Timber.i("response: " + response);
                    Timber.i("response.isSuccessful(): " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String bodyString = response.body().string();
                        Timber.i("bodyString: " + bodyString);
                    } else {
                        String errorBodyString = response.errorBody().string();
                        Timber.e("errorBodyString: " + errorBodyString);
                        // TODO: Handle error
                    }
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
    }
}
