package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import ai.elimu.model.enums.analytics.LearningEventType;

@Entity
public class LetterLearningEvent extends LearningEvent {

    private Long letterId;

    @NonNull
    private String letterText;

    @NonNull
    private LearningEventType learningEventType;

    public Long getLetterId() {
        return letterId;
    }

    public void setLetterId(Long letterId) {
        this.letterId = letterId;
    }

    public String getLetterText() {
        return letterText;
    }

    public void setLetterText(String letterText) {
        this.letterText = letterText;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
