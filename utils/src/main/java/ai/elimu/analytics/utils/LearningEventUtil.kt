package ai.elimu.analytics.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.stream.Collectors;

import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.LetterSoundGson;
import ai.elimu.model.v2.gson.content.SoundGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.VideoGson;
import ai.elimu.model.v2.gson.content.WordGson;

/**
 * A utility class that makes it easier for other apps to report learning events.
 */
public class LearningEventUtil {

    /**
     * @param letterSoundGson The letter-sound correspondence that the student is learning.
     * @param context Needed to fetch the {@code packageName} of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    public static void reportLetterSoundLearningEvent(LetterSoundGson letterSoundGson, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(),"reportLetterSoundLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.LETTER_SOUND_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("letterSoundId", letterSoundGson.getId());
        broadcastIntent.putExtra("letterSoundLetterTexts", letterSoundGson.getLetters().stream().map(LetterGson::getText).toArray());
        broadcastIntent.putExtra("letterSoundSoundValuesIpa", letterSoundGson.getSounds().stream().map(SoundGson::getValueIpa).toArray());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    /**
     * @param wordGson The word that the student is learning.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application ({@code packageName}).
     * @param context Needed to fetch the {@code packageName} of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    public static void reportWordLearningEvent(WordGson wordGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(),"reportWordLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.WORD_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("wordId", wordGson.getId());
        broadcastIntent.putExtra("wordText", wordGson.getText());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    /**
     * @param storyBookGson The storybook that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application ({@code packageName}).
     * @param context Needed to fetch the {@code packageName} of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    public static void reportStoryBookLearningEvent(StoryBookGson storyBookGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(),"reportStoryBookLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("storyBookId", storyBookGson.getId());
        broadcastIntent.putExtra("storyBookTitle", storyBookGson.getTitle());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }

    /**
     * @param videoGson The video that the student is learning from.
     * @param learningEventType The type of learning (i.e. the learning format) that is presented to the student in the application ({@code packageName}).
     * @param context Needed to fetch the {@code packageName} of the application where the learning event occurred.
     * @param analyticsApplicationId The package name of the analytics application that will receive the Intent and store the event.
     */
    public static void reportVideoLearningEvent(VideoGson videoGson, LearningEventType learningEventType, Context context, String analyticsApplicationId) {
        Log.i(LearningEventUtil.class.getName(),"reportVideoLearningEvent");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ai.elimu.intent.action.VIDEO_LEARNING_EVENT");
        broadcastIntent.putExtra("packageName", context.getPackageName());
        broadcastIntent.putExtra("videoId", videoGson.getId());
        broadcastIntent.putExtra("videoTitle", videoGson.getTitle());
        broadcastIntent.putExtra("learningEventType", learningEventType.toString());
        broadcastIntent.setPackage(analyticsApplicationId);
        context.sendBroadcast(broadcastIntent);
    }
}
