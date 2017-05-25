package org.literacyapp.analytics.eventtracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

public class EventTracker {

    public static void reportApplicationOpenedEvent(Context context, String packageName) {
        Log.i(EventTracker.class.getName(), "reportApplicationOpenedEvent");

        Intent intent = new Intent();
        intent.setPackage("org.literacyapp.analytics");
        intent.setAction("literacyapp.intent.action.APPLICATION_OPENED_EVENT");
        intent.putExtra("packageName", packageName);
        context.sendBroadcast(intent);
    }

    public static void reportLetterLearningEvent(Context context, String letter) {
        Log.i(EventTracker.class.getName(), "reportLetterLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("org.literacyapp.analytics");
        intent.setAction("literacyapp.intent.action.LETTER_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("letter", letter);
        context.sendBroadcast(intent);
    }

    public static void reportNumberLearningEvent(Context context, Integer number) {
        Log.i(EventTracker.class.getName(), "reportNumberLearningEvent");

        Intent intent = new Intent();
        intent.setPackage("org.literacyapp.analytics");
        intent.setAction("literacyapp.intent.action.NUMBER_LEARNING_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("number", number);
        context.sendBroadcast(intent);
    }

    @Deprecated
    public static void reportUsageEvent(Context context, LiteracySkill literacySkill, String letter) {
        Log.i(EventTracker.class.getName(), "reportUsageEvent");

        Intent intent = new Intent();
        intent.setPackage("org.literacyapp.analytics");
        intent.setAction("literacyapp.intent.action.USAGE_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("literacySkill", literacySkill);
        intent.putExtra("letter", letter);
        context.sendBroadcast(intent);
    }

    @Deprecated
    public static void reportUsageEvent(Context context, NumeracySkill numeracySkill, Integer number) {
        Log.i(EventTracker.class.getName(), "reportUsageEvent");

        Intent intent = new Intent();
        intent.setPackage("org.literacyapp.analytics");
        intent.setAction("literacyapp.intent.action.USAGE_EVENT");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("numeracySkill", numeracySkill);
        intent.putExtra("number", number);
        context.sendBroadcast(intent);
    }
}
