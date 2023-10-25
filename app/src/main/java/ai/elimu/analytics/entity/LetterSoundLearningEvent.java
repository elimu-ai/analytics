package ai.elimu.analytics.entity;

import androidx.room.Entity;

@Entity
public class LetterSoundLearningEvent extends LearningEvent {

    private Long letterSoundId;

    public Long getLetterSoundId() {
        return letterSoundId;
    }

    public void setLetterSoundId(Long letterSoundId) {
        this.letterSoundId = letterSoundId;
    }
}
