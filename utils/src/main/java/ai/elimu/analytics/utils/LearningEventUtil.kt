package ai.elimu.analytics.utils

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.SoundGson
import ai.elimu.model.v2.gson.content.StoryBookGson
import ai.elimu.model.v2.gson.content.VideoGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.content.Intent
import android.util.Log
import org.json.JSONObject
import java.util.stream.Collectors

/**
 * A utility class that makes it easier for other apps to report learning events.
 */
object LearningEventUtil {
    /**
     * @param letterSoundGson The letter-sound correspondence that the student is learning.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_letter_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportLetterSoundLearningEvent(
        letterSoundGson: LetterSoundGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportLetterSoundLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_SOUND_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra("additionalData", additionalData.toString())
        }
        broadcastIntent.putExtra("letterSoundId", letterSoundGson.id)
        broadcastIntent.putExtra(
            "letterSoundLetterTexts",
            letterSoundGson.letters.stream().map { obj: LetterGson -> obj.text }
                .collect(Collectors.toList()).toTypedArray()
        )

        val letterSoundSoundValuesIpa = letterSoundGson.sounds.stream().map {
            obj: SoundGson -> obj.valueIpa
        }.collect(Collectors.toList()).toTypedArray()

        broadcastIntent.putExtra(
            "letterSoundSoundValuesIpa",
            letterSoundSoundValuesIpa)

        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param wordGson The word that the student is learning.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_word_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportWordLearningEvent(
        wordGson: WordGson,
        learningEventType: LearningEventType? = null,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportWordLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra("additionalData", additionalData.toString())
        }
        broadcastIntent.putExtra("wordId", wordGson.id)
        broadcastIntent.putExtra("wordText", wordGson.text)
        learningEventType?.let {
            broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param storyBookGson The storybook that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param additionalData Any additional data related to the learning event, e.g. `{'time_spent_per_chapter_ms':[7500,12900,34422,5023]}`
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportStoryBookLearningEvent(
        storyBookGson: StoryBookGson,
        learningEventType: LearningEventType? = null,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportStoryBookLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra("additionalData", additionalData.toString())
        }
        broadcastIntent.putExtra("storyBookTitle", storyBookGson.title)
        broadcastIntent.putExtra("storyBookId", storyBookGson.id)
        learningEventType?.let {
            broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param videoGson The video that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_video_paused':true}`
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportVideoLearningEvent(
        videoGson: VideoGson,
        learningEventType: LearningEventType? = null,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportVideoLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.VIDEO_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra("additionalData", additionalData.toString())
        }
        broadcastIntent.putExtra("videoId", videoGson.id)
        broadcastIntent.putExtra("videoTitle", videoGson.title)
        learningEventType?.let {
            broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }
}
