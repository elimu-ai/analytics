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

/**
 * A utility class that makes it easier for other apps to report learning events.
 */
object LearningEventUtil {
    /**
     * @param letterSoundGson The letter-sound correspondence that the student is learning.
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportLetterSoundLearningEvent(
        letterSoundGson: LetterSoundGson,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportLetterSoundLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_SOUND_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("letterSoundId", letterSoundGson.id)
        broadcastIntent.putExtra(
            "letterSoundLetterTexts",
            letterSoundGson.letters.stream().map { obj: LetterGson -> obj.text }.toArray()
        )
        broadcastIntent.putExtra(
            "letterSoundSoundValuesIpa",
            letterSoundGson.sounds.stream().map { obj: SoundGson -> obj.valueIpa }.toArray()
        )
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param wordGson The word that the student is learning.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportWordLearningEvent(
        wordGson: WordGson,
        learningEventType: LearningEventType,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportWordLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("wordId", wordGson.id)
        broadcastIntent.putExtra("wordText", wordGson.text)
        broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param storyBookGson The storybook that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportStoryBookLearningEvent(
        storyBookGson: StoryBookGson,
        learningEventType: LearningEventType,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportStoryBookLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("storyBookId", storyBookGson.id)
        broadcastIntent.putExtra("storyBookTitle", storyBookGson.title)
        broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param videoGson The video that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportVideoLearningEvent(
        videoGson: VideoGson,
        learningEventType: LearningEventType,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportVideoLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.VIDEO_LEARNING_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("videoId", videoGson.id)
        broadcastIntent.putExtra("videoTitle", videoGson.title)
        broadcastIntent.putExtra("learningEventType", learningEventType.toString())
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }
}
