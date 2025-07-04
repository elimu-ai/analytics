package ai.elimu.analytics.entity

import androidx.room.Entity

@Entity
class LetterSoundLearningEvent : LearningEvent() {
    var letterSoundId: Long? = null

    lateinit var letterSoundLetterTexts: Array<String>

    lateinit var letterSoundSoundValuesIpa: Array<String>
}
