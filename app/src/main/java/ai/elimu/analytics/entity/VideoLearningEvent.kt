package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import ai.elimu.model.v2.enums.analytics.LearningEventType;

@Entity
public class VideoLearningEvent extends LearningEvent {

    private Long videoId;

    @NonNull
    private String videoTitle;

    @NonNull
    private LearningEventType learningEventType;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
