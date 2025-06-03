package ai.elimu.analytics.entity

import ai.elimu.model.v2.enums.analytics.LearningEventType
import androidx.room.Entity

@Entity
class StoryBookLearningEvent : LearningEvent() {
    var storyBookTitle: String = ""

    @JvmField
    var storyBookId: Long = 0L

    lateinit var learningEventType: LearningEventType

    override fun toString(): String {
        return "id: " + id +
                ", time: " + time.time +
                ", androidId: \"" + androidId +
                "\"" + ", packageName: \"" +
                packageName + "\"" +
                ", storyBookTitle: \"" + storyBookTitle + "\"" +
                ", storyBookId: " + storyBookId +
                ", learningEventType: \"" + learningEventType + "\""
    }
}
