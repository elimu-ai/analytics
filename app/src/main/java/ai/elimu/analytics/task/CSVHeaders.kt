package ai.elimu.analytics.task

object CSVHeaders {

    val LETTER_SOUND_ASSESSMENT by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "mastery_score",
        "time_spent_ms",
        "research_experiment",
        "experiment_group",
        "letter_sound_letters",
        "letter_sound_sounds",
        "letter_sound_id") }

    val LETTER_SOUND_LEARNING by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "additional_data",
        "research_experiment",
        "experiment_group",
        "letter_sound_letter_texts",
        "letter_sound_sound_values_ipa",
        "letter_sound_id") }

    val WORD_ASSESSMENT by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "mastery_score",
        "time_spent_ms",
        "research_experiment",
        "experiment_group",
        "word_text",
        "word_id") }

    val WORD_LEARNING by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "additional_data",
        "learning_event_type",
        "research_experiment",
        "experiment_group",
        "word_text",
        "word_id"
    ) }

    val NUMBER_LEARNING by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "additional_data",
        "learning_event_type",
        "research_experiment",
        "experiment_group",
        "number_value",
        "number_symbol",
        "number_id"
    ) }

    val STORYBOOK_LEARNING by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "additional_data",
        "learning_event_type",
        "research_experiment",
        "experiment_group",
        "storybook_title",
        "storybook_id"
    ) }

    val VIDEO_LEARNING by lazy { arrayOf(
        "id",
        "timestamp",
        "package_name",
        "additional_data",
        "learning_event_type",
        "research_experiment",
        "experiment_group",
        "video_title",
        "video_id"
    ) }
}