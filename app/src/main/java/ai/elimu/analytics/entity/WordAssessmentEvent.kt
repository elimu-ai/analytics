package ai.elimu.analytics.entity

import androidx.room.Entity

@Entity
class WordAssessmentEvent : AssessmentEvent() {
    lateinit var wordText: String

    var wordId: Long? = null

    var masteryScore: Float = 0f

    var timeSpentMs: Long = 0L
}
