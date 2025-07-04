package ai.elimu.analytics.entity

import androidx.room.Entity

/**
 * Based on https://github.com/elimu-ai/webapp/blob/main/src/main/java/ai/elimu/entity/analytics/LetterSoundAssessmentEvent.java
 */
@Entity
class LetterSoundAssessmentEvent : AssessmentEvent() {
    /**
     * The sequence of letters. E.g. `sh`.
     */
    lateinit var letterSoundLetters: String

    /**
     * The sequence of sounds (IPA values). E.g. `ʃ`.
     */
    lateinit var letterSoundSounds: String

    /**
     * This field might not be included, e.g. if the assessment task was done in a
     * 3rd-party app that did not load the content from the elimu.ai Content Provider.
     * In this case, the `letterSoundId` will be `null`.
     */
    var letterSoundId: Long? = null
}
