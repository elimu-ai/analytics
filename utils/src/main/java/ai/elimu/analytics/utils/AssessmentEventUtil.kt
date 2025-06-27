package ai.elimu.analytics.utils

import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.NumberGson
import ai.elimu.model.v2.gson.content.SoundGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.content.Intent
import android.util.Log
import org.json.JSONObject
import java.util.stream.Collectors

/**
 * A utility class that makes it easier for other apps to report assessments events to the receivers
 * in the `:app` module.
 */
object AssessmentEventUtil {

    private const val TAG = "AssessmentEventUtil"

    /**
     * @param letterSoundGson The letter-sound correspondence that the student is being assessed for.
     * @param masteryScore A value in the range [0.0, 1.0].
     * @param timeSpentMs The number of milliseconds passed between the student opening the assessment task and submitting a response. E.g. `15000`.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_letter_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportLetterSoundAssessmentEvent(
        letterSoundGson: LetterSoundGson,
        masteryScore: Float,
        timeSpentMs: Long,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(TAG,"reportLetterSoundAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction(LearningEventUtil.BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra("intent_action", IntentAction.LETTER_SOUND_ASSESSMENT.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_LETTERS,
            letterSoundGson.letters.stream().map(LetterGson::getText).collect(Collectors.joining()))
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_SOUNDS,
            letterSoundGson.sounds.stream().map(SoundGson::getValueIpa).collect(Collectors.joining()))
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_ID, letterSoundGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_MASTERY_SCORE, masteryScore)
        broadcastIntent.putExtra(BundleKeys.KEY_TIME_SPENT_MS, timeSpentMs)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param wordGson The word that the student is being assessed for.
     * @param masteryScore A value in the range [0.0, 1.0].
     * @param timeSpentMs The number of milliseconds passed between the student opening the assessment task and submitting a response. E.g. `15000`.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_word_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportWordAssessmentEvent(
        wordGson: WordGson,
        masteryScore: Float,
        timeSpentMs: Long,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(TAG, "reportWordAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction(LearningEventUtil.BROADCAST_INTENT_ACTION_ANALYTICS)
        broadcastIntent.putExtra("intent_action", IntentAction.WORD_ASSESSMENT.action)
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_ID, wordGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_TEXT, wordGson.text)
        broadcastIntent.putExtra(BundleKeys.KEY_MASTERY_SCORE, masteryScore)
        broadcastIntent.putExtra(BundleKeys.KEY_TIME_SPENT_MS, timeSpentMs)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    /**
     * @param numberGson The number that the student is being assessed for.
     * @param masteryScore A value in the range [0.0, 1.0].
     * @param timeSpentMs The number of milliseconds passed between the student opening the assessment task and submitting a response. E.g. `15000`.
     * @param additionalData Any additional data related to the learning event, e.g. `{'is_number_pressed':true}`
     * @param context Needed to fetch the `packageName` of the application where the event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    fun reportNumberAssessmentEvent(
        numberGson: NumberGson,
        masteryScore: Float,
        timeSpentMs: Long,
        additionalData: JSONObject? = null,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(TAG, "reportNumberAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setPackage(analyticsApplicationId)
        broadcastIntent.setAction("ai.elimu.intent.action.NUMBER_ASSESSMENT_EVENT")
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        broadcastIntent.putExtra(BundleKeys.KEY_MASTERY_SCORE, masteryScore)
        broadcastIntent.putExtra(BundleKeys.KEY_TIME_SPENT_MS, timeSpentMs)
        additionalData?.let {
            broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData.toString())
        }
        broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_VALUE, numberGson.value)
        broadcastIntent.putExtra(BundleKeys.KEY_NUMBER_ID, numberGson.id)

        context.sendBroadcast(broadcastIntent)
    }
}
