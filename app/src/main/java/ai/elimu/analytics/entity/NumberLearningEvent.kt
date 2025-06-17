package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class NumberLearningEvent : LearningEvent() {

    var numberId: Long? = null

    var number: Long? = null

    var learningEventType: LearningEventType? = null
}
