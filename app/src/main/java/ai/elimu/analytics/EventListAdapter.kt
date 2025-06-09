package ai.elimu.analytics

import ai.elimu.analytics.databinding.ActivityEventListItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventListAdapter internal constructor(context: Context?) :
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    data class EventTypeCount(val label: String)

    inner class EventViewHolder internal constructor(binding: ActivityEventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textViewEventCount: TextView = binding.tvEventCount
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var eventTypeCounts: List<EventTypeCount> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(ActivityEventListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
        val eventTypeCount = eventTypeCounts[position]
        viewHolder.textViewEventCount.text = eventTypeCount.label
    }

    override fun getItemCount(): Int {
        return eventTypeCounts.size
    }

    fun setEventTypeCounts(eventTypeCounts: List<EventTypeCount>) {
        this.eventTypeCounts = eventTypeCounts
        notifyDataSetChanged()
    }
}
