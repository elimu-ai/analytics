package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/NumberAssessmentEvent.java
 */
@Entity
class NumberAssessmentEvent : AssessmentEvent() {
    var numberValue: Int = Int.MIN_VALUE

    // TODO: numberSymbol

    var numberId: Long? = null
}
