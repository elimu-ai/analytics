package ai.elimu.analytics.utils.logic;

import java.util.List;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;

public class MasteryHelper {

    /**
     * Definition of letter mastery: Answered correctly in the 3 most recent assessments.
     */
    public static boolean isLetterMastered(List<LetterAssessmentEventGson> letterAssessmentEventGsons) {
        // Verify minimum number of assessment events
        if (letterAssessmentEventGsons.size() < 3) {
            return false;
        }

        // Verify assessment correctness
        int correctInARowCount = 0;
        for (LetterAssessmentEventGson letterAssessmentEventGson : letterAssessmentEventGsons) {
            if (letterAssessmentEventGson.getMasteryScore() == 1.00f) {
                correctInARowCount++;
            } else {
                correctInARowCount = 0;
            }

            if (correctInARowCount == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Definition of word mastery: Answered correctly in the 3 most recent assessments.
     */
    public static boolean isWordMastered(List<WordAssessmentEventGson> wordAssessmentEventGsons) {
        // Verify minimum number of assessment events
        if (wordAssessmentEventGsons.size() < 3) {
            return false;
        }

        // Verify assessment correctness
        int correctInARowCount = 0;
        for (WordAssessmentEventGson wordAssessmentEventGson : wordAssessmentEventGsons) {
            if (wordAssessmentEventGson.getMasteryScore() == 1.00f) {
                correctInARowCount++;
            } else {
                correctInARowCount = 0;
            }

            if (correctInARowCount == 3) {
                return true;
            }
        }
        return false;
    }
}
