package ai.elimu.analytics.task

import ai.elimu.analytics.BaseApplication
import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.toServiceClass
import ai.elimu.analytics.util.SharedPreferencesHelper
import ai.elimu.analytics.util.VersionHelper.extractVersionCode
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.Arrays

/**
 * Uploads CSV files previously generated by the [ExportEventsToCsvWorker]
 */
class UploadEventsWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Timber.i("doWork")

        uploadLearningEvents(eventType = AnalyticEventType.LETTER_SOUND_ASSESSMENT)
        uploadLearningEvents(eventType = AnalyticEventType.LETTER_SOUND_LEARNING)
        uploadLearningEvents(eventType = AnalyticEventType.WORD_LEARNING)
        uploadLearningEvents(eventType = AnalyticEventType.WORD_ASSESSMENT)
        uploadLearningEvents(eventType = AnalyticEventType.STORY_BOOK_LEARNING)

        return Result.success()
    }

    private fun uploadLearningEvents(eventType: AnalyticEventType) {
        Timber.i("uploadLearningEvents: $eventType")

        // Upload CSV files to the server
        // Example format:
        //   files/lang-HIN/letter-assessment-events/7161a85a0e4751cd_3003002_letter-assessment-events_2025-06-07.csv
        val filesDir = applicationContext.filesDir
        val language = SharedPreferencesHelper.getLanguage(applicationContext)
        Timber.i("language: ${language}")
        for (file in filesDir.listFiles() ?: emptyArray()) {
            Timber.i("file.name: ${file.name}")
            if (file.name.startsWith("lang-${language}")) {
                val eventsDir = File(file, eventType.type)
                Timber.i("Uploading CSV files from ${eventsDir}")
                val files = eventsDir.listFiles()
                if (files != null) {
                    Timber.i("files.length: %s", files.size)
                    Arrays.sort(files)
                    for (i in files.indices) {
                        val file = files[i]
                        Timber.i("file.getAbsoluteFile(): %s", file.absoluteFile)
                        Timber.i("file.getName(): %s", file.name)
                        val logFileVersionCode = file.name.extractVersionCode()
                        if (logFileVersionCode != BuildConfig.VERSION_CODE) continue

                        val baseApplication = applicationContext as BaseApplication
                        val retrofit = baseApplication.retrofit
                        val uploadService = retrofit.create(eventType.toServiceClass())
                        val requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
                        val call = uploadService.uploadCsvFile(part)

                        Timber.i("call.request(): %s", call.request())
                        try {
                            val response = call.execute()
                            Timber.i("response: $response")
                            Timber.i("response.isSuccessful(): %s", response.isSuccessful)
                            if (response.isSuccessful) {
                                val bodyString = response.body()?.string()
                                Timber.i("bodyString: $bodyString")
                            } else {
                                val errorBodyString = response.errorBody()?.string()
                                Timber.e("errorBodyString: $errorBodyString")
                                // TODO: Handle error
                            }
                        } catch (e: IOException) {
                            Timber.e(e)
                        }
                    }
                }
            }
        }
    }
}
