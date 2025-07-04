package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LetterSoundLearningEvent.java
 */
@Entity
class LetterSoundLearningEvent : LearningEvent() {
    var letterSoundId: Long? = null

    lateinit var letterSoundLetterTexts: Array<String>

    lateinit var letterSoundSoundValuesIpa: Array<String>
}
