package ai.elimu.analytics.entity

import ai.elimu.analytics.rest.LetterSoundAssessmentEventService
import ai.elimu.analytics.rest.LetterSoundLearningEventService
import ai.elimu.analytics.rest.StoryBookLearningEventService
import ai.elimu.analytics.rest.UploadService
import ai.elimu.analytics.rest.WordAssessmentEventService
import ai.elimu.analytics.rest.WordLearningEventService

enum class AnalyticEventType(val type: String) {
    LETTER_SOUND_ASSESSMENT("letter-sound-assessment-events"),
    LETTER_SOUND_LEARNING("letter-sound-learning-events"),
    STORY_BOOK_LEARNING("storybook-learning-events"),
    WORD_ASSESSMENT("word-assessment-events"),
    WORD_LEARNING("word-learning-events")
}

fun AnalyticEventType.toServiceClass(): Class<out UploadService> {
    return when (this) {
        AnalyticEventType.LETTER_SOUND_ASSESSMENT -> LetterSoundAssessmentEventService::class.java
        AnalyticEventType.LETTER_SOUND_LEARNING -> LetterSoundLearningEventService::class.java
        AnalyticEventType.STORY_BOOK_LEARNING -> StoryBookLearningEventService::class.java
        AnalyticEventType.WORD_ASSESSMENT -> WordAssessmentEventService::class.java
        AnalyticEventType.WORD_LEARNING -> WordLearningEventService::class.java
    }
}
