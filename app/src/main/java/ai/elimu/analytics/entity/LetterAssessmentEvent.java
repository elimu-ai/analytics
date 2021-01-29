package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class LetterAssessmentEvent extends AssessmentEvent {

    private Long letterId;

    @NonNull
    private String letterText;

    @NonNull
    private Float masteryScore;

    @NonNull
    private Long timeSpentMs;

    // TODO: assessmentEventType

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

    public Float getMasteryScore() {
        return masteryScore;
    }

    public void setMasteryScore(Float masteryScore) {
        this.masteryScore = masteryScore;
    }

    public Long getTimeSpentMs() {
        return timeSpentMs;
    }

    public void setTimeSpentMs(Long timeSpentMs) {
        this.timeSpentMs = timeSpentMs;
    }
}
