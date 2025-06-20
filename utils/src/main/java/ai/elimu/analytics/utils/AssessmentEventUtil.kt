package ai.elimu.analytics.utils

import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.SoundGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.stream.Collectors

/**
 * A utility class that makes it easier for other apps to report assessments events.
 */
object AssessmentEventUtil {

    private const val TAG = "AssessmentEventUtil"

    fun reportLetterSoundAssessmentEvent(
        letterSoundGson: LetterSoundGson,
        masteryScore: Float,
        timeSpentMs: Long,
        additionalData: String,
        context: Context,
        analyticsApplicationId: String
    ) {
        Log.i(TAG,"reportLetterSoundAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_SOUND_ASSESSMENT_EVENT")
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_LETTERS,
            letterSoundGson.letters.stream().map(LetterGson::getText).collect(Collectors.joining()))
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_SOUNDS,
            letterSoundGson.sounds.stream().map(SoundGson::getValueIpa).collect(Collectors.joining()))
        broadcastIntent.putExtra(BundleKeys.KEY_LETTER_SOUND_ID, letterSoundGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_MASTERY_SCORE, masteryScore)
        broadcastIntent.putExtra(BundleKeys.KEY_TIME_SPENT, timeSpentMs)
        broadcastIntent.putExtra(BundleKeys.KEY_ADDITIONAL_DATA, additionalData)
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }

    fun reportWordAssessmentEvent(
        wordGson: WordGson,
        masteryScore: Float?,
        timeSpentMs: Long?,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(TAG, "reportWordAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_ASSESSMENT_EVENT")
        broadcastIntent.putExtra(BundleKeys.KEY_PACKAGE_NAME, context.packageName)
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_ID, wordGson.id)
        broadcastIntent.putExtra(BundleKeys.KEY_WORD_TEXT, wordGson.text)
        broadcastIntent.putExtra(BundleKeys.KEY_MASTERY_SCORE, masteryScore)
        broadcastIntent.putExtra(BundleKeys.KEY_TIME_SPENT, timeSpentMs)
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }
}
