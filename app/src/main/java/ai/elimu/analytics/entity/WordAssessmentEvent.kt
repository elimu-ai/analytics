package ai.elimu.analytics.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class WordAssessmentEvent extends AssessmentEvent {

    private Long wordId;

    @NonNull
    private String wordText;

    @NonNull
    private Float masteryScore;

    @NonNull
    private Long timeSpentMs;

    // TODO: assessmentEventType

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
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
