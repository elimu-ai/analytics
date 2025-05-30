package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class WordLearningEvent : LearningEvent() {
    @JvmField
    var wordId: Long? = null

    lateinit var wordText: String

    lateinit var learningEventType: LearningEventType
}
