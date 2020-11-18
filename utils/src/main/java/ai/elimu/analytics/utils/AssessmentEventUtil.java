package ai.elimu.analytics.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ai.elimu.model.enums.analytics.LearningEventType;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.WordGson;

public class AssessmentEventUtil {

    public static void reportLetterAssessmentEvent(LetterGson letterGson, Float masteryScore, Long timeSpentMs, Context context, String analyticsApplicationId) {
        Log.i(LearningEventType.class.getName(), "reportLetterAssessmentEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_ASSESSMENT_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("letterId", letterGson.getId());
        broadcastIntent.putExtra("letterText", letterGson.getText());
        broadcastIntent.putExtra("masteryScore", masteryScore);
        broadcastIntent.putExtra("timeSpentMs", timeSpentMs);
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    public static void reportWordAssessmentEvent(WordGson wordGson, Float masteryScore, Long timeSpentMs, Context context, String analyticsApplicationId) {
        Log.i(LearningEventType.class.getName(), "reportWordAssessmentEvent");

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
