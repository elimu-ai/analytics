package ai.elimu.analytics.task

import ai.elimu.analytics.enum.EventType
import ai.elimu.analytics.entity.BaseEntity
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import ai.elimu.analytics.entity.LetterSoundLearningEvent
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.entity.StoryBookLearningEvent
import ai.elimu.analytics.entity.VideoLearningEvent
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.entity.WordLearningEvent

fun LetterSoundAssessmentEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.time.timeInMillis / 1_000,
        this.packageName,
        this.masteryScore,
        this.timeSpentMs,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.letterSoundLetters,
        this.letterSoundSounds,
        this.letterSoundId)
}

fun LetterSoundLearningEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.timestamp.timeInMillis / 1_000,
        this.packageName,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        null,
        null,
        this.id
    )
}

fun WordAssessmentEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.time.timeInMillis / 1_000,
        this.packageName,
        this.masteryScore,
        this.timeSpentMs,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.wordText,
        this.wordId
    )
}

fun WordLearningEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.timestamp.timeInMillis / 1_000,
        this.packageName,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.wordText,
        this.wordId
    )
}

fun NumberLearningEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.timestamp.timeInMillis / 1_000,
        this.packageName,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.numberValue,
        this.numberSymbol,
        this.numberId
    )
}

fun StoryBookLearningEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.timestamp.timeInMillis / 1_000,
        this.packageName,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.storyBookTitle,
        this.storyBookId
    )
}

fun VideoLearningEvent.getCSVFields(): List<Any?> {
    return listOf(
        this.id,
        this.timestamp.timeInMillis / 1_000,
        this.packageName,
        this.additionalData,
        this.researchExperiment?.ordinal,
        this.experimentGroup?.ordinal,
        this.videoTitle,
        this.videoId
    )
}

fun BaseEntity.getCSVFields(eventType: EventType): List<Any?> {
    return when (eventType) {
        EventType.LETTER_SOUND_ASSESSMENT -> (this as LetterSoundAssessmentEvent).getCSVFields()
        EventType.LETTER_SOUND_LEARNING -> (this as LetterSoundLearningEvent).getCSVFields()

        EventType.WORD_ASSESSMENT -> (this as WordAssessmentEvent).getCSVFields()
        EventType.WORD_LEARNING -> (this as WordLearningEvent).getCSVFields()

        EventType.NUMBER_LEARNING -> (this as NumberLearningEvent).getCSVFields()

        EventType.STORY_BOOK_LEARNING -> (this as StoryBookLearningEvent).getCSVFields()

        EventType.VIDEO_LEARNING -> (this as VideoLearningEvent).getCSVFields()
    }
}
