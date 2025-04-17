package ai.elimu.analytics.language

import ai.elimu.analytics.R
import ai.elimu.analytics.language.LanguageListDialogFragment.Companion.newInstance
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class SelectLanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_select_language)
    }

    override fun onStart() {
        Timber.i("onStart")
        super.onStart()

        newInstance().show(supportFragmentManager, "dialog")
    }
}
