package ai.elimu.analytics.rest;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface StoryBookLearningEventService {

    @Multipart
    @PUT("analytics/storybook-learning-events/csv")
    Call<ResponseBody> uploadCsvFile(@Part("csvFile") RequestBody csvFileRequestBody);
}
