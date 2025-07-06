package ai.elimu.analytics.task

import ai.elimu.analytics.utils.BundleKeys

object CSVHeaders {

    val LETTER_SOUND_ASSESSMENT by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_MASTERY_SCORE,
        BundleKeys.KEY_TIME_SPENT_MS,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_LETTER_SOUND_LETTERS,
        BundleKeys.KEY_LETTER_SOUND_SOUNDS,
        BundleKeys.KEY_LETTER_SOUND_ID) }

    val LETTER_SOUND_LEARNING by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_LETTER_SOUND_LETTER_TEXTS,
        BundleKeys.KEY_LETTER_SOUND_SOUND_VALUES_IPA,
        BundleKeys.KEY_LETTER_SOUND_ID) }


    val WORD_ASSESSMENT by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_MASTERY_SCORE,
        BundleKeys.KEY_TIME_SPENT_MS,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_WORD_TEXT,
        BundleKeys.KEY_WORD_ID) }

    val WORD_LEARNING by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_WORD_TEXT,
        BundleKeys.KEY_WORD_ID
    ) }


    val NUMBER_LEARNING by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_NUMBER_VALUE,
        BundleKeys.KEY_NUMBER_SYMBOL,
        BundleKeys.KEY_NUMBER_ID
    ) }

    // TODO: number assessment


    val STORYBOOK_LEARNING by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_STORYBOOK_TITLE,
        BundleKeys.KEY_STORYBOOK_ID
    ) }


    val VIDEO_LEARNING by lazy { arrayOf(
        BundleKeys.KEY_ID,
        BundleKeys.KEY_TIMESTAMP,
        BundleKeys.KEY_PACKAGE_NAME,
        BundleKeys.KEY_ADDITIONAL_DATA,
        "research_experiment",
        "experiment_group",
        BundleKeys.KEY_VIDEO_TITLE,
        BundleKeys.KEY_VIDEO_ID
    ) }
}
