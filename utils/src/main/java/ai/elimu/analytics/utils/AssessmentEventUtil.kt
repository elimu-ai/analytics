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
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("letterSoundLetters", letterSoundGson.letters.stream().map(LetterGson::getText).collect(Collectors.joining()))
        broadcastIntent.putExtra("letterSoundSounds", letterSoundGson.sounds.stream().map(SoundGson::getValueIpa).collect(Collectors.joining()))
        broadcastIntent.putExtra("letterSoundId", letterSoundGson.id)
        broadcastIntent.putExtra("masteryScore", masteryScore)
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs)
        broadcastIntent.putExtra("additionalData", additionalData)
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
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("wordId", wordGson.id)
        broadcastIntent.putExtra("wordText", wordGson.text)
        broadcastIntent.putExtra("masteryScore", masteryScore)
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs)
        broadcastIntent.setPackage(analyticsApplicationId)
        context.sendBroadcast(broadcastIntent)
    }
}
