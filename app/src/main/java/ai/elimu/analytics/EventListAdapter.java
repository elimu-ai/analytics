package ai.elimu.analytics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewFirstLine;
        private final TextView textViewSecondLine;

        private EventViewHolder(View itemView) {
            super(itemView);
            textViewFirstLine = itemView.findViewById(R.id.textViewFirstLine);
            textViewSecondLine = itemView.findViewById(R.id.textViewSecondLine);
        }
    }

    private final LayoutInflater layoutInflater;

    private List<StoryBookLearningEvent> storyBookLearningEvents;

    EventListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.activity_event_list_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder viewHolder, int position) {
        if (storyBookLearningEvents != null) {
            StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(position);
            viewHolder.textViewFirstLine.setText("StoryBookLearningEvent");
            viewHolder.textViewSecondLine.setText("id: " + storyBookLearningEvent.getId()
                    + ", time: " + storyBookLearningEvent.time.getTime()
                    + ", androidId: \"" + storyBookLearningEvent.androidId + "\""
                    + ", packageName: \"" + storyBookLearningEvent.packageName + "\""
                    + ", storyBookId: " + storyBookLearningEvent.getStoryBookId()
                    + ", learningEventType: \"" + storyBookLearningEvent.getLearningEventType() + "\"");
        }
    }

    @Override
    public int getItemCount() {
        if (storyBookLearningEvents == null) {
            return 0;
        } else {
            return storyBookLearningEvents.size();
        }
    }

    public void setStoryBookLearningEvents(List<StoryBookLearningEvent> storyBookLearningEvents) {
        this.storyBookLearningEvents = storyBookLearningEvents;
        notifyDataSetChanged();
    }
}
