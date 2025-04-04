package ai.elimu.analytics.rest

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LetterAssessmentEventService {
    @Multipart
    @POST("analytics/letter-assessment-events/csv")
    fun uploadCsvFile(@Part part: MultipartBody.Part?): Call<ResponseBody?>
}
