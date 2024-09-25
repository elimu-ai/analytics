package ai.elimu.analytics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

import ai.elimu.model.v2.enums.Language;
import timber.log.Timber;

/**
 * Helps detect upgrades from previously installed versions of the app.
 */
public class VersionHelper {

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersionCode(Context context) {
        Timber.i("getAppVersionCode");

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Stores the version code of the application currently installed. And detects upgrades from previously installed
     * versions.
     */
    public static void updateAppVersion(Context context) {
        Timber.i("updateAppVersion");

        // Check if the application's versionCode was upgraded
        int oldVersionCode = SharedPreferencesHelper.getAppVersionCode(context);
        int newVersionCode = VersionHelper.getAppVersionCode(context);
        if (oldVersionCode == 0) {
            SharedPreferencesHelper.storeAppVersionCode(context, newVersionCode);
            oldVersionCode = newVersionCode;
        }
        Timber.i("oldVersionCode: " + oldVersionCode);
        Timber.i("newVersionCode: " + newVersionCode);

        // Handle upgrade from previous version
        if (oldVersionCode < newVersionCode) {
            Timber.i("Upgrading application from version " + oldVersionCode + " to " + newVersionCode + "...");

            if (oldVersionCode < 3001015) {
                Timber.w("oldVersionCode < 3001015");
                // Delete CSV files stored under the old folder structure

                File filesDir = context.getFilesDir();

                File letterAssessmentEventsDir = new File(filesDir, "letter-assessment-events");
                File[] files = letterAssessmentEventsDir.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        Timber.w("Deleting " + files[i]);
                        Timber.w("files[i].delete(): " + files[i].delete());
                    }
                }
                Timber.w("Deleting " + letterAssessmentEventsDir);
                Timber.w("letterAssessmentEventsDir.delete(): " + letterAssessmentEventsDir.delete());

                File letterLearningEventsDir = new File(filesDir, "letter-learning-events");
                files = letterLearningEventsDir.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        Timber.w("Deleting " + files[i]);
                        Timber.w("files[i].delete(): " + files[i].delete());
                    }
                }
                Timber.w("Deleting " + letterLearningEventsDir);
                Timber.w("letterLearningEventsDir.delete(): " + letterLearningEventsDir.delete());

                File storyBookLearningEventsDir = new File(filesDir, "storybook-learning-events");
                files = storyBookLearningEventsDir.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        Timber.w("Deleting " + files[i]);
                        Timber.w("files[i].delete(): " + files[i].delete());
                    }
                }
                Timber.w("Deleting " + storyBookLearningEventsDir);
                Timber.w("storyBookLearningEventsDir.delete(): " + storyBookLearningEventsDir.delete());

                File wordAssessmentEventsDir = new File(filesDir, "word-assessment-events");
                files = wordAssessmentEventsDir.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        Timber.w("Deleting " + files[i]);
                        Timber.w("files[i].delete(): " + files[i].delete());
                    }
                }
                Timber.w("Deleting " + wordAssessmentEventsDir);
                Timber.w("wordAssessmentEventsDir.delete(): " + wordAssessmentEventsDir.delete());

                File wordLearningEventsDir = new File(filesDir, "word-learning-events");
                files = wordLearningEventsDir.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        Timber.w("Deleting " + files[i]);
                        Timber.w("files[i].delete(): " + files[i].delete());
                    }
                }
                Timber.w("Deleting " + wordLearningEventsDir);
                Timber.w("wordLearningEventsDir.delete(): " + wordLearningEventsDir.delete());
            }

            if (oldVersionCode < 3001020) {
                Timber.w("oldVersionCode < 3001020");
                // Handle renaming from "FIL" to "TGL"
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE);
                String languageAsString = sharedPreferences.getString(SharedPreferencesHelper.PREF_LANGUAGE, null);
                Timber.w("languageAsString: " + languageAsString);
                if ("FIL".equals(languageAsString)) {
                    SharedPreferencesHelper.storeLanguage(context, Language.TGL);
                }
            }

//            if (oldVersionCode < ???) {
//                ...
//            }

            SharedPreferencesHelper.storeAppVersionCode(context, newVersionCode);
        }
    }
}
