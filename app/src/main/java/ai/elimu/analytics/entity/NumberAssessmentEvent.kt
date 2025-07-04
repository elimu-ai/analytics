package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/NumberAssessmentEvent.java
 */
@Entity
class NumberAssessmentEvent : AssessmentEvent() {
    var numberValue: Int = Int.MIN_VALUE

    // TODO: numberSymbol

    /**
     * This field might not be included, e.g. if the event occurred in a 3rd-party app that did not
     * load the content from the elimu.ai Content Provider. In that case, this field will be {@code null}.
     */
    var numberId: Long? = null
}
