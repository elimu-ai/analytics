package ai.elimu.analytics.entity;

import androidx.room.Entity;

@Entity
public class LetterSoundCorrespondenceLearningEvent extends LearningEvent {

    private Long letterSoundCorrespondenceLearningEventId;

    public Long getLetterSoundCorrespondenceLearningEventId() {
        return letterSoundCorrespondenceLearningEventId;
    }

    public void setLetterSoundCorrespondenceLearningEventId(Long letterSoundCorrespondenceLearningEventId) {
        this.letterSoundCorrespondenceLearningEventId = letterSoundCorrespondenceLearningEventId;
    }
}
