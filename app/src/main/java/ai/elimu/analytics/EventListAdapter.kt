package ai.elimu.analytics

import ai.elimu.analytics.EventListAdapter.EventViewHolder
import ai.elimu.analytics.databinding.ActivityEventListItemBinding
import ai.elimu.analytics.entity.StoryBookLearningEvent
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventListAdapter internal constructor(context: Context?) :
    RecyclerView.Adapter<EventViewHolder>() {
    inner class EventViewHolder internal constructor(binding: ActivityEventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textViewFirstLine: TextView = binding.textViewFirstLine
        val textViewSecondLine: TextView = binding.textViewSecondLine
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var storyBookLearningEvents: List<StoryBookLearningEvent> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(ActivityEventListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
        val storyBookLearningEvent = storyBookLearningEvents[position]
        viewHolder.textViewFirstLine.text = "StoryBookLearningEvent"
        viewHolder.textViewSecondLine.text = storyBookLearningEvent.toString()
    }

    override fun getItemCount(): Int {
        return storyBookLearningEvents.size
    }

    fun setStoryBookLearningEvents(storyBookLearningEvents: List<StoryBookLearningEvent>) {
        this.storyBookLearningEvents = storyBookLearningEvents
        notifyDataSetChanged()
    }
}
