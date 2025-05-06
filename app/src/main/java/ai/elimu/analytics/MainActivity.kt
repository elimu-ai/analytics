package ai.elimu.analytics

import ai.elimu.analytics.db.RoomDb
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

            // If the database is not empty, redirect the user to the EventListActivity
            val roomDb = RoomDb.getDatabase(applicationContext)
            val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
            val wordLearningEventDao = roomDb.wordLearningEventDao()
            RoomDb.databaseWriteExecutor.execute {
                val wordLearningEvents =
                    wordLearningEventDao.loadAllOrderedByTimeDesc()
                Timber.i("wordLearningEvents.size(): %s", wordLearningEvents.size)

                val storyBookLearningEvents =
                    storyBookLearningEventDao.loadAll()
                Timber.i("storyBookLearningEvents.size(): %s", storyBookLearningEvents.size)
                if (storyBookLearningEvents.isNotEmpty()) {
                    startActivity(Intent(applicationContext, EventListActivity::class.java))
                    finish()
                }
            }
        }
    }
}
