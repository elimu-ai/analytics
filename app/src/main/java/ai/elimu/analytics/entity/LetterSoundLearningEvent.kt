package ai.elimu.analytics.entity

import androidx.room.Entity

@Entity
class LetterSoundLearningEvent : LearningEvent() {
    @JvmField
    var letterSoundId: Long? = null

    @JvmField
    var letterSoundLetterTexts: Array<String> = arrayOf()

    @JvmField
    var letterSoundSoundValuesIpa: Array<String> = arrayOf()
}
