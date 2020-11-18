package ai.elimu.analytics.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ai.elimu.analytics.utils.converter.CursorToWordAssessmentEventGsonConverter;
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.WordGson;

public class AssessmentEventUtil {

    public static void reportLetterAssessmentEvent(LetterGson letterGson, Float masteryScore, Long timeSpentMs, Context context, String analyticsApplicationId) {
        Log.i(AssessmentEventUtil.class.getName(), "reportLetterAssessmentEvent");

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
        Log.i(AssessmentEventUtil.class.getName(), "reportWordAssessmentEvent");

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

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsons(Set<Long> idsOfWordsInWordLearningEvents, Context context, String analyticsApplicationId) {
        Log.i(AssessmentEventUtil.class.getName(), "getWordAssessmentEventGsons");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events");
        Log.i(AssessmentEventUtil.class.getName(), "wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Log.i(AssessmentEventUtil.class.getName(), "wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Log.e(AssessmentEventUtil.class.getName(), "wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(AssessmentEventUtil.class.getName(), "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Log.e(AssessmentEventUtil.class.getName(), "wordAssessmentEventsCursor.getCount() == 0");
            } else {
                boolean isLast = false;
                while (!isLast) {
                    wordAssessmentEventsCursor.moveToNext();

                    // Convert from Room to Gson
                    WordAssessmentEventGson wordAssessmentEventGson = CursorToWordAssessmentEventGsonConverter.getWordAssessmentEventGson(wordAssessmentEventsCursor);

                    wordAssessmentEventGsons.add(wordAssessmentEventGson);

                    isLast = wordAssessmentEventsCursor.isLast();
                }

                wordAssessmentEventsCursor.close();
                Log.i(AssessmentEventUtil.class.getName(), "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(AssessmentEventUtil.class.getName(), "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }
}
