package ai.elimu.analytics.task

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.LearningEventUploadType
import ai.elimu.analytics.util.VersionHelper.getAppVersionCode
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.io.FileUtils
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Exports events from the database into CSV files, that will later be uploaded to the server by
 * the [UploadEventsWorker].
 */
class ExportEventsToCsvWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val eventDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.US) }

    override fun doWork(): Result {
        Timber.i("doWork")

        exportLetterSoundAssessmentEventsToCsv()
        exportLetterSoundLearningEventsToCsv()
        exportWordLearningEventsToCsv()
        exportWordAssessmentEventsToCsv()
        exportStoryBookLearningEventsToCsv()

        return Result.success()
    }

    private fun exportLetterSoundAssessmentEventsToCsv() {
        Timber.i("exportLetterSoundAssessmentEventsToCsv")

        // Extract LetterSoundAssessmentEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val letterSoundAssessmentEventDao = roomDb.letterSoundAssessmentEventDao()
        val letterSoundAssessmentEvents = letterSoundAssessmentEventDao.loadAll()
        Timber.i("letterSoundAssessmentEvents.size(): %s", letterSoundAssessmentEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "timestamp",
                "android_id",
                "package_name",
                "letter_sound_letters",
                "letter_sound_sounds",
                "letter_sound_id",
                "mastery_score",
                "time_spent_ms",
//                "additional_data"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (letterSoundAssessmentEvent in letterSoundAssessmentEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3002024/letter-sound-assessment-events/7161a85a0e4751cd_3002024_letter-sound-assessment-events_2025-05-29.csv
                val versionCode = getAppVersionCode(applicationContext)
                val date = eventDateFormat.format(letterSoundAssessmentEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date
                val csvFilename = letterSoundAssessmentEvent.androidId + "_" + versionCode + "_letter-sound-assessment-events_" + date + ".csv"
                Timber.i("csvFilename: ${csvFilename}")

                csvPrinter.printRecord(
                    letterSoundAssessmentEvent.id,
                    letterSoundAssessmentEvent.time.timeInMillis,
                    letterSoundAssessmentEvent.androidId,
                    letterSoundAssessmentEvent.packageName,
                    letterSoundAssessmentEvent.letterSoundLetters,
                    letterSoundAssessmentEvent.letterSoundSounds,
                    letterSoundAssessmentEvent.letterSoundId,
                    letterSoundAssessmentEvent.masteryScore,
                    letterSoundAssessmentEvent.timeSpentMs,
//                    letterSoundAssessmentEvent.additionalData
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val filesDir = applicationContext.filesDir
                val letterSoundAssessmentEventsDir = File(filesDir,
                    LearningEventUploadType.LETTER_SOUND_ASSESSMENT.type)
                val csvFile = File(letterSoundAssessmentEventsDir, csvFilename)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportLetterSoundLearningEventsToCsv() {
        Timber.i("exportLetterSoundLearningEventsToCsv")

        // Extract LetterSoundLearningEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao()
        val letterSoundLearningEvents = letterSoundLearningEventDao.loadAllOrderedByTime()
        Timber.i("letterSoundLearningEvents.size(): %s", letterSoundLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "time",
                "android_id",
                "package_name",
                "letter_sound_id",
                "letter_sound_letter_texts",
                "letter_sound_sound_values_ipa"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)


            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (letterSoundLearningEvent in letterSoundLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001017/letter-sound-learning-events/7161a85a0e4751cd_3001017_letter-sound-learning-events_2023-10-25.csv
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                val date = eventDateFormat.format(letterSoundLearningEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date
                val csvFilename =
                    letterSoundLearningEvent.androidId + "_" + versionCode + "_letter-sound-learning-events_" + date + ".csv"
                Timber.i("csvFilename: $csvFilename")

                csvPrinter.printRecord(
                    letterSoundLearningEvent.id,
                    letterSoundLearningEvent.time.timeInMillis,
                    letterSoundLearningEvent.androidId,
                    letterSoundLearningEvent.packageName,
                    letterSoundLearningEvent.id,
                    null,
                    null
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val filesDir = applicationContext.filesDir
                val letterSoundLearningEventsDir =
                    File(filesDir, LearningEventUploadType.LETTER_SOUND_LEARNING.type)
                val csvFile = File(letterSoundLearningEventsDir, csvFilename)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportWordLearningEventsToCsv() {
        Timber.i("exportWordLearningEventsToCsv")

        // Extract WordLearningEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val wordLearningEventDao = roomDb.wordLearningEventDao()
        val wordLearningEvents = wordLearningEventDao.loadAllOrderedByTimeDesc()
        Timber.i("wordLearningEvents.size(): %s", wordLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "time",
                "android_id",
                "package_name",
                "word_id",
                "word_text",
                "learning_event_type"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (wordLearningEvent in wordLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/word-learning-events/7161a85a0e4751cd_3001012_word-learning-events_2020-03-21.csv
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                val date = eventDateFormat.format(wordLearningEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date
                val csvFilename =
                    wordLearningEvent.androidId + "_" + versionCode + "_word-learning-events_" + date + ".csv"
                Timber.i("csvFilename: $csvFilename")

                csvPrinter.printRecord(
                    wordLearningEvent.id,
                    wordLearningEvent.time.timeInMillis,
                    wordLearningEvent.androidId,
                    wordLearningEvent.packageName,
                    wordLearningEvent.wordId,
                    wordLearningEvent.wordText,
                    wordLearningEvent.learningEventType
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val filesDir = applicationContext.filesDir
                val wordLearningEventsDir = File(filesDir, LearningEventUploadType.WORD_LEARNING.type)
                val csvFile = File(wordLearningEventsDir, csvFilename)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportWordAssessmentEventsToCsv() {
        Timber.i("exportWordAssessmentEventsToCsv")

        // Extract WordAssessmentEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val wordAssessmentEventDao = roomDb.wordAssessmentEventDao()
        val wordAssessmentEvents = wordAssessmentEventDao.loadAllOrderedByTimeAsc()
        Timber.i("wordAssessmentEvents.size(): %s", wordAssessmentEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "time",
                "android_id",
                "package_name",
                "word_id",
                "word_text",
                "mastery_score",
                "time_spent_ms"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (wordAssessmentEvent in wordAssessmentEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/word-assessment-events/7161a85a0e4751cd_3001012_word-assessment-events_2020-03-21.csv
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                val date = eventDateFormat.format(wordAssessmentEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date
                val csvFilename =
                    wordAssessmentEvent.androidId + "_" + versionCode + "_word-assessment-events_" + date + ".csv"
                Timber.i("csvFilename: $csvFilename")

                csvPrinter.printRecord(
                    wordAssessmentEvent.id,
                    wordAssessmentEvent.time.timeInMillis,
                    wordAssessmentEvent.androidId,
                    wordAssessmentEvent.packageName,
                    wordAssessmentEvent.wordId,
                    wordAssessmentEvent.wordText,
                    wordAssessmentEvent.masteryScore,
                    wordAssessmentEvent.timeSpentMs
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val filesDir = applicationContext.filesDir
                val wordAssessmentEventsDir = File(filesDir, LearningEventUploadType.WORD_ASSESSMENT.type)
                val csvFile = File(wordAssessmentEventsDir, csvFilename)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportStoryBookLearningEventsToCsv() {
        Timber.i("exportStoryBookLearningEventsToCsv")


        // Extract StoryBookLearningEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val storyBookLearningEventDao = roomDb.storyBookLearningEventDao()
        val storyBookLearningEvents = storyBookLearningEventDao.loadAll()
        Timber.i("storyBookLearningEvents.size(): %s", storyBookLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "time",
                "android_id",
                "package_name",
                "storybook_title",
                "storybook_id",
                "learning_event_type"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (storyBookLearningEvent in storyBookLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/storybook-learning-events/7161a85a0e4751cd_3001012_storybook-learning-events_2020-03-21.csv
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                val date = eventDateFormat.format(storyBookLearningEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date
                val csvFilename =
                    storyBookLearningEvent.androidId + "_" + versionCode + "_storybook-learning-events_" + date + ".csv"
                Timber.i("csvFilename: $csvFilename")

                csvPrinter.printRecord(
                    storyBookLearningEvent.id,
                    storyBookLearningEvent.time.timeInMillis,
                    storyBookLearningEvent.androidId,
                    storyBookLearningEvent.packageName,
                    storyBookLearningEvent.storyBookTitle,
                    storyBookLearningEvent.storyBookId,
                    storyBookLearningEvent.learningEventType
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val filesDir = applicationContext.filesDir
                val storyBookLearningEventsDir = File(filesDir, LearningEventUploadType.STORY_BOOK_LEARNING.type)
                val csvFile = File(storyBookLearningEventsDir, csvFilename)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }
}
