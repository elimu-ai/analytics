package ai.elimu.analytics

import ai.elimu.analytics.databinding.ActivityEventListBinding
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.util.Clipboard
import ai.elimu.common.utils.ui.setLightStatusBar
import ai.elimu.common.utils.ui.setStatusBarColorCompat
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import timber.log.Timber

class EventListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)

        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val androidId: String? = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        if (androidId?.isEmpty() == false) {
            binding.ivCopy.visibility = View.VISIBLE
            binding.tvAndroidIdValue.text = androidId
        } else {
            binding.ivCopy.visibility = View.GONE
        }

        binding.ivCopy.setOnClickListener {
            Clipboard.copy(this, binding.tvAndroidIdValue.text.toString())
        }

        val recyclerView = binding.recyclerview
        eventListAdapter = EventListAdapter(this)
        recyclerView.adapter = eventListAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        window.apply {
            setLightStatusBar()
            setStatusBarColorCompat(R.color.colorPrimaryDark)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchEvents()
    }

    /**
     * Fetch event counts from database, and update adapter
     */
    private fun fetchEvents() {
        val roomDb = RoomDb.getDatabase(applicationContext)
        RoomDb.databaseWriteExecutor.execute {
            try {
                val eventTypeCounts = listOf(
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_letter_sound_assessment,
                            roomDb.letterSoundAssessmentEventDao().getCount())
                    ),
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_word_assessment,
                            roomDb.wordAssessmentEventDao().getCount())
                    ),
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_letter_sound_learning,
                            roomDb.letterSoundLearningEventDao().getCount())
                    ),
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_word_learning,
                            roomDb.wordLearningEventDao().getCount())
                    ),
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_storybook_learning,
                            roomDb.storyBookLearningEventDao().getCount())
                    ),
                    EventListAdapter.EventTypeCount(
                        getString(R.string.event_label_video_learning,
                            roomDb.videoLearningEventDao().getCount())
                    )
                )
                runOnUiThread {
                    eventListAdapter.setEventTypeCounts(eventTypeCounts)
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to load event counts")
                runOnUiThread {
                    // Show error state or empty list
                    eventListAdapter.setEventTypeCounts(emptyList())
                }
            }
        }
    }
}
