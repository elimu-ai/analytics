package ai.elimu.analytics.enum

import ai.elimu.analytics.entity.BaseEntity
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import ai.elimu.analytics.entity.LetterSoundLearningEvent
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.entity.StoryBookLearningEvent
import ai.elimu.analytics.entity.VideoLearningEvent
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.entity.WordLearningEvent
import ai.elimu.analytics.rest.LetterSoundAssessmentEventService
import ai.elimu.analytics.rest.LetterSoundLearningEventService
import ai.elimu.analytics.rest.NumberLearningEventService
import ai.elimu.analytics.rest.StoryBookLearningEventService
import ai.elimu.analytics.rest.UploadService
import ai.elimu.analytics.rest.VideoLearningEventService
import ai.elimu.analytics.rest.WordAssessmentEventService
import ai.elimu.analytics.rest.WordLearningEventService
import ai.elimu.analytics.task.CSVHeaders
import ai.elimu.analytics.util.SharedPreferencesHelper
import ai.elimu.analytics.utils.BundleKeys
import ai.elimu.analytics.utils.research.ExperimentAssignmentHelper
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber
import java.io.File
import java.util.Calendar

enum class EventType(val type: String) {
    LETTER_SOUND_ASSESSMENT("letter-sound-assessment-events"),
    LETTER_SOUND_LEARNING("letter-sound-learning-events"),

    WORD_ASSESSMENT("word-assessment-events"),
    WORD_LEARNING("word-learning-events"),

    NUMBER_LEARNING("number-learning-events"),

    STORY_BOOK_LEARNING("storybook-learning-events"),

    VIDEO_LEARNING("video-learning-events")
}

fun EventType.toServiceClass(): Class<out UploadService> {
    return when (this) {
        EventType.LETTER_SOUND_ASSESSMENT -> LetterSoundAssessmentEventService::class.java
        EventType.LETTER_SOUND_LEARNING -> LetterSoundLearningEventService::class.java

        EventType.WORD_ASSESSMENT -> WordAssessmentEventService::class.java
        EventType.WORD_LEARNING -> WordLearningEventService::class.java

        EventType.NUMBER_LEARNING -> NumberLearningEventService::class.java

        EventType.STORY_BOOK_LEARNING -> StoryBookLearningEventService::class.java

        EventType.VIDEO_LEARNING -> VideoLearningEventService::class.java
    }
}

fun EventType.getUploadCsvFile(context: Context,
                               androidId: String,
                               versionCode: Int,
                               date: String): File {

    val csvFileName = androidId + "_" + versionCode + "_${this.type}_" + date + ".csv"

    val filesDir = context.filesDir
    val language = SharedPreferencesHelper.getLanguage(context)
    val languageDir = File(filesDir, "lang-${language}")
    val eventsDir = File(languageDir, this.type)
    val csvFile = File(eventsDir, csvFileName)
    return csvFile
}

fun EventType.getCSVHeaders(): Array<String> {
    return when (this) {
        EventType.LETTER_SOUND_ASSESSMENT -> CSVHeaders.LETTER_SOUND_ASSESSMENT
        EventType.LETTER_SOUND_LEARNING -> CSVHeaders.LETTER_SOUND_LEARNING

        EventType.WORD_ASSESSMENT -> CSVHeaders.WORD_ASSESSMENT
        EventType.WORD_LEARNING -> CSVHeaders.WORD_LEARNING

        EventType.NUMBER_LEARNING -> CSVHeaders.NUMBER_LEARNING

        EventType.STORY_BOOK_LEARNING -> CSVHeaders.STORYBOOK_LEARNING

        EventType.VIDEO_LEARNING -> CSVHeaders.VIDEO_LEARNING
    }
}

fun EventType.createEventFromIntent(context: Context, intent: Intent): BaseEntity {
    Timber.i("createEventFromIntent")

    val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    Timber.i("androidId: \"${androidId}\"")

    val packageName: String = intent.getStringExtra(BundleKeys.KEY_PACKAGE_NAME) ?: ""
    Timber.i("packageName: \"${packageName}\"")

    val timestamp: Calendar = Calendar.getInstance()
    Timber.i("timestamp.time: ${timestamp.time}")

    val additionalData: String? = intent.getStringExtra(BundleKeys.KEY_ADDITIONAL_DATA)
    Timber.i("additionalData: \"${additionalData}\"")

    val researchExperiment = ExperimentAssignmentHelper.CURRENT_EXPERIMENT
    val experimentGroup = ExperimentAssignmentHelper.getExperimentGroup(context)
    Timber.i("researchExperiment: ${researchExperiment} (${experimentGroup})")

    return when (this) {
        EventType.LETTER_SOUND_ASSESSMENT -> {
            val masteryScore: Float = intent.getFloatExtra(BundleKeys.KEY_MASTERY_SCORE, 0f)
            Timber.i("masteryScore: ${masteryScore}")

            val timeSpentMs: Long = intent.getLongExtra(BundleKeys.KEY_TIME_SPENT_MS, 0)
            Timber.i("timeSpentMs: ${timeSpentMs}")

            val letterSoundLetters: String = intent.getStringExtra(BundleKeys.KEY_LETTER_SOUND_LETTERS) ?: ""
            Timber.i("letterSoundLetters: \"${letterSoundLetters}\"")

            val letterSoundSounds: String = intent.getStringExtra(BundleKeys.KEY_LETTER_SOUND_SOUNDS) ?: ""
            Timber.i("letterSoundSounds: \"${letterSoundSounds}\"")

            val letterSoundId: Long = intent.getLongExtra(BundleKeys.KEY_LETTER_SOUND_ID, 0)
            Timber.i("letterSoundId: ${letterSoundId}")

            LetterSoundAssessmentEvent().apply {
                this.androidId = androidId
                this.packageName = packageName
                this.time = timestamp
                this.masteryScore = masteryScore
                this.timeSpentMs = timeSpentMs
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.letterSoundLetters = letterSoundLetters
                this.letterSoundSounds = letterSoundSounds
                this.letterSoundId = letterSoundId
            }
        }

        EventType.LETTER_SOUND_LEARNING -> {
            val letterSoundLetters = intent.getStringArrayExtra(BundleKeys.KEY_LETTER_SOUND_LETTER_TEXTS) ?: emptyArray()
            Timber.i("letterSoundLetters: $letterSoundLetters")

            val letterSoundSounds = intent.getStringArrayExtra(BundleKeys.KEY_LETTER_SOUND_SOUND_VALUES_IPA) ?: emptyArray()
            Timber.i("letterSoundSounds: $letterSoundSounds")

            var letterSoundId: Long? = null
            if (intent.hasExtra(BundleKeys.KEY_LETTER_SOUND_ID)) {
                letterSoundId = intent.getLongExtra(BundleKeys.KEY_LETTER_SOUND_ID, 0)
            }
            Timber.i("letterSoundId: $letterSoundId")

            LetterSoundLearningEvent().apply {
                this.androidId = androidId
                this.packageName = packageName
                this.timestamp = timestamp
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.letterSoundLetterTexts = letterSoundLetters
                this.letterSoundSoundValuesIpa = letterSoundSounds
                this.letterSoundId = letterSoundId
            }
        }

        EventType.WORD_ASSESSMENT -> {
            val masteryScore = intent.getFloatExtra(BundleKeys.KEY_MASTERY_SCORE, 0f)
            Timber.i("masteryScore: $masteryScore")

            val timeSpentMs = intent.getLongExtra(BundleKeys.KEY_TIME_SPENT_MS, 0)
            Timber.i("timeSpentMs: $timeSpentMs")

            val wordText = intent.getStringExtra(BundleKeys.KEY_WORD_TEXT) ?: ""
            Timber.i("wordText: \"$wordText\"")

            var wordId: Long? = null
            if (intent.hasExtra(BundleKeys.KEY_WORD_ID)) {
                wordId = intent.getLongExtra(BundleKeys.KEY_WORD_ID, 0)
            }
            Timber.i("wordId: $wordId")

            WordAssessmentEvent().apply {
                this.androidId = androidId
                this.packageName = packageName
                this.time = timestamp
                this.masteryScore = masteryScore
                this.timeSpentMs = timeSpentMs
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.wordText = wordText
                this.wordId = wordId
            }
        }

        EventType.WORD_LEARNING -> {
            val wordText = intent.getStringExtra(BundleKeys.KEY_WORD_TEXT) ?: ""
            Timber.i("wordText: \"$wordText\"")

            var wordId: Long? = null
            if (intent.hasExtra(BundleKeys.KEY_WORD_ID)) {
                wordId = intent.getLongExtra(BundleKeys.KEY_WORD_ID, 0)
            }
            Timber.i("wordId: $wordId")

            WordLearningEvent().apply {
                this.androidId = androidId
                this.packageName = packageName
                this.timestamp = timestamp
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.wordText = wordText
                this.wordId = wordId
            }
        }

        EventType.NUMBER_LEARNING -> {
            val numberValue = intent.getIntExtra(BundleKeys.KEY_NUMBER_VALUE, 0)
            Timber.i("numberValue: \"$numberValue\"")

            val numberSymbol = intent.getStringExtra(BundleKeys.KEY_NUMBER_SYMBOL)
            Timber.i("numberSymbol: \"$numberSymbol\"")

            val numberId = if (intent.hasExtra(BundleKeys.KEY_NUMBER_ID)) {
                intent.getLongExtra(BundleKeys.KEY_NUMBER_ID, 0)
            } else null

            Timber.i("numberId: $numberId. hasKey NUMBER_ID: ${intent.hasExtra(BundleKeys.KEY_NUMBER_ID)}")

            NumberLearningEvent().apply {
                this.timestamp = timestamp
                this.androidId = androidId
                this.packageName = packageName
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.numberValue = numberValue
                this.numberSymbol = numberSymbol
                this.numberId = numberId
            }
        }

        EventType.STORY_BOOK_LEARNING -> {
            val storyBookTitle: String = intent.getStringExtra(BundleKeys.KEY_STORYBOOK_TITLE)
                ?: throw IllegalArgumentException("storyBookTitle must be provided")
            Timber.i("storyBookTitle: \"$storyBookTitle\"")

            val storyBookId = intent.getLongExtra(BundleKeys.KEY_STORYBOOK_ID, 0)
            Timber.i("storyBookId: $storyBookId")
            if (storyBookId > 0) {
                StoryBookLearningEvent().apply {
                    this.storyBookId = storyBookId
                }
            }

            StoryBookLearningEvent().apply {
                this.androidId = androidId
                this.packageName = packageName
                this.timestamp = timestamp
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.storyBookTitle = storyBookTitle
            }
        }

        EventType.VIDEO_LEARNING -> {
            val videoTitle = intent.getStringExtra(BundleKeys.KEY_VIDEO_TITLE) ?: ""
            Timber.i("videoTitle: \"$videoTitle\"")

            val videoId = intent.getLongExtra(BundleKeys.KEY_VIDEO_ID, 0)
            Timber.i("videoId: $videoId")

            VideoLearningEvent().apply {
                this.timestamp = timestamp
                this.androidId = androidId
                this.packageName = packageName
                this.additionalData = additionalData
                this.researchExperiment = researchExperiment
                this.experimentGroup = experimentGroup
                this.videoTitle = videoTitle
                this.videoId = videoId
            }
        }
    }
}