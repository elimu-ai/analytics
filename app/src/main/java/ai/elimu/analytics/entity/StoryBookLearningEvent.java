package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import ai.elimu.model.enums.analytics.LearningEventType;

@Entity
public class StoryBookLearningEvent {

    @PrimaryKey(autoGenerate = true)
    private Long id;


    @NonNull
    private String androidId;

    @NonNull
    private String packageName;

    @NonNull
    private Calendar time;


    @NonNull
    private Long storyBookId;

    private LearningEventType learningEventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

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
