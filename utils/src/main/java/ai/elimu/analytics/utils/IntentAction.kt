package ai.elimu.analytics.utils

enum class IntentAction(val action: String) {
    LETTER_SOUND_ASSESSMENT("ai.elimu.intent.action.LETTER_SOUND_ASSESSMENT_EVENT"),
    LETTER_SOUND_LEARNING("ai.elimu.intent.action.LETTER_SOUND_LEARNING_EVENT"),

    WORD_ASSESSMENT("ai.elimu.intent.action.WORD_ASSESSMENT_EVENT"),
    WORD_LEARNING("ai.elimu.intent.action.WORD_LEARNING_EVENT"),

    NUMBER_LEARNING("ai.elimu.intent.action.NUMBER_LEARNING_EVENT"),

    STORYBOOK_LEARNING("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT"),

    VIDEO_LEARNING("ai.elimu.intent.action.VIDEO_LEARNING_EVENT")
}
