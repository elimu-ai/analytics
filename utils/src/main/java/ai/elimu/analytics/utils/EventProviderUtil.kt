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
    fun getLetterAssessmentEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<LetterAssessmentEventGson> {
        Log.i(EventProviderUtil::class.java.name, "getLetterAssessmentEventGsons")

        val letterAssessmentEventGsons: MutableList<LetterAssessmentEventGson> = ArrayList()

        val letterAssessmentEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.letter_assessment_event_provider/events")
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventsUri: $letterAssessmentEventsUri"
        )
        val letterAssessmentEventsCursor =
            context.contentResolver.query(letterAssessmentEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventsCursor: $letterAssessmentEventsCursor"
        )
        if (letterAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "letterAssessmentEventsCursor == null")
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG)
                .show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.count
            )
            if (letterAssessmentEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size
        )

        return letterAssessmentEventGsons
    }

    fun getLetterAssessmentEventGsonsByLetter(
        letterGson: LetterGson,
        context: Context,
        analyticsApplicationId: String
    ): List<LetterAssessmentEventGson> {
        Log.i(EventProviderUtil::class.java.name, "getLetterAssessmentEventGsonsByLetter")

        val letterAssessmentEventGsons: MutableList<LetterAssessmentEventGson> = ArrayList()

        val letterAssessmentEventsUri =
            Uri.parse("content://" + analyticsApplicationId + ".provider.letter_assessment_event_provider/events/by-letter-id/" + letterGson.id)
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventsUri: $letterAssessmentEventsUri"
        )
        val letterAssessmentEventsCursor =
            context.contentResolver.query(letterAssessmentEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventsCursor: $letterAssessmentEventsCursor"
        )
        if (letterAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "letterAssessmentEventsCursor == null")
            Toast.makeText(context, "letterAssessmentEventsCursor == null", Toast.LENGTH_LONG)
                .show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "letterAssessmentEventsCursor.getCount(): " + letterAssessmentEventsCursor.count
            )
            if (letterAssessmentEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "letterAssessmentEventsCursor.isClosed(): " + letterAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            EventProviderUtil::class.java.name,
            "letterAssessmentEventGsons.size(): " + letterAssessmentEventGsons.size
        )

        return letterAssessmentEventGsons
    }

    fun getWordLearningEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<WordLearningEventGson> {
        Log.i(EventProviderUtil::class.java.name, "getWordLearningEventGsons")

        val wordLearningEventGsons: MutableList<WordLearningEventGson> = ArrayList()

        val wordLearningEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_learning_event_provider/events")
        Log.i(
            EventProviderUtil::class.java.name,
            "wordLearningEventsUri: $wordLearningEventsUri"
        )
        val wordLearningEventsCursor =
            context.contentResolver.query(wordLearningEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "wordLearningEventsCursor: $wordLearningEventsCursor"
        )
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "wordLearningEventsCursor == null")
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.count
            )
            if (wordLearningEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed
                )
            }
        }
        Log.i(
            EventProviderUtil::class.java.name,
            "wordLearningEventGsons.size(): " + wordLearningEventGsons.size
        )

        return wordLearningEventGsons
    }

    fun getIdsOfWordsInWordLearningEvents(
        context: Context,
        analyticsApplicationId: String
    ): Set<Long> {
        Log.i(EventProviderUtil::class.java.name, "getIdsOfWordsInWordLearningEvents")

        val wordIdsSet: MutableSet<Long> = HashSet()

        val wordLearningEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_learning_event_provider/events")
        Log.i(
            EventProviderUtil::class.java.name,
            "wordLearningEventsUri: $wordLearningEventsUri"
        )
        val wordLearningEventsCursor =
            context.contentResolver.query(wordLearningEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "wordLearningEventsCursor: $wordLearningEventsCursor"
        )
        if (wordLearningEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "wordLearningEventsCursor == null")
            Toast.makeText(context, "wordLearningEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "wordLearningEventsCursor.getCount(): " + wordLearningEventsCursor.count
            )
            if (wordLearningEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "wordLearningEventsCursor.isClosed(): " + wordLearningEventsCursor.isClosed
                )
            }
        }
        Log.i(EventProviderUtil::class.java.name, "wordIdsSet.size(): " + wordIdsSet.size)

        return wordIdsSet
    }

    fun getWordAssessmentEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<WordAssessmentEventGson> {
        Log.i(EventProviderUtil::class.java.name, "getWordAssessmentEventGsons")

        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()

        val wordAssessmentEventsUri =
            Uri.parse("content://$analyticsApplicationId.provider.word_assessment_event_provider/events")
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventsUri: $wordAssessmentEventsUri"
        )
        val wordAssessmentEventsCursor =
            context.contentResolver.query(wordAssessmentEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventsCursor: $wordAssessmentEventsCursor"
        )
        if (wordAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "wordAssessmentEventsCursor == null")
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.count
            )
            if (wordAssessmentEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size
        )

        return wordAssessmentEventGsons
    }

    fun getWordAssessmentEventGsonsByWord(
        wordGson: WordGson,
        context: Context,
        analyticsApplicationId: String
    ): List<WordAssessmentEventGson> {
        Log.i(EventProviderUtil::class.java.name, "getWordAssessmentEventGsonsByWord")

        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()

        val wordAssessmentEventsUri =
            Uri.parse("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events/by-word-id/" + wordGson.id)
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventsUri: $wordAssessmentEventsUri"
        )
        val wordAssessmentEventsCursor =
            context.contentResolver.query(wordAssessmentEventsUri, null, null, null, null)
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventsCursor: $wordAssessmentEventsCursor"
        )
        if (wordAssessmentEventsCursor == null) {
            Log.e(EventProviderUtil::class.java.name, "wordAssessmentEventsCursor == null")
            Toast.makeText(context, "wordAssessmentEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                EventProviderUtil::class.java.name,
                "wordAssessmentEventsCursor.getCount(): " + wordAssessmentEventsCursor.count
            )
            if (wordAssessmentEventsCursor.count == 0) {
                Log.e(
                    EventProviderUtil::class.java.name,
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
                    EventProviderUtil::class.java.name,
                    "wordAssessmentEventsCursor.isClosed(): " + wordAssessmentEventsCursor.isClosed
                )
            }
        }
        Log.i(
            EventProviderUtil::class.java.name,
            "wordAssessmentEventGsons.size(): " + wordAssessmentEventGsons.size
        )

        return wordAssessmentEventGsons
    }
}
