package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class NumberLearningEvent (val numberValue: Int) : LearningEvent() {
    // TODO: numberValue

    var numberSymbol: String? = null

    var numberId: Long? = null

    @Deprecated("Will be replaced by `additionalData`")
    var learningEventType: LearningEventType? = null
}
