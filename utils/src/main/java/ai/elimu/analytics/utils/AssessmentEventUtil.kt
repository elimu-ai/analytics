package ai.elimu.analytics.utils

import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * A utility class that makes it easier for other apps to report assessments events.
 */
object AssessmentEventUtil {

    private const val TAG = "AssessmentEventUtil"

    fun reportLetterAssessmentEvent(
        letterGson: LetterGson,
        masteryScore: Float?,
        timeSpentMs: Long?,
        context: Context,
        analyticsApplicationId: String?
    ) {
        Log.i(TAG, "reportLetterAssessmentEvent")

        val broadcastIntent = Intent()
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_ASSESSMENT_EVENT")
        broadcastIntent.putExtra("packageName", context.packageName)
        broadcastIntent.putExtra("letterId", letterGson.id)
        broadcastIntent.putExtra("letterText", letterGson.text)
        broadcastIntent.putExtra("masteryScore", masteryScore)
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs)
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
