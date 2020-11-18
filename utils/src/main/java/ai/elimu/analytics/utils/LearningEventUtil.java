package ai.elimu.analytics.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ai.elimu.analytics.utils.converter.CursorToWordLearningEventGsonConverter;
import ai.elimu.model.enums.analytics.LearningEventType;
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.WordGson;

public class LearningEventUtil {

    public static void reportLetterLearningEvent(LetterGson letterGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(), "reportLetterLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("letterId", letterGson.getId());
        broadcastIntent.putExtra("letterText", letterGson.getText());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    public static void reportWordLearningEvent(WordGson wordGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(), "reportWordLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("wordId", wordGson.getId());
        broadcastIntent.putExtra("wordText", wordGson.getText());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    public static void reportStoryBookLearningEvent(StoryBookGson storyBookGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(), "reportStoryBookLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("storyBookId", storyBookGson.getId());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    public static List<WordLearningEventGson> getWordLearningEventGsons(Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(), "getWordLearningEventGsons");

        List<WordLearningEventGson> wordLearningEventGsons = new ArrayList<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(LearningEventUtil.class.getName(), "wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(LearningEventUtil.class.getName(), "wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(LearningEventUtil.class.getName(), "wordLearningEventsCursor.getCount() == 0");
            } else {
                boolean isLast = false;
                while (!isLast) {
                    wordLearningEventsCursor.moveToNext();

                    // Convert from Room to Gson
                    WordLearningEventGson wordLearningEventGson = CursorToWordLearningEventGsonConverter.getWordLearningEventGson(wordLearningEventsCursor);

                    wordLearningEventGsons.add(wordLearningEventGson);

                    isLast = wordLearningEventsCursor.isLast();
                }

                wordLearningEventsCursor.close();
                Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(LearningEventUtil.class.getName(), "wordLearningEventGsons.size(): " + wordLearningEventGsons.size());

        return wordLearningEventGsons;
    }

    public static Set<Long> getIdsOfWordsInWordLearningEvents(Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(), "getIdsOfWordsInWordLearningEvents");

        Set<Long> wordIdsSet = new HashSet<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(LearningEventUtil.class.getName(), "wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(LearningEventUtil.class.getName(), "wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(LearningEventUtil.class.getName(), "wordLearningEventsCursor.getCount() == 0");
            } else {
                boolean isLast = false;
                while (!isLast) {
                    wordLearningEventsCursor.moveToNext();

                    // Convert from Room to Gson
                    WordLearningEventGson wordLearningEventGson = CursorToWordLearningEventGsonConverter.getWordLearningEventGson(wordLearningEventsCursor);

                    wordIdsSet.add(wordLearningEventGson.getWordId());

                    isLast = wordLearningEventsCursor.isLast();
                }

                wordLearningEventsCursor.close();
                Log.i(LearningEventUtil.class.getName(), "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(LearningEventUtil.class.getName(), "wordIdsSet.size(): " + wordIdsSet.size());

        return wordIdsSet;
    }
}
