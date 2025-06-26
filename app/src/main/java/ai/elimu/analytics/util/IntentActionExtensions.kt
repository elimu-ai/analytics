package ai.elimu.analytics.util

import ai.elimu.analytics.enum.EventType
import ai.elimu.analytics.utils.IntentAction

fun IntentAction.toEventType(): EventType {
    return when (this) {
        IntentAction.LETTER_SOUND_ASSESSMENT -> EventType.LETTER_SOUND_ASSESSMENT
        IntentAction.LETTER_SOUND_LEARNING -> EventType.LETTER_SOUND_LEARNING

        IntentAction.WORD_ASSESSMENT -> EventType.WORD_ASSESSMENT
        IntentAction.WORD_LEARNING -> EventType.WORD_LEARNING

        IntentAction.NUMBER_LEARNING -> EventType.NUMBER_LEARNING
        // TODO: number assessment

        IntentAction.STORYBOOK_LEARNING -> EventType.STORY_BOOK_LEARNING

        IntentAction.VIDEO_LEARNING -> EventType.VIDEO_LEARNING
    }
}
