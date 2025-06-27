package ai.elimu.analytics.entity

import androidx.room.Entity

@Entity
class WordAssessmentEvent : AssessmentEvent() {
    // TODO: assessmentEventType
    @JvmField
    var wordId: Long? = null

    lateinit var wordText: String

    @JvmField
    var masteryScore: Float = 0f

    @JvmField
    var timeSpentMs: Long = 0L
}
