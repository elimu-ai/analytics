package ai.elimu.analytics.utils;

import android.content.Context;
import android.content.Intent;

import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.WordGson;
import timber.log.Timber;

/**
 * A utility class that makes it easier for other apps to report assessments events.
 */
public class AssessmentEventUtil {

    public static void reportLetterAssessmentEvent(LetterGson letterGson, Float masteryScore, Long timeSpentMs, Context context, String analyticsApplicationId) {
        Timber.i("reportLetterAssessmentEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_ASSESSMENT_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("letterId", letterGson.getId());
        broadcastIntent.putExtra("letterText", letterGson.getText());
        broadcastIntent.putExtra("masteryScore", masteryScore);
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs);
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    public static void reportWordAssessmentEvent(WordGson wordGson, Float masteryScore, Long timeSpentMs, Context context, String analyticsApplicationId) {
        Timber.i("reportWordAssessmentEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_ASSESSMENT_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("wordId", wordGson.getId());
        broadcastIntent.putExtra("wordText", wordGson.getText());
        broadcastIntent.putExtra("masteryScore", masteryScore);
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs);
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }
}
