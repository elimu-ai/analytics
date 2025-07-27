package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LetterSoundLearningEvent.java
 */
@Entity
class LetterSoundLearningEvent : LearningEvent() {
    @Deprecated("Will be replaced by `letterSoundLetters`")
    lateinit var letterSoundLetterTexts: Array<String>

    @Deprecated("Will be replaced by `letterSoundSounds`")
    lateinit var letterSoundSoundValuesIpa: Array<String>

    /**
     * The sequence of letters. E.g. `["s","h"]`.
     */
    lateinit var letterSoundLetters: List<String>

    /**
     *  The sequence of sounds (IPA values). E.g. `["ʃ"]`.
     */
    lateinit var letterSoundSounds: List<String>

    /**
     * This field might not be included, e.g. if the event occurred in a 3rd-party app that did not
     * load the content from the elimu.ai Content Provider. In that case, this field will be {@code null}.
     */
    var letterSoundId: Long? = null
}
