package ai.elimu.analytics.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
        Timber.i("getLetterAssessmentEventGsons");

        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();

        Uri letterAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events");
        Timber.i("letterAssessmentEventsUri: " + letterAssessmentEventsUri);
        Cursor letterAssessmentEventsCursor = context.getContentResolver().query(letterAssessmentEventsUri, null, null, null, null);
        Timber.i("letterAssessmentEventsCursor: " + letterAssessmentEventsCursor);
        if (letterAssessmentEventsCursor == null) {
            Timber.e("letterAssessmentEventsCursor == null");
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.getCount());
            if (letterAssessmentEventsCursor.getCount() == 0) {
                Timber.e("letterAssessmentEventsCursor.getCount() == 0");
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
                Timber.i("letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed());
            }
        }
        Timber.i("letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size());

        return letterAssessmentEventGsons;
    }

    public static List<LetterAssessmentEventGson> getLetterAssessmentEventGsonsByLetter(LetterGson letterGson, Context context, String analyticsApplicationId) {
        Timber.i("getLetterAssessmentEventGsonsByLetter");

        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();

        Uri letterAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events/by-letter-id/" + letterGson.getId());
        Timber.i("letterAssessmentEventsUri: " + letterAssessmentEventsUri);
        Cursor letterAssessmentEventsCursor = context.getContentResolver().query(letterAssessmentEventsUri, null, null, null, null);
        Timber.i("letterAssessmentEventsCursor: " + letterAssessmentEventsCursor);
        if (letterAssessmentEventsCursor == null) {
            Timber.e("letterAssessmentEventsCursor == null");
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.getCount());
            if (letterAssessmentEventsCursor.getCount() == 0) {
                Timber.e("letterAssessmentEventsCursor.getCount() == 0");
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
                Timber.i("letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed());
            }
        }
        Timber.i("letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size());

        return letterAssessmentEventGsons;
    }

    public static List<WordLearningEventGson> getWordLearningEventGsons(Context context, String analyticsApplicationId) {
        Timber.i("getWordLearningEventGsons");

        List<WordLearningEventGson> wordLearningEventGsons = new ArrayList<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Timber.i("wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Timber.i("wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Timber.e("wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Timber.e("wordLearningEventsCursor.getCount() == 0");
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
                Timber.i("wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Timber.i("wordLearningEventGsons.size(): " + wordLearningEventGsons.size());

        return wordLearningEventGsons;
    }

    public static Set<Long> getIdsOfWordsInWordLearningEvents(Context context, String analyticsApplicationId) {
        Timber.i("getIdsOfWordsInWordLearningEvents");

        Set<Long> wordIdsSet = new HashSet<>();

        Uri wordLearningEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_learning_event_provider/events");
        Timber.i("wordLearningEventsUri: " + wordLearningEventsUri);
        Cursor wordLearningEventsCursor = context.getContentResolver().query(wordLearningEventsUri, null, null, null, null);
        Timber.i("wordLearningEventsCursor: " + wordLearningEventsCursor);
        if (wordLearningEventsCursor == null) {
            Timber.e("wordLearningEventsCursor == null");
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.getCount());
            if (wordLearningEventsCursor.getCount() == 0) {
                Timber.e("wordLearningEventsCursor.getCount() == 0");
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
                Timber.i("wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed());
            }
        }
        Timber.i("wordIdsSet.size(): " + wordIdsSet.size());

        return wordIdsSet;
    }

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsons(Context context, String analyticsApplicationId) {
        Timber.i("getWordAssessmentEventGsons");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events");
        Timber.i("wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Timber.i("wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Timber.e("wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Timber.e("wordAssessmentEventsCursor.getCount() == 0");
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
                Timber.i("wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Timber.i("wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }

    public static List<WordAssessmentEventGson> getWordAssessmentEventGsonsByWord(WordGson wordGson, Context context, String analyticsApplicationId) {
        Timber.i("getWordAssessmentEventGsonsByWord");

        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();

        Uri wordAssessmentEventsUri = Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events/by-word-id/" + wordGson.getId());
        Timber.i("wordAssessmentEventsUri: " + wordAssessmentEventsUri);
        Cursor wordAssessmentEventsCursor = context.getContentResolver().query(wordAssessmentEventsUri, null, null, null, null);
        Timber.i("wordAssessmentEventsCursor: " + wordAssessmentEventsCursor);
        if (wordAssessmentEventsCursor == null) {
            Timber.e("wordAssessmentEventsCursor == null");
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show();
        } else {
            Timber.i("wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.getCount());
            if (wordAssessmentEventsCursor.getCount() == 0) {
                Timber.e("wordAssessmentEventsCursor.getCount() == 0");
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
                Timber.i("wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed());
            }
        }
        Timber.i("wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size());

        return wordAssessmentEventGsons;
    }
}
