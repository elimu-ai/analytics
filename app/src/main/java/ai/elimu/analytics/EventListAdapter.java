package ai.elimu.analytics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        private EventViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
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
            viewHolder.textView.setText("id: " + storyBookLearningEvent.getId() + ", storyBookId: " + storyBookLearningEvent.getStoryBookId());
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
    }
}
