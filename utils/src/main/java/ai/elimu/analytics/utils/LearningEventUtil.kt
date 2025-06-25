package ai.elimu.analytics.utils

import ai.elimu.model.v2.enums.analytics.LearningEventType
import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.NumberGson
import ai.elimu.model.v2.gson.content.SoundGson
import ai.elimu.model.v2.gson.content.StoryBookGson
import ai.elimu.model.v2.gson.content.VideoGson
import ai.elimu.model.v2.gson.content.WordGson
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra(BundleKeys.KEY_INTENT_ACTION, IntentAction.LETTER_SOUND_LEARNING.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_ID, letterSoundGson.id)
        broadcastIntent.putExtra(
            BundleKeys.KEY_LETTER_SOUND_LETTER_TEXTS,
            letterSoundGson.letters.stream().map { obj: LetterGson -> obj.text }
                .collect(Collectors.toList()).toTypedArray()
        )

        val letterSoundSoundValuesIpa = letterSoundGson.sounds.stream().map {
            obj: SoundGson -> obj.valueIpa
        }.collect(Collectors.toList()).toTypedArray()

        broadcastIntent.putExtra(
            BundleKeys.KEY_LETTER_SOUND_SOUND_VALUES_IPA,
            letterSoundSoundValuesIpa)

        broadcastIntent.setPackage(analyticsApplicationId)

        val resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(LearningEventUtil::class.simpleName, "onReceive")
                val results: Bundle = getResultExtras(true)
                val errorClassName: String? = results.getString("errorClassName")
                errorClassName?.let {
                    Log.e(LearningEventUtil::class.simpleName, "errorClassName: ${errorClassName}")
                    Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        context.sendOrderedBroadcast(broadcastIntent, null, resultReceiver, null, Activity.RESULT_OK, null, null)
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
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra(BundleKeys.KEY_INTENT_ACTION, IntentAction.WORD_LEARNING.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_ID, wordGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_TEXT, wordGson.text)
        learningEventType?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE, learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)

        val resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(LearningEventUtil::class.simpleName, "onReceive")
                val results: Bundle = getResultExtras(true)
                val errorClassName: String? = results.getString("errorClassName")
                errorClassName?.let {
                    Log.e(LearningEventUtil::class.simpleName, "errorClassName: ${errorClassName}")
                    Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        context.sendOrderedBroadcast(broadcastIntent, null, resultReceiver, null, Activity.RESULT_OK, null, null)
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
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra(BundleKeys.KEY_INTENT_ACTION, IntentAction.STORYBOOK_LEARNING.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.putExtra(BundleKeys.KEY_STORYBOOK_TITLE, storyBookGson.title)
        broadcastIntent.putExtra(BundleKeys.KEY_STORYBOOK_ID, storyBookGson.id)
        learningEventType?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE, learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)

        val resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(LearningEventUtil::class.simpleName, "onReceive")
                val results: Bundle = getResultExtras(true)
                val errorClassName: String? = results.getString("errorClassName")
                errorClassName?.let {
                    Log.e(LearningEventUtil::class.simpleName, "errorClassName: ${errorClassName}")
                    Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        context.sendOrderedBroadcast(broadcastIntent, null, resultReceiver, null, Activity.RESULT_OK, null, null)
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
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra(BundleKeys.KEY_INTENT_ACTION, IntentAction.VIDEO_LEARNING.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.putExtra(BundleKeys.KEY_VIDEO_ID, videoGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_VIDEO_TITLE, videoGson.title)
        learningEventType?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE, learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)

        val resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(LearningEventUtil::class.simpleName, "onReceive")
                val results: Bundle = getResultExtras(true)
                val errorClassName: String? = results.getString("errorClassName")
                errorClassName?.let {
                    Log.e(LearningEventUtil::class.simpleName, "errorClassName: ${errorClassName}")
                    Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        context.sendOrderedBroadcast(broadcastIntent, null, resultReceiver, null, Activity.RESULT_OK, null, null)
    }

    /**
     * @param numberGson The number that the student is learning.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application (`packageName`).
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_number_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportNumberLearningEvent(
        numberGson: NumberGson,
        learningEventType: LearningEventType? = null,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(LearningEventUtil::class.java.name, "reportNumberLearningEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra(BundleKeys.KEY_INTENT_ACTION, IntentAction.NUMBER_LEARNING.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        numberGson.id?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_ID, numberGson.id)
        }

        broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_VALUE, numberGson.value)
        broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_SYMBOL, numberGson.symbol)
        learningEventType?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_LEARNING_EVENT_TYPE, learningEventType.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)

        val resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(LearningEventUtil::class.simpleName, "onReceive")
                val results: Bundle = getResultExtras(true)
                val errorClassName: String? = results.getString("errorClassName")
                errorClassName?.let {
                    Log.e(LearningEventUtil::class.simpleName, "errorClassName: ${errorClassName}")
                    Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        context.sendOrderedBroadcast(broadcastIntent, null, resultReceiver, null, Activity.RESULT_OK, null, null)
    }
}
