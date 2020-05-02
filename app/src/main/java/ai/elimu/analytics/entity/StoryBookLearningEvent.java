package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import ai.elimu.model.enums.analytics.LearningEventType;

@Entity
public class StoryBookLearningEvent extends LearningEvent {

    @NonNull
    private Long storyBookId;

    @NonNull
    private LearningEventType learningEventType;

    public Long getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
