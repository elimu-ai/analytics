package ai.elimu.analytics

import ai.elimu.analytics.EventListAdapter.EventViewHolder
import ai.elimu.analytics.entity.StoryBookLearningEvent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventListAdapter internal constructor(context: Context?) :
    RecyclerView.Adapter<EventViewHolder>() {
    inner class EventViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textViewFirstLine: TextView = itemView.findViewById(R.id.textViewFirstLine)
        val textViewSecondLine: TextView = itemView.findViewById(R.id.textViewSecondLine)
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var storyBookLearningEvents: List<StoryBookLearningEvent> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = layoutInflater.inflate(R.layout.activity_event_list_item, parent, false)
        return EventViewHolder(itemView)
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
