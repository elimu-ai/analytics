package ai.elimu.analytics.eventtracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.model.enums.content.Shape;

public class EventTracker {

    public static void reportApplicationOpenedEvent(Context context, String packageName) {
        Log.i(EventTracker.class.getName(), "reportApplicationOpenedEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.APPLICATION_OPENED_EVENT");
        intent.putExtra("packageName", packageName);
        context.sendBroadcast(intent);
    }

    public static void reportLetterLearningEvent(Context context, String letter) {
        Log.i(EventTracker.class.getName(), "reportLetterLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.LETTER_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("letter", letter);
        context.sendBroadcast(intent);
    }

    public static void reportNumberLearningEvent(Context context, Integer number) {
        Log.i(EventTracker.class.getName(), "reportNumberLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.NUMBER_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("number", number);
        context.sendBroadcast(intent);
    }

    public static void reportVideoLearningEvent(Context context, Long videoId) {
        Log.i(EventTracker.class.getName(), "reportVideoLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.VIDEO_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("videoId", videoId);
        context.sendBroadcast(intent);
    }

    public static void reportStoryBookLearningEvent(Context context, Long storyBookId) {
        Log.i(EventTracker.class.getName(), "reportStoryBookLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.STORYBOOK_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("storyBookId", storyBookId);
        context.sendBroadcast(intent);
    }

    public static void reportShapeLearningEvent(Context context, Shape shape) {
        Log.i(EventTracker.class.getName(), "reportShapeLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("ai.elimu.analytics");
        intent.setAction("ai.elimu.intent.action.SHAPE_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("shape", shape);
        context.sendBroadcast(intent);
    }
}
