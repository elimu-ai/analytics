package ai.elimu.analytics.rest;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LetterSoundLearningEventService {

    @Multipart
    @POST("analytics/letter-sound-learning-events/csv")
    Call<ResponseBody> uploadCsvFile(@Part MultipartBody.Part part);
}
