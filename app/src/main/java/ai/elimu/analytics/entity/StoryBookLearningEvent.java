package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import ai.elimu.model.v2.enums.analytics.LearningEventType;

@Entity
public class StoryBookLearningEvent extends LearningEvent {

    @NonNull
    private Long storyBookId;

    private String storyBookTitle;

    @NonNull
    private LearningEventType learningEventType;

    public Long getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }

    public String getStoryBookTitle() {
        return storyBookTitle;
    }

    public void setStoryBookTitle(String storyBookTitle) {
        this.storyBookTitle = storyBookTitle;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
