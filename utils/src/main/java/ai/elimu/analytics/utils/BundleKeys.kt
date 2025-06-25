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
    const val KEY_ANDROID_ID = "android_id"
    const val KEY_PACKAGE_NAME = "package_name"
    const val KEY_TIMESTAMP = "timestamp"
    const val KEY_ADDITIONAL_DATA = "additional_data"

    // LearningEvent
    @Deprecated("Will be replaced by `additionalData`")
    const val KEY_LEARNING_EVENT_TYPE = "learning_event_type"
    const val KEY_INTENT_ACTION = "intent_action"

    // AssessmentEvent
    const val KEY_TIME_SPENT_MS = "time_spent_ms"
    const val KEY_MASTERY_SCORE = "mastery_score"

    // LetterSoundAssessmentEvent/LetterSoundLearningEvent
    @Deprecated("Will be replaced by `letterSoundLetters`")
    const val KEY_LETTER_SOUND_LETTER_TEXTS = "letter_sound_letter_texts"

    @Deprecated("Will be replaced by `letterSoundSounds`")
    const val KEY_LETTER_SOUND_SOUND_VALUES_IPA = "letter_sound_sound_values_ipa"

    const val KEY_LETTER_SOUND_LETTERS = "letter_sound_letters"
    const val KEY_LETTER_SOUND_SOUNDS = "letter_sound_sounds"
    const val KEY_LETTER_SOUND_ID = "letter_sound_id"

    // WordAssessmentEvent/WordLearningEvent
    const val KEY_WORD_TEXT = "word_text"
    const val KEY_WORD_ID = "word_id"

    // NumberAssessmentEvent/NumberLearningEvent
    const val KEY_NUMBER_VALUE = "number_value"
    const val KEY_NUMBER_SYMBOL = "number_symbol"
    const val KEY_NUMBER_ID = "number_id"

    // StoryBookLearningEvent
    const val KEY_STORYBOOK_TITLE = "storybook_title"
    const val KEY_STORYBOOK_ID = "storybook_id"

    // VideoLearningEvent
    const val KEY_VIDEO_TITLE = "video_title"
    const val KEY_VIDEO_ID = "video_id"
}
