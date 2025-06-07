package ai.elimu.analytics.util

import ai.elimu.model.v2.enums.Language
import android.content.Context
import android.content.pm.PackageManager
import timber.log.Timber
import java.io.File

/**
 * Helps detect upgrades from previously installed versions of the app.
 */
object VersionHelper {
    /**
     * @return Application's version code from the `PackageManager`.
     */
    @JvmStatic
    fun getAppVersionCode(context: Context): Int {
        Timber.i("getAppVersionCode")

        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Could not get package name: $e")
        }
    }

    /**
     * Stores the version code of the application currently installed. And detects upgrades from previously installed
     * versions.
     */
    @JvmStatic
    fun updateAppVersion(context: Context) {
        Timber.i("updateAppVersion")

        // Check if the application's versionCode was upgraded
        var oldVersionCode = SharedPreferencesHelper.getAppVersionCode(context)
        val newVersionCode = getAppVersionCode(context)
        if (oldVersionCode == 0) {
            SharedPreferencesHelper.storeAppVersionCode(context, newVersionCode)
            oldVersionCode = newVersionCode
        }
        Timber.i("oldVersionCode: $oldVersionCode")
        Timber.i("newVersionCode: $newVersionCode")

        // Handle upgrade from previous version
        if (oldVersionCode < newVersionCode) {
            Timber.i("Upgrading application from version $oldVersionCode to $newVersionCode...")

            if (oldVersionCode < 3001015) {
                Timber.w("oldVersionCode < 3001015")

                // Delete CSV files stored under the old folder structure
                val filesDir = context.filesDir

                val letterAssessmentEventsDir = File(filesDir, "letter-assessment-events")
                var files = letterAssessmentEventsDir.listFiles()
                if (files != null) {
                    for (i in files.indices) {
                        Timber.w("Deleting %s", files[i])
                        Timber.w("files[i].delete(): %s", files[i].delete())
                    }
                }
                Timber.w("Deleting $letterAssessmentEventsDir")
                Timber.w("letterAssessmentEventsDir.delete(): %s", letterAssessmentEventsDir.delete())

                val letterLearningEventsDir = File(filesDir, "letter-learning-events")
                files = letterLearningEventsDir.listFiles()
                if (files != null) {
                    for (i in files.indices) {
                        Timber.w("Deleting %s", files[i])
                        Timber.w("files[i].delete(): %s", files[i].delete())
                    }
                }
                Timber.w("Deleting $letterLearningEventsDir")
                Timber.w("letterLearningEventsDir.delete(): %s", letterLearningEventsDir.delete())

                val storyBookLearningEventsDir = File(filesDir, "storybook-learning-events")
                files = storyBookLearningEventsDir.listFiles()
                if (files != null) {
                    for (i in files.indices) {
                        Timber.w("Deleting %s", files[i])
                        Timber.w("files[i].delete(): %s", files[i].delete())
                    }
                }
                Timber.w("Deleting $storyBookLearningEventsDir")
                Timber.w("storyBookLearningEventsDir.delete(): %s", storyBookLearningEventsDir.delete())

                val wordAssessmentEventsDir = File(filesDir, "word-assessment-events")
                files = wordAssessmentEventsDir.listFiles()
                if (files != null) {
                    for (i in files.indices) {
                        Timber.w("Deleting %s", files[i])
                        Timber.w("files[i].delete(): %s", files[i].delete())
                    }
                }
                Timber.w("Deleting $wordAssessmentEventsDir")
                Timber.w("wordAssessmentEventsDir.delete(): %s", wordAssessmentEventsDir.delete())

                val wordLearningEventsDir = File(filesDir, "word-learning-events")
                files = wordLearningEventsDir.listFiles()
                if (files != null) {
                    for (i in files.indices) {
                        Timber.w("Deleting %s", files[i])
                        Timber.w("files[i].delete(): %s", files[i].delete())
                    }
                }
                Timber.w("Deleting $wordLearningEventsDir")
                Timber.w("wordLearningEventsDir.delete(): %s", wordLearningEventsDir.delete())
            }

            if (oldVersionCode < 3001020) {
                Timber.w("oldVersionCode < 3001020")
                // Handle renaming from Language.FIL to Language.TGL
                val sharedPreferences = context.getSharedPreferences(
                    SharedPreferencesHelper.SHARED_PREFS,
                    Context.MODE_PRIVATE
                )
                val languageAsString =
                    sharedPreferences.getString(SharedPreferencesHelper.PREF_LANGUAGE, null)
                Timber.w("languageAsString: $languageAsString")
                if ("FIL" == languageAsString) {
                    SharedPreferencesHelper.storeLanguage(context, Language.TGL)
                }
            }

            if (oldVersionCode < 3003002) {
                Timber.w("oldVersionCode < 3003002")
                // Delete old folder structure
                val filesDir = context.filesDir
                for (file in filesDir.listFiles()) {
                    Timber.i("file.name: ${file.name}")
                    if (file.name.startsWith("version-code-")) {
                        Timber.w("file.delete(): ${file.delete()}")
                    }
                }
            }

//            if (oldVersionCode < ???) {
//                ...
//            }

            SharedPreferencesHelper.storeAppVersionCode(context, newVersionCode)
        }
    }
}
