package ai.elimu.analytics.util

import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.utils.IntentAction

fun IntentAction.toAnalyticEvent(): AnalyticEventType {
    return when (this) {
        IntentAction.LETTER_SOUND_ASSESSMENT -> AnalyticEventType.LETTER_SOUND_ASSESSMENT
        IntentAction.LETTER_SOUND_LEARNING -> AnalyticEventType.LETTER_SOUND_LEARNING
        IntentAction.WORD_ASSESSMENT -> AnalyticEventType.WORD_ASSESSMENT
        IntentAction.NUMBER_LEARNING -> AnalyticEventType.NUMBER_LEARNING
        IntentAction.STORYBOOK_LEARNING -> AnalyticEventType.STORY_BOOK_LEARNING
        IntentAction.VIDEO_LEARNING -> AnalyticEventType.VIDEO_LEARNING
        IntentAction.WORD_LEARNING -> AnalyticEventType.WORD_LEARNING
    }
}