package ai.elimu.analytics.entity

import ai.elimu.analytics.rest.LetterSoundAssessmentEventService
import ai.elimu.analytics.rest.LetterSoundLearningEventService
import ai.elimu.analytics.rest.NumberLearningEventService
import ai.elimu.analytics.rest.StoryBookLearningEventService
import ai.elimu.analytics.rest.UploadService
import ai.elimu.analytics.rest.VideoLearningEventService
import ai.elimu.analytics.rest.WordAssessmentEventService
import ai.elimu.analytics.rest.WordLearningEventService
import ai.elimu.analytics.util.SharedPreferencesHelper
import android.content.Context
import timber.log.Timber
import java.io.File

enum class AnalyticEventType(val type: String) {
    LETTER_SOUND_ASSESSMENT("letter-sound-assessment-events"),
    LETTER_SOUND_LEARNING("letter-sound-learning-events"),
    STORY_BOOK_LEARNING("storybook-learning-events"),
    WORD_ASSESSMENT("word-assessment-events"),
    WORD_LEARNING("word-learning-events"),
    VIDEO_LEARNING("video-learning-events"),
    NUMBER_LEARNING("number-learning-events")
}

fun AnalyticEventType.toServiceClass(): Class<out UploadService> {
    return when (this) {
        AnalyticEventType.LETTER_SOUND_ASSESSMENT -> LetterSoundAssessmentEventService::class.java
        AnalyticEventType.LETTER_SOUND_LEARNING -> LetterSoundLearningEventService::class.java
        AnalyticEventType.STORY_BOOK_LEARNING -> StoryBookLearningEventService::class.java
        AnalyticEventType.WORD_ASSESSMENT -> WordAssessmentEventService::class.java
        AnalyticEventType.WORD_LEARNING -> WordLearningEventService::class.java
        AnalyticEventType.VIDEO_LEARNING -> VideoLearningEventService::class.java
        AnalyticEventType.NUMBER_LEARNING -> NumberLearningEventService::class.java
    }
}

fun AnalyticEventType.getUploadCsvFile(context: Context,
                                       androidId: String,
                                       versionCode: Int,
                                       date: String): File {

    val csvFileName = androidId + "_" + versionCode + "_${this.type}_" + date + ".csv"
    Timber.i("csvFilename: $csvFileName")

    val filesDir = context.filesDir
    val language = SharedPreferencesHelper.getLanguage(context)
    val languageDir = File(filesDir, "lang-${language}")
    val eventsDir = File(languageDir, this.type)
    val csvFile = File(eventsDir, csvFileName)
    return csvFile
}
