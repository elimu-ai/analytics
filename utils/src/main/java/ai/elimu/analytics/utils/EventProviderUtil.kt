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

import ai.elimu.analytics.utils.converter.CursorToLetterAssessmentEventGsonConverter;
import ai.elimu.analytics.utils.converter.CursorToWordAssessmentEventGsonConverter;
import ai.elimu.analytics.utils.converter.CursorToWordLearningEventGsonConverter;
import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.WordGson;

public class EventProviderUtil {

    public static List<LetterAssessmentEventGson> getLetterAssessmentEventGsons(Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getLetterAssessmentEventGsons");

        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();

        Uri letterAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events");
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsUri: " + letterAssessmentEventsUri);
        Cursor letterAssessmentEventsCursor = context.getContentResolver().query(letterAssessmentEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor: " + letterAssessmentEventsCursor);
        if (letterAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor == null");
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.getCount());
            if (letterAssessmentEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.getCount() == 0");
            } else {
                boolean isLast = false;
                while (!isLast) {
                    letterAssessmentEventsCursor.moveToNext();

                    // Convert from Room to Gson
                    LetterAssessmentEventGson letterAssessmentEventGson = CursorToLetterAssessmentEventGsonConverter.getLetterAssessmentEventGson(letterAssessmentEventsCursor);

                    letterAssessmentEventGsons.add(letterAssessmentEventGson);

                    isLast = letterAssessmentEventsCursor.isLast();
                }

                letterAssessmentEventsCursor.close();
                Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size());

        return letterAssessmentEventGsons;
    }

    public static List<LetterAssessmentEventGson> getLetterAssessmentEventGsonsByLetter(LetterGson letterGson, Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getLetterAssessmentEventGsonsByLetter");

        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();

        Uri letterAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events/by-letter-id/" + letterGson.getId());
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsUri: " + letterAssessmentEventsUri);
        Cursor letterAssessmentEventsCursor = context.getContentResolver().query(letterAssessmentEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor: " + letterAssessmentEventsCursor);
        if (letterAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor == null");
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.getCount());
            if (letterAssessmentEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.getCount() == 0");
            } else {
                boolean isLast = false;
                while (!isLast) {
                    letterAssessmentEventsCursor.moveToNext();

                    // Convert from Room to Gson
                    LetterAssessmentEventGson letterAssessmentEventGson = CursorToLetterAssessmentEventGsonConverter.getLetterAssessmentEventGson(letterAssessmentEventsCursor);

                    letterAssessmentEventGsons.add(letterAssessmentEventGson);

                    isLast = letterAssessmentEventsCursor.isLast();
                }

                letterAssessmentEventsCursor.close();
                Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size());

        return letterAssessmentEventGsons;
    }

    public static List<WordLearningEventGson> getWordLearningEventGsons(Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getWordLearningEventGsons");

        List<WordLearningEventGson> wordLearningEventGsons = new ArrayList<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(EventProviderUtil.class.getName(),"wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"wordLearningEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"wordLearningEventGsons.size(): " + wordLearningEventGsons.size());

        return wordLearningEventGsons;
    }

    public static Set<Long> getIdsOfWordsInWordLearningEvents(Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getIdsOfWordsInWordLearningEvents");

        Set<Long> wordIdsSet = new HashSet<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Log.i(EventProviderUtil.class.getName(),"wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"wordLearningEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(),"wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"wordIdsSet.size(): " + wordIdsSet.size());

        return wordIdsSet;
    }

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsons(Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getWordAssessmentEventGsons");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events");
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsonsByWord(WordGson wordGson, Context context, String analyticsApplicationId) {
        Log.i(EventProviderUtil.class.getName(),"getWordAssessmentEventGsonsByWord");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events/by-word-id/" + wordGson.getId());
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Log.e(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.getCount() == 0");
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
                Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Log.i(EventProviderUtil.class.getName(),"wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }
}
