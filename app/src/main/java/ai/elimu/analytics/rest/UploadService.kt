package ai.elimu.analytics.rest

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Part

interface UploadService {
    fun uploadCsvFile(@Part part: MultipartBody.Part): Call<ResponseBody?>
}