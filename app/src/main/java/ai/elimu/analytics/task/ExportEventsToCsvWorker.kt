package ai.elimu.analytics.task

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.util.SharedPreferencesHelper
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.io.FileUtils
import timber.log.Timber
import java.io.File
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Exports events from the database into CSV files, that will later be uploaded to the server by
 * the [UploadEventsWorker].
 *
 * Folder structure:
 * ```
 * ├── lang-ENG
 * │    ├── letter-sound-learning-events
 * │    │    ├── e387e38700000001_4001000_letter-sound-learning-events_2025-07-27.csv
 * │    │    ├── e387e38700000001_4001000_letter-sound-learning-events_2025-07-27.csv
 * │    │    └── e387e38700000001_4001000_letter-sound-learning-events_2025-07-27.csv
 * │    ├── word-learning-events
 * │    │    ├── e387e38700000002_4000018_word-learning-events_2025-07-27.csv
 * │    │    ├── e387e38700000002_4000018_word-learning-events_2025-07-27.csv
 * │    │    ├── e387e38700000002_4000018_word-learning-events_2025-07-27.csv
 * ```
 */
class ExportEventsToCsvWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val eventDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.US) }

    override fun doWork(): Result {
        Timber.i("doWork")

        exportLetterSoundLearningEvents()
        exportLetterSoundAssessmentEvents()

        exportWordLearningEvents()
        exportWordAssessmentEvents()

        exportNumberLearningEvents();
        exportNumberAssessmentEvents()

        exportStoryBookLearningEvents()

        exportVideoLearningEvents()

        return Result.success()
    }

    private fun exportLetterSoundLearningEvents() {
        Timber.i("exportLetterSoundLearningEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao()
        val events = letterSoundLearningEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/letter-sound-learning-events/5b7c682a12ecbe2e_4001000_letter-sound-learning-events_2025-07-27.csv
        //   lang-THA/letter-sound-learning-events/5b7c682a12ecbe2e_4001000_letter-sound-learning-events_2025-07-27.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-07-27"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "letter-sound-learning-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_letter-sound-learning-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "letter_sound_letters",
                    "letter_sound_sounds",
                    "letter_sound_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.letterSoundLetters,
                event.letterSoundSounds,
                event.letterSoundId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportLetterSoundLearningEvents complete!")
    }

    private fun exportLetterSoundAssessmentEvents() {
        Timber.i("exportLetterSoundAssessmentEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val letterSoundAssessmentEventDao = roomDb.letterSoundAssessmentEventDao()
        val events = letterSoundAssessmentEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/letter-sound-assessment-events/5b7c682a12ecbe2e_4001000_letter-sound-assessment-events_2025-07-27.csv
        //   lang-THA/letter-sound-assessment-events/5b7c682a12ecbe2e_4001000_letter-sound-assessment-events_2025-07-27.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-07-27"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "letter-sound-assessment-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_letter-sound-assessment-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "mastery_score",
                    "time_spent_ms",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "letter_sound_letters",
                    "letter_sound_sounds",
                    "letter_sound_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.masteryScore,
                event.timeSpentMs,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.letterSoundLetters,
                event.letterSoundSounds,
                event.letterSoundId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportLetterSoundAssessmentEvents complete!")
    }

    
    private fun exportWordLearningEvents() {
        Timber.i("exportWordLearningEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val wordLearningEventDao = roomDb.wordLearningEventDao()
        val events = wordLearningEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/word-learning-events/5b7c682a12ecbe2e_4000021_word-learning-events_2025-06-29.csv
        //   lang-THA/word-learning-events/5b7c682a12ecbe2e_4000021_word-learning-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "word-learning-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_word-learning-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "word_text",
                    "word_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.wordText,
                event.wordId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportWordLearningEvents complete!")
    }
    
    private fun exportWordAssessmentEvents() {
        Timber.i("exportWordAssessmentEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
        val events = wordAssessmentEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/word-assessment-events/5b7c682a12ecbe2e_4000021_word-assessment-events_2025-06-29.csv
        //   lang-THA/word-assessment-events/5b7c682a12ecbe2e_4000021_word-assessment-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "word-assessment-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_word-assessment-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "mastery_score",
                    "time_spent_ms",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "word_text",
                    "word_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.masteryScore,
                event.timeSpentMs,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.wordText,
                event.wordId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportWordAssessmentEvents complete!")
    }


    private fun exportNumberLearningEvents() {
        Timber.i("exportNumberLearningEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val numberLearningEventDao = roomDb.numberLearningEventDao()
        val events = numberLearningEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/number-learning-events/5b7c682a12ecbe2e_4000021_number-learning-events_2025-06-29.csv
        //   lang-THA/number-learning-events/5b7c682a12ecbe2e_4000021_number-learning-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "number-learning-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_number-learning-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "number_value",
                    "number_symbol",
                    "number_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.numberValue,
                event.numberSymbol,
                event.numberId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportNumberLearningEvents complete!")
    }

    private fun exportNumberAssessmentEvents() {
        Timber.i("exportNumberAssessmentEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val numberAssessmentEventDao = roomDb.numberAssessmentEventDao()
        val events = numberAssessmentEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/number-assessment-events/5b7c682a12ecbe2e_4000021_number-assessment-events_2025-06-29.csv
        //   lang-THA/number-assessment-events/5b7c682a12ecbe2e_4000021_number-assessment-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "number-assessment-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_number-assessment-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "mastery_score",
                    "time_spent_ms",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "number_value",
                    "number_symbol",
                    "number_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.masteryScore,
                event.timeSpentMs,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.numberValue,
                event.numberSymbol,
                event.numberId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportNumberAssessmentEvents complete!")
    }


    private fun exportStoryBookLearningEvents() {
        Timber.i("exportStoryBookLearningEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
        val events = storyBookLearningEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/storybook-learning-events/5b7c682a12ecbe2e_4000021_storybook-learning-events_2025-06-29.csv
        //   lang-THA/storybook-learning-events/5b7c682a12ecbe2e_4000021_storybook-learning-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "storybook-learning-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_storybook-learning-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "storybook_title",
                    "storybook_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.storyBookTitle,
                event.storyBookId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportStoryBookLearningEvents complete!")
    }


    private fun exportVideoLearningEvents() {
        Timber.i("exportVideoLearningEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val videoLearningEventDao = roomDb.videoLearningEventDao()
        val events = videoLearningEventDao.loadAllOrderedByTimestampAsc()
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/video-learning-events/5b7c682a12ecbe2e_4000021_video-learning-events_2025-06-29.csv
        //   lang-THA/video-learning-events/5b7c682a12ecbe2e_4000021_video-learning-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.timestamp.time)

            // Prepare the CSV file path
            val languageDir = File(applicationContext.filesDir, "lang-${SharedPreferencesHelper.getLanguage(applicationContext)}")
            val eventsDir = File(languageDir, "video-learning-events")
            val csvFile = File(eventsDir, "${event.androidId}_${BuildConfig.VERSION_CODE}_video-learning-events_${date}.csv")

            if (date != dateOfPreviousEvent) {
                // Reset file content, and prepare the headers for a new CSV file
                Timber.i("csvFile: ${csvFile}")
                stringWriter = StringWriter()
                csvPrinter = CSVPrinter(stringWriter, CSVFormat.DEFAULT.builder().setHeader(
                    "id",
                    "timestamp",
                    "package_name",
                    "additional_data",
                    "research_experiment",
                    "experiment_group",
                    "video_title",
                    "video_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.timestamp.timeInMillis / 1_000,
                event.packageName,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.videoTitle,
                event.videoId
            )
            csvPrinter?.flush()

            // Write the content to the CSV file
            val csvFileContent = stringWriter.toString()
            FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")

            dateOfPreviousEvent = date
        }

        Timber.i("exportVideoLearningEvents complete!")
    }
}
