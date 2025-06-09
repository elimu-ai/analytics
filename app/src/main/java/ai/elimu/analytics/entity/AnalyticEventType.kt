package ai.elimu.analytics.entity

import ai.elimu.analytics.rest.LetterSoundAssessmentEventService
import ai.elimu.analytics.rest.LetterSoundLearningEventService
import ai.elimu.analytics.rest.StoryBookLearningEventService
import ai.elimu.analytics.rest.UploadService
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

fun AnalyticEventType.getUploadCsvFile(context: Context,
                                       androidId: String,
                                       versionCode: Int,
                                       date: String): File {

    val csvFileName = androidId + "_" + versionCode + "_${this.type}_" + date + ".csv"
    Timber.i("csvFilename: $csvFileName")

    val filesDir = context.filesDir
    val language = SharedPreferencesHelper.getLanguage(context)
    val languageDir = File(filesDir, "lang-${language}")
    val letterSoundLearningEventsDir = File(languageDir, "letter-sound-learning-events")
    val csvFile = File(letterSoundLearningEventsDir, csvFileName)
    return csvFile
}
