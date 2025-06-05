package ai.elimu.analytics

import ai.elimu.analytics.databinding.ActivityEventListBinding
import ai.elimu.analytics.db.RoomDb
import ai.elimu.common.utils.ui.setLightStatusBar
import ai.elimu.common.utils.ui.setStatusBarColorCompat
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import timber.log.Timber

class EventListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)

        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)?.let {
            binding.tvStudentId.text = getString(R.string.student_id, it)
        }

        val recyclerView = binding.recyclerview
        val eventListAdapter = EventListAdapter(this)
        recyclerView.adapter = eventListAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Fetch all learning events from database, and update adapter
        val roomDb = RoomDb.getDatabase(applicationContext)
        val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
        RoomDb.databaseWriteExecutor.execute {
            val storyBookLearningEvents =
                storyBookLearningEventDao.loadAll()
            Timber.d("storyBookLearningEvents.size(): %s", storyBookLearningEvents.size)
            eventListAdapter.setStoryBookLearningEvents(storyBookLearningEvents)
        }

        window.apply {
            setLightStatusBar()
            setStatusBarColorCompat(R.color.colorPrimaryDark)
        }
    }
}
