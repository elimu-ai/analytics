package ai.elimu.analytics.entity

import ai.elimu.analytics.rest.LetterAssessmentEventService
import ai.elimu.analytics.rest.LetterLearningEventService
import ai.elimu.analytics.rest.LetterSoundLearningEventService
import ai.elimu.analytics.rest.StoryBookLearningEventService
import ai.elimu.analytics.rest.UploadService
import ai.elimu.analytics.rest.WordAssessmentEventService
import ai.elimu.analytics.rest.WordLearningEventService

enum class LearningEventUploadType(val type: String) {
    LETTER_LEARNING("letter-learning-events"),
    LETTER_ASSESSMENT("letter-assessment-events"),
    LETTER_SOUND_LEARNING("letter-sound-learning-events"),
    STORY_BOOK_LEARNING("storybook-learning-events"),
    WORD_ASSESSMENT("word-assessment-events"),
    WORD_LEARNING("word-learning-events")
}

fun LearningEventUploadType.toServiceClass(): Class<out UploadService> {
    return when (this) {
        LearningEventUploadType.LETTER_LEARNING -> LetterLearningEventService::class.java
        LearningEventUploadType.LETTER_ASSESSMENT -> LetterAssessmentEventService::class.java
        LearningEventUploadType.LETTER_SOUND_LEARNING -> LetterSoundLearningEventService::class.java
        LearningEventUploadType.STORY_BOOK_LEARNING -> StoryBookLearningEventService::class.java
        LearningEventUploadType.WORD_ASSESSMENT -> WordAssessmentEventService::class.java
        LearningEventUploadType.WORD_LEARNING -> WordLearningEventService::class.java
    }
}