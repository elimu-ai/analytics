package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class LetterSoundLearningEvent extends LearningEvent {

    private Long letterSoundId;

    @NonNull
    private String[] letterSoundLetterTexts;

    @NonNull
    private String[] letterSoundSoundValuesIpa;

    public Long getLetterSoundId() {
        return letterSoundId;
    }

    public void setLetterSoundId(Long letterSoundId) {
        this.letterSoundId = letterSoundId;
    }

    @NonNull
    public String[] getLetterSoundLetterTexts() {
        return letterSoundLetterTexts;
    }

    public void setLetterSoundLetterTexts(@NonNull String[] letterSoundLetterTexts) {
        this.letterSoundLetterTexts = letterSoundLetterTexts;
    }

    @NonNull
    public String[] getLetterSoundSoundValuesIpa() {
        return letterSoundSoundValuesIpa;
    }

    public void setLetterSoundSoundValuesIpa(@NonNull String[] letterSoundSoundValuesIpa) {
        this.letterSoundSoundValuesIpa = letterSoundSoundValuesIpa;
    }
}
