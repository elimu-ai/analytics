package ai.elimu.analytics.utils

import ai.elimu.analytics.utils.converter.CursorToLetterAssessmentEventGsonConverter.getLetterAssessmentEventGson
import ai.elimu.analytics.utils.converter.CursorToWordAssessmentEventGsonConverter
import ai.elimu.analytics.utils.converter.CursorToWordLearningEventGsonConverter
import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson
import ai.elimu.model.v2.gson.content.LetterGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast

object EventProviderUtil {
    
    private const val TAG = "EventProviderUtil"
    
    fun getLetterAssessmentEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<LetterAssessmentEventGson> {
        Log.i(TAG, "getLetterAssessmentEventGsons")

        val letterAssessmentEventGsons: MutableList<LetterAssessmentEventGson> = ArrayList()

        val letterAssessmentEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.letter_assessment_event_provider/events")
        Log.i(
            TAG,
            "letterAssessmentEventsUri: $letterAssessmentEventsUri"
        )
        val letterAssessmentEventsCursor =
            context.contentResolver.query(letterAssessmentEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "letterAssessmentEventsCursor: $letterAssessmentEventsCursor"
        )
        if (letterAssessmentEventsCursor == null) {
            Log.e(TAG, "letterAssessmentEventsCursor == null")
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG)
                .show()
        } else {
            Log.i(
                TAG,
                "letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.count
            )
            if (letterAssessmentEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "letterAssessmentEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    letterAssessmentEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val letterAssessmentEventGson =
                        getLetterAssessmentEventGson(letterAssessmentEventsCursor)

                    letterAssessmentEventGsons.add(letterAssessmentEventGson)

                    isLast = letterAssessmentEventsCursor.isLast
                }

                letterAssessmentEventsCursor.close()
                Log.i(
                    TAG,
                    "letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size
        )

        return letterAssessmentEventGsons
    }

    fun getLetterAssessmentEventGsonsByLetter(
        letterGson: LetterGson,
        context: Context,
        analyticsApplicationId: String
    ): List<LetterAssessmentEventGson> {
        Log.i(TAG, "getLetterAssessmentEventGsonsByLetter")

        val letterAssessmentEventGsons: MutableList<LetterAssessmentEventGson> = ArrayList()

        val letterAssessmentEventsUri =
            Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events/by-letter-id/" + letterGson.id)
        Log.i(
            TAG,
            "letterAssessmentEventsUri: $letterAssessmentEventsUri"
        )
        val letterAssessmentEventsCursor =
            context.contentResolver.query(letterAssessmentEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "letterAssessmentEventsCursor: $letterAssessmentEventsCursor"
        )
        if (letterAssessmentEventsCursor == null) {
            Log.e(TAG, "letterAssessmentEventsCursor == null")
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG)
                .show()
        } else {
            Log.i(
                TAG,
                "letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.count
            )
            if (letterAssessmentEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "letterAssessmentEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    letterAssessmentEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val letterAssessmentEventGson =
                        getLetterAssessmentEventGson(letterAssessmentEventsCursor)

                    letterAssessmentEventGsons.add(letterAssessmentEventGson)

                    isLast = letterAssessmentEventsCursor.isLast
                }

                letterAssessmentEventsCursor.close()
                Log.i(
                    TAG,
                    "letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size
        )

        return letterAssessmentEventGsons
    }

    fun getWordLearningEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<WordLearningEventGson> {
        Log.i(TAG, "getWordLearningEventGsons")

        val wordLearningEventGsons: MutableList<WordLearningEventGson> = ArrayList()

        val wordLearningEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_learning_event_provider/events")
        Log.i(
            TAG,
            "wordLearningEventsUri: $wordLearningEventsUri"
        )
        val wordLearningEventsCursor =
            context.contentResolver.query(wordLearningEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "wordLearningEventsCursor: $wordLearningEventsCursor"
        )
        if (wordLearningEventsCursor == null) {
            Log.e(TAG, "wordLearningEventsCursor == null")
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                TAG,
                "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.count
            )
            if (wordLearningEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "wordLearningEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    wordLearningEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val wordLearningEventGson =
                        CursorToWordLearningEventGsonConverter.getWordLearningEventGson(
                            wordLearningEventsCursor
                        )

                    wordLearningEventGsons.add(wordLearningEventGson)

                    isLast = wordLearningEventsCursor.isLast
                }

                wordLearningEventsCursor.close()
                Log.i(
                    TAG,
                    "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "wordLearningEventGsons.size(): " + wordLearningEventGsons.size
        )

        return wordLearningEventGsons
    }

    fun getIdsOfWordsInWordLearningEvents(
        context: Context,
        analyticsApplicationId: String
    ): Set<Long> {
        Log.i(TAG, "getIdsOfWordsInWordLearningEvents")

        val wordIdsSet: MutableSet<Long> = HashSet()

        val wordLearningEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_learning_event_provider/events")
        Log.i(
            TAG,
            "wordLearningEventsUri: $wordLearningEventsUri"
        )
        val wordLearningEventsCursor =
            context.contentResolver.query(wordLearningEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "wordLearningEventsCursor: $wordLearningEventsCursor"
        )
        if (wordLearningEventsCursor == null) {
            Log.e(TAG, "wordLearningEventsCursor == null")
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                TAG,
                "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.count
            )
            if (wordLearningEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "wordLearningEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    wordLearningEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val wordLearningEventGson =
                        CursorToWordLearningEventGsonConverter.getWordLearningEventGson(
                            wordLearningEventsCursor
                        )

                    wordIdsSet.add(wordLearningEventGson.wordId)

                    isLast = wordLearningEventsCursor.isLast
                }

                wordLearningEventsCursor.close()
                Log.i(
                    TAG,
                    "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed
                )
            }
        }
        Log.i(TAG, "wordIdsSet.size(): " + wordIdsSet.size)

        return wordIdsSet
    }

    fun getWordAssessmentEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<WordAssessmentEventGson> {
        Log.i(TAG, "getWordAssessmentEventGsons")

        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()

        val wordAssessmentEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_assessment_event_provider/events")
        Log.i(
            TAG,
            "wordAssessmentEventsUri: $wordAssessmentEventsUri"
        )
        val wordAssessmentEventsCursor =
            context.contentResolver.query(wordAssessmentEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "wordAssessmentEventsCursor: $wordAssessmentEventsCursor"
        )
        if (wordAssessmentEventsCursor == null) {
            Log.e(TAG, "wordAssessmentEventsCursor == null")
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                TAG,
                "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.count
            )
            if (wordAssessmentEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "wordAssessmentEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    wordAssessmentEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val wordAssessmentEventGson =
                        CursorToWordAssessmentEventGsonConverter.getWordAssessmentEventGson(
                            wordAssessmentEventsCursor
                        )

                    wordAssessmentEventGsons.add(wordAssessmentEventGson)

                    isLast = wordAssessmentEventsCursor.isLast
                }

                wordAssessmentEventsCursor.close()
                Log.i(
                    TAG,
                    "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size
        )

        return wordAssessmentEventGsons
    }

    fun getWordAssessmentEventGsonsByWord(
        wordGson: WordGson,
        context: Context,
        analyticsApplicationId: String
    ): List<WordAssessmentEventGson> {
        Log.i(TAG, "getWordAssessmentEventGsonsByWord")

        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()

        val wordAssessmentEventsUri =
            Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events/by-word-id/" + wordGson.id)
        Log.i(
            TAG,
            "wordAssessmentEventsUri: $wordAssessmentEventsUri"
        )
        val wordAssessmentEventsCursor =
            context.contentResolver.query(wordAssessmentEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "wordAssessmentEventsCursor: $wordAssessmentEventsCursor"
        )
        if (wordAssessmentEventsCursor == null) {
            Log.e(TAG, "wordAssessmentEventsCursor == null")
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                TAG,
                "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.count
            )
            if (wordAssessmentEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "wordAssessmentEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    wordAssessmentEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val wordAssessmentEventGson =
                        CursorToWordAssessmentEventGsonConverter.getWordAssessmentEventGson(
                            wordAssessmentEventsCursor
                        )

                    wordAssessmentEventGsons.add(wordAssessmentEventGson)

                    isLast = wordAssessmentEventsCursor.isLast
                }

                wordAssessmentEventsCursor.close()
                Log.i(
                    TAG,
                    "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size
        )

        return wordAssessmentEventGsons
    }
}
