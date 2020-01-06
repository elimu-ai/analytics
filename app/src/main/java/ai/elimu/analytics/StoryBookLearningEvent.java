package ai.elimu.analytics;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StoryBookLearningEvent {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    // TODO: calendar

    @NonNull
    private Long storyBookId;

    // TODO: learningEventType

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }
}
