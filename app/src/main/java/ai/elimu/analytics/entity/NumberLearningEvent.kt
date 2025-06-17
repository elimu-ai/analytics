package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity(tableName = "NumberLearningEvent")
class NumberLearningEvent (val numberValue: Int)
    : LearningEvent() {

    var numberId: Long? = null

    var numberSymbol: String? = null

    var learningEventType: LearningEventType? = null
}
