package ai.elimu.analytics.utils.logic;

import android.util.Log;

import java.util.List;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;

public class MasteryHelper {

    /**
     * Definition of letter mastery: Answered correctly in the 3 most recent assessments.
     */
    public static boolean isLetterMastered(List<LetterAssessmentEventGson> letterAssessmentEventGsons) {
//        Log.i(MasteryHelper.class.getName(), "getLetterMastery");

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
}
