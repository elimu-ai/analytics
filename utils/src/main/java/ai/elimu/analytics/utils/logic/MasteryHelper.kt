package ai.elimu.analytics.utils.logic

import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson

object MasteryHelper {
    /**
     * Definition of word mastery: Answered correctly in the 3 most recent assessments.
     */
    @JvmStatic
    fun isWordMastered(wordAssessmentEventGsons: List<WordAssessmentEventGson>): Boolean {
        // Verify minimum number of assessment events
        if (wordAssessmentEventGsons.size < 3) {
            return false
        }

        // Verify assessment correctness
        wordAssessmentEventGsons.sortedBy { it.timestamp }.takeLast(3).forEach {
            if (it.masteryScore != 1.00f) return false
        }
        return true
    }
}
