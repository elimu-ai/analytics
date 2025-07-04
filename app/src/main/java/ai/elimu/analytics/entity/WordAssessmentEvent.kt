package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/WordAssessmentEvent.java
 */
@Entity
class WordAssessmentEvent : AssessmentEvent() {
    lateinit var wordText: String

    var wordId: Long? = null

    var masteryScore: Float = 0f

    var timeSpentMs: Long = 0L
}
