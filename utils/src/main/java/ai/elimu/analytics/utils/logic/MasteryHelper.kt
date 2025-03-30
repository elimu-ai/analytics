package ai.elimu.analytics.utils.logic

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson

object MasteryHelper {
    /**
     * Definition of letter mastery: Answered correctly in the 3 most recent assessments.
     */
    fun isLetterMastered(letterAssessmentEventGsons: List<LetterAssessmentEventGson>): Boolean {
        // Verify minimum number of assessment events
        if (letterAssessmentEventGsons.size < 3) {
            return false
        }

        // Verify assessment correctness
        var correctInARowCount = 0
        for (letterAssessmentEventGson in letterAssessmentEventGsons) {
            if (letterAssessmentEventGson.masteryScore == 1.00f) {
                correctInARowCount++
            } else {
                correctInARowCount = 0
            }

            if (correctInARowCount == 3) {
                return true
            }
        }
        return false
    }

    /**
     * Definition of word mastery: Answered correctly in the 3 most recent assessments.
     */
    fun isWordMastered(wordAssessmentEventGsons: List<WordAssessmentEventGson>): Boolean {
        // Verify minimum number of assessment events
        if (wordAssessmentEventGsons.size < 3) {
            return false
        }

        // Verify assessment correctness
        var correctInARowCount = 0
        for (wordAssessmentEventGson in wordAssessmentEventGsons) {
            if (wordAssessmentEventGson.masteryScore == 1.00f) {
                correctInARowCount++
            } else {
                correctInARowCount = 0
            }

            if (correctInARowCount == 3) {
                return true
            }
        }
        return false
    }
}
