package ai.elimu.analytics.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ai.elimu.analytics.utils.converter.CursorToWordAssessmentEventGsonConverter;
import ai.elimu.analytics.utils.converter.CursorToWordLearningEventGsonConverter;
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson;

public class EventProviderUtil {

    public static List<WordLearningEventGson> getWordLearningEventGsons(Context context, String contentProviderApplicationId) {
        Log.i(EventProviderUtil.class.getName(), "getWordLearningEventGsons");

        List<WordLearningEventGson> wordLearningEventGsons = new ArrayList<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + contentProviderApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(EventProviderUtil.class.getName(), "wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(), "wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(), "wordLearningEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(), "wordLearningEventGsons.size(): " + wordLearningEventGsons.size());

        return wordLearningEventGsons;
    }

    public static Set<Long> getIdsOfWordsInWordLearningEvents(Context context, String contentProviderApplicationId) {
        Log.i(EventProviderUtil.class.getName(), "getIdsOfWordsInWordLearningEvents");

        Set<Long> wordIdsSet = new HashSet<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + contentProviderApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(EventProviderUtil.class.getName(), "wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(), "wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(), "wordLearningEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(), "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(), "wordIdsSet.size(): " + wordIdsSet.size());

        return wordIdsSet;
    }

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsons(Set<Long> idsOfWordsInWordLearningEvents, Context context, String contentProviderApplicationId) {
        Log.i(EventProviderUtil.class.getName(), "getWordAssessmentEventGsons");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + contentProviderApplicationId + ".provider.word_assessment_event_provider/events");
        Log.i(EventProviderUtil.class.getName(), "wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(), "wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(), "wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(), "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(), "wordAssessmentEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(), "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(), "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }
}
