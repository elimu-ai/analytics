package ai.elimu.analytics.entity;

import androidx.room.Entity;

@Entity
public class LetterSoundCorrespondenceLearningEvent extends LearningEvent {

    private Long letterSoundCorrespondenceId;

    public Long getLetterSoundCorrespondenceId() {
        return letterSoundCorrespondenceId;
    }

    public void setLetterSoundCorrespondenceId(Long letterSoundCorrespondenceId) {
        this.letterSoundCorrespondenceId = letterSoundCorrespondenceId;
    }
}
