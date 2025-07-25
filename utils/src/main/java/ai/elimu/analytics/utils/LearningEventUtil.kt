package ai.elimu.analytics.utils

import ai.elimu.analytics.utils.receiver.ErrorResultReceiver
import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.NumberGson
import ai.elimu.model.v2.gson.content.SoundGson
import ai.elimu.model.v2.gson.content.StoryBookGson
import ai.elimu.model.v2.gson.content.VideoGson
import ai.elimu.model.v2.gson.content.WordGson
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import org.json.JSONObject
import java.util.stream.Collectors

/**
 * A utility class that makes it easier for other apps to report learning events to the receivers
 * in the `:app` module.
 */
object LearningEventUtil {

    const val BROADCAST_INTENT_ACTION_ANALYTICS = "ai.elimu.intent.action.ANALYTICS_EVENT"

    /**
     * @param letterSoundGson The letter-sound correspondence that the student is learning.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_letter_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportLetterSoundLearningEvent(
        letterSoundGson: LetterSoundGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(this::class.simpleName, "reportLetterSoundLearningEvent")

        try {
            val broadcastIntent = Intent()
            broadcastIntent.setPackage(analyticsApplicationId)
            broadcastIntent.setAction("ai.elimu.intent.action.LETTER_SOUND_LEARNING_EVENT")
            broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
            additionalData?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
            }

            broadcastIntent.putExtra(
                BundleKeys.KEY_LETTER_SOUND_LETTER_TEXTS,
                letterSoundGson.letters.stream().map { obj: LetterGson -> obj.text }
                    .collect(Collectors.toList()).toTypedArray()
            )

            val letterSoundSoundValuesIpa = letterSoundGson.sounds.stream().map {
                obj: SoundGson -> obj.valueIpa
            }.collect(Collectors.toList()).toTypedArray()
            broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_SOUND_VALUES_IPA, letterSoundSoundValuesIpa)

            letterSoundGson.id?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_ID, letterSoundGson.id)
            }

            context.sendOrderedBroadcast(broadcastIntent, null, ErrorResultReceiver(), null, Activity.RESULT_OK, null, null)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error during Intent preparation", e)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @param wordGson The word that the student is learning.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_word_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportWordLearningEvent(
        wordGson: WordGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(this::class.simpleName, "reportWordLearningEvent")

        try {
            val broadcastIntent = Intent()
            broadcastIntent.setPackage(analyticsApplicationId)
            broadcastIntent.setAction("ai.elimu.intent.action.WORD_LEARNING_EVENT")
            broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
            additionalData?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
            }
            broadcastIntent.putExtra(BundleKeys.KEY_WORD_TEXT, wordGson.text)
            wordGson.id?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_WORD_ID, wordGson.id)
            }

            context.sendOrderedBroadcast(broadcastIntent, null, ErrorResultReceiver(), null, Activity.RESULT_OK, null, null)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error during Intent preparation", e)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @param storyBookGson The storybook that the student is learning from.
     * @param additionalData Any additional data related to the learning event, e.g. `{'time_spent_per_chapter_ms':[7500,12900,34422,5023]}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportStoryBookLearningEvent(
        storyBookGson: StoryBookGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(this::class.simpleName, "reportStoryBookLearningEvent")

        try {
            val broadcastIntent = Intent()
            broadcastIntent.setPackage(analyticsApplicationId)
            broadcastIntent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT")
            broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
            additionalData?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
            }
            broadcastIntent.putExtra(BundleKeys.KEY_STORYBOOK_TITLE, storyBookGson.title)
            storyBookGson.id?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_STORYBOOK_ID, storyBookGson.id)
            }

            context.sendOrderedBroadcast(broadcastIntent, null, ErrorResultReceiver(), null, Activity.RESULT_OK, null, null)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error during Intent preparation", e)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @param videoGson The video that the student is learning from.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_video_paused':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportVideoLearningEvent(
        videoGson: VideoGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(this::class.simpleName, "reportVideoLearningEvent")

        try {
            val broadcastIntent = Intent()
            broadcastIntent.setPackage(analyticsApplicationId)
            broadcastIntent.setAction("ai.elimu.intent.action.VIDEO_LEARNING_EVENT")
            broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
            additionalData?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
            }
            broadcastIntent.putExtra(BundleKeys.KEY_VIDEO_TITLE, videoGson.title)
            videoGson.id?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_VIDEO_ID, videoGson.id)
            }

            context.sendOrderedBroadcast(broadcastIntent, null, ErrorResultReceiver(), null, Activity.RESULT_OK, null, null)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error during Intent preparation", e)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @param numberGson The number that the student is learning.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_number_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportNumberLearningEvent(
        numberGson: NumberGson,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(this::class.simpleName, "reportNumberLearningEvent")

        try {
            val broadcastIntent = Intent()
            broadcastIntent.setPackage(analyticsApplicationId)
            broadcastIntent.setAction("ai.elimu.intent.action.NUMBER_LEARNING_EVENT")
            broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
            additionalData?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
            }
            broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_VALUE, numberGson.value)
            numberGson.symbol?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_SYMBOL, numberGson.symbol)
            }
            numberGson.id?.let {
                broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_ID, numberGson.id)
            }

            context.sendOrderedBroadcast(broadcastIntent, null, ErrorResultReceiver(), null, Activity.RESULT_OK, null, null)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error during Intent preparation", e)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
