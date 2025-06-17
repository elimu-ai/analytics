package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class NumberLearningEvent (val numberValue: Int) : LearningEvent() {

    var numberId: Long? = null

    lateinit var numberSymbol: String

    var learningEventType: LearningEventType? = null
}
