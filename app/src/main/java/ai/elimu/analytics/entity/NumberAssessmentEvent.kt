package ai.elimu.analytics.entity

import androidx.room.Entity

@Entity
class NumberAssessmentEvent : AssessmentEvent() {
    var masteryScore: Float = -1f

    var timeSpentMs: Long = 0

    var numberValue: Int = Int.MIN_VALUE

    var numberId: Long? = 0
}
