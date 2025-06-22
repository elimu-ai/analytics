package ai.elimu.analytics.utils

/**
 * Defines a set of constant keys used for passing data between the Analytics app
 * and utils module via Bundles.
 *
 * ⚠️ **Important:** Do **not rename** or **delete** any of the values in this object.
 * These keys are relied upon by multiple versions of Analytics application that may be using
 * different versions of the shared utils library. Modifying any of the existing keys
 * will **break backward-compatibility** and may cause analytics data to become inconsistent or lost.
 *
 * Only **add new keys** when introducing new functionality.
 */
object BundleKeys {

    // BaseEntity
    const val KEY_ID = "id"

    // LearningEvent/AssessmentEvent
    const val KEY_ANDROID_ID = "androidId"
    const val KEY_PACKAGE_NAME = "packageName"
    const val KEY_TIMESTAMP = "timestamp"
    const val KEY_ADDITIONAL_DATA = "additionalData"

    // LearningEvent
    @Deprecated("Will be replaced by `additionalData`")
    const val KEY_LEARNING_EVENT_TYPE = "learningEventType"

    // AssessmentEvent
    const val KEY_TIME_SPENT_MS = "timeSpentMs"
    const val KEY_MASTERY_SCORE = "masteryScore"

    // LetterSoundAssessmentEvent/LetterSoundLearningEvent
    @Deprecated("Will be replaced by `letterSoundLetters`")
    const val KEY_LETTER_SOUND_LETTER_TEXTS = "letterSoundLetterTexts"

    @Deprecated("Will be replaced by `letterSoundSounds`")
    const val KEY_LETTER_SOUND_SOUND_VALUES_IPA = "letterSoundSoundValuesIpa"

    const val KEY_LETTER_SOUND_LETTERS = "letterSoundLetters"
    const val KEY_LETTER_SOUND_SOUNDS = "letterSoundSounds"
    const val KEY_LETTER_SOUND_ID = "letterSoundId"

    // WordAssessmentEvent/WordLearningEvent
    const val KEY_WORD_TEXT = "wordText"
    const val KEY_WORD_ID = "wordId"

    // NumberAssessmentEvent/NumberLearningEvent
    const val KEY_NUMBER_VALUE = "numberValue"
    const val KEY_NUMBER_SYMBOL = "numberSymbol"
    const val KEY_NUMBER_ID = "numberId"

    // StoryBookLearningEvent
    const val KEY_STORYBOOK_TITLE = "storyBookTitle"
    const val KEY_STORYBOOK_ID = "storyBookId"

    // VideoLearningEvent
    const val KEY_VIDEO_TITLE = "videoTitle"
    const val KEY_VIDEO_ID = "videoId"
}
