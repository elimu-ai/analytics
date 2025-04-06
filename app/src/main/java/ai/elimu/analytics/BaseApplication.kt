package ai.elimu.analytics

import ai.elimu.analytics.util.SharedPreferencesHelper.getLanguage
import ai.elimu.analytics.util.VersionHelper
import android.app.Application
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

class BaseApplication : Application() {
    override fun onCreate() {
        Log.i(javaClass.name, "onCreate")
        super.onCreate()

        // Log config 🪵
        plant(DebugTree())
        Timber.i("onCreate")

        VersionHelper.updateAppVersion(applicationContext)
    }

    val retrofit: Retrofit
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(restUrl + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    private val baseUrl: String
        /**
         * E.g. "https://eng.elimu.ai" or "https://hin.elimu.ai"
         */
        get() {
            val language =
                getLanguage(applicationContext)
            var url = "http://" + language!!.isoCode
            url += ".elimu.ai"
            return url
        }

    private val restUrl: String
        get() = baseUrl + "/rest/v2"
}
