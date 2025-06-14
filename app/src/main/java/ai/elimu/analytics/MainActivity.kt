package ai.elimu.analytics

import ai.elimu.analytics.language.SelectLanguageActivity
import ai.elimu.analytics.task.TaskInitializer
import ai.elimu.analytics.util.SharedPreferencesHelper.getLanguage
import ai.elimu.common.utils.ui.setLightStatusBar
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        window.apply {
            setLightStatusBar()
        }
    }

    override fun onStart() {
        Timber.i("onStart")
        super.onStart()

        val language = getLanguage(
            applicationContext
        )
        Timber.i("language: $language")
        if (language == null) {
            // Redirect to language selection

            val selectLanguageIntent = Intent(
                applicationContext,
                SelectLanguageActivity::class.java
            )
            startActivity(selectLanguageIntent)
            finish()
        } else {
            // Redirect to event list

            TaskInitializer.initializePeriodicWork(applicationContext)

            startActivity(Intent(applicationContext, EventListActivity::class.java))
            finish()
        }
    }
}
