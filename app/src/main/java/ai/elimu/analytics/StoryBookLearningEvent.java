package ai.elimu.analytics;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class StoryBookLearningEvent {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Calendar timestamp;

    @NonNull
    private Long storyBookId;

    // TODO: learningEventType

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public Long getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }
}
