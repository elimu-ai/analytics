package ai.elimu.analytics.utils

import ai.elimu.analytics.utils.converter.CursorToLetterSoundAssessmentEventGsonConverter
import ai.elimu.analytics.utils.converter.CursorToVideoLearningEventGsonConverter
import ai.elimu.analytics.utils.converter.CursorToWordAssessmentEventGsonConverter
import ai.elimu.analytics.utils.converter.CursorToWordLearningEventGsonConverter
import ai.elimu.model.v2.gson.analytics.LetterSoundAssessmentEventGson
import ai.elimu.model.v2.gson.analytics.VideoLearningEventGson
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import ai.elimu.model.v2.gson.analytics.WordLearningEventGson
import ai.elimu.model.v2.gson.content.WordGson
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri

object EventProviderUtil {
    
    private const val TAG = "EventProviderUtil"

    fun getLetterSoundAssessmentEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<LetterSoundAssessmentEventGson> {
        Log.i(TAG, "getLetterSoundAssessmentEventGsons")

        val letterSoundAssessmentEventGsons: MutableList<LetterSoundAssessmentEventGson> = ArrayList()

        val letterSoundAssessmentEventsUri = "content://${analyticsApplicationId}.provider.letter_sound_assessment_event_provider/events".toUri()
        Log.i(TAG, "letterSoundAssessmentEventsUri: ${letterSoundAssessmentEventsUri}")
        val letterSoundAssessmentEventsCursor = context.contentResolver.query(letterSoundAssessmentEventsUri, null, null, null, null)
        Log.i(TAG, "letterSoundAssessmentEventsCursor: ${letterSoundAssessmentEventsCursor}")
        if (letterSoundAssessmentEventsCursor == null) {
            Log.e(TAG, "letterSoundAssessmentEventsCursor == null")
            Toast.makeText(context, "letterSoundAssessmentEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(TAG, "letterSoundAssessmentEventsCursor.count: " + letterSoundAssessmentEventsCursor.count)
            if (letterSoundAssessmentEventsCursor.count == 0) {
                Log.e(TAG, "letterSoundAssessmentEventsCursor.count == 0")
            } else {
                var isLast: Boolean = false
                while (!isLast) {
                    letterSoundAssessmentEventsCursor.moveToNext()

                    // Convert from Room to Gson
                    val letterSoundAssessmentEventGson = CursorToLetterSoundAssessmentEventGsonConverter.getLetterSoundAssessmentEventGson(letterSoundAssessmentEventsCursor)

                    letterSoundAssessmentEventGsons.add(letterSoundAssessmentEventGson)

                    isLast = letterSoundAssessmentEventsCursor.isLast
                }

                letterSoundAssessmentEventsCursor.close()
                Log.i(TAG, "letterSoundAssessmentEventsCursor.isClosed: " + letterSoundAssessmentEventsCursor.isClosed)
            }
        }
        Log.i(TAG, "letterSoundAssessmentEventGsons.size: " + letterSoundAssessmentEventGsons.size)

        return letterSoundAssessmentEventGsons
    }

    fun getWordLearningEventGsons(
        context: Context,
        analyticsApplicationId: String
    ): List<WordLearningEventGson> {
        Log.i(TAG, "getWordLearningEventGsons")

        val wordLearningEventGsons: MutableList<WordLearningEventGson> = ArrayList()

        val wordLearningEventsUri =
            "content://$analyticsApplicationId.provider.word_learning_event_provider/events".toUri()
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
            "content://$analyticsApplicationId.provider.word_learning_event_provider/events".toUri()
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
            "content://$analyticsApplicationId.provider.word_assessment_event_provider/events".toUri()
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
            ("content://" + analyticsApplicationId + ".provider.word_assessment_event_provider/events/by-word-id/" + wordGson.id).toUri()
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

    fun getVideoLearningEventGSONs(
        context: Context,
        analyticsApplicationId: String
    ): List<VideoLearningEventGson> {
        Log.i(TAG, "getVideoLearningEventGSONs")

        val videoLearningEventGSONs: MutableList<VideoLearningEventGson> = ArrayList()

        val videoLearningEventsUri =
            "content://$analyticsApplicationId.provider.video_learning_event_provider/events".toUri()
        Log.i(
            TAG,
            "videoLearningEventsUri: $videoLearningEventsUri"
        )
        val videoLearningEventsCursor =
            context.contentResolver.query(videoLearningEventsUri, null, null, null, null)
        Log.i(
            TAG,
            "videoLearningEventsCursor: $videoLearningEventsCursor"
        )
        if (videoLearningEventsCursor == null) {
            Log.e(TAG, "videoLearningEventsCursor == null")
            Toast.makeText(context, "videoLearningEventsCursor == null", Toast.LENGTH_LONG).show()
        } else {
            Log.i(
                TAG,
                "videoLearningEventsCursor.getCount(): " + videoLearningEventsCursor.count
            )
            if (videoLearningEventsCursor.count == 0) {
                Log.e(
                    TAG,
                    "videoLearningEventsCursor.getCount() == 0"
                )
            } else {
                var isLast = false
                while (!isLast) {
                    videoLearningEventsCursor.moveToNext()

                    // Convert from Room to GSON
                    val videoLearningEventGSON =
                        CursorToVideoLearningEventGsonConverter.getVideoLearningEventGSON(
                            videoLearningEventsCursor
                        )

                    videoLearningEventGSONs.add(videoLearningEventGSON)

                    isLast = videoLearningEventsCursor.isLast
                }

                videoLearningEventsCursor.close()
                Log.i(
                    TAG,
                    "videoLearningEventsCursor.isClosed(): " + videoLearningEventsCursor.isClosed
                )
            }
        }
        Log.i(
            TAG,
            "videoLearningEventGSONs.size(): " + videoLearningEventGSONs.size
        )

        return videoLearningEventGSONs
    }
}
