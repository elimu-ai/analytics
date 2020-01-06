package ai.elimu.analytics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventItemView;

        private EventViewHolder(View itemView) {
            super(itemView);
            eventItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater layoutInflater;

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
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // TODO
    }

    @Override
    public int getItemCount() {
        // TODO
        return 0;
    }
}
