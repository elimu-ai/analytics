package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/NumberAssessmentEvent.java
 */
@Entity
class NumberAssessmentEvent : AssessmentEvent() {
    /**
     * The number represented as an Integer. E.g. `10`.
     */
    var numberValue: Int = Int.MIN_VALUE

    /**
     * The number represented as a symbol specific to the language. E.g. "१०" for `10` in Hindi.
     */
    var numberSymbol: String? = null

    /**
     * This field might not be included, e.g. if the event occurred in a 3rd-party app that did not
     * load the content from the elimu.ai Content Provider. In that case, this field will be {@code null}.
     */
    var numberId: Long? = null
}
