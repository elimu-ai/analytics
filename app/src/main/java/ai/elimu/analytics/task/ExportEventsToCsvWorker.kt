package ai.elimu.analytics.task

import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.getUploadCsvFile
import ai.elimu.analytics.util.VersionHelper.getAppVersionCode
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.io.FileUtils
import timber.log.Timber
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
                //   files/lang-HIN/letter-sound-assessment-events/7161a85a0e4751cd_3003002_letter-sound-assessment-events_2025-06-07.csv
                val versionCode = getAppVersionCode(applicationContext)
                val date = eventDateFormat.format(letterSoundAssessmentEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date

                csvPrinter.printRecord(
                    letterSoundAssessmentEvent.id,
                    letterSoundAssessmentEvent.time.timeInMillis / 1_000,
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
                val csvFile = AnalyticEventType.LETTER_SOUND_ASSESSMENT.getUploadCsvFile(
                    context = applicationContext,
                    androidId = letterSoundAssessmentEvent.androidId,
                    versionCode = versionCode,
                    date = date)
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
                "timestamp",
                "package_name",
                "additional_data",
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
                //   files/lang-HIN/letter-sound-learning-events/7161a85a0e4751cd_3003002_letter-sound-learning-events_2025-06-07.csv
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

                csvPrinter.printRecord(
                    letterSoundLearningEvent.id,
                    letterSoundLearningEvent.time.timeInMillis / 1_000,
                    letterSoundLearningEvent.packageName,
                    letterSoundLearningEvent.additionalData,
                    letterSoundLearningEvent.id,
                    null,
                    null
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = AnalyticEventType.LETTER_SOUND_LEARNING.getUploadCsvFile(
                    context = applicationContext,
                    androidId = letterSoundLearningEvent.androidId,
                    versionCode = versionCode,
                    date = date)
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
        val wordLearningEvents = wordLearningEventDao.loadAllOrderedByTime(isDesc = false)
        Timber.i("wordLearningEvents.size(): %s", wordLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "timestamp",
                "package_name",
                "additional_data",
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
                //   files/lang-HIN/word-learning-events/7161a85a0e4751cd_3003002_word-learning-events_2025-06-07.csv
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

                csvPrinter.printRecord(
                    wordLearningEvent.id,
                    wordLearningEvent.time.timeInMillis / 1_000,
                    wordLearningEvent.packageName,
                    wordLearningEvent.additionalData,
                    wordLearningEvent.wordId,
                    wordLearningEvent.wordText,
                    wordLearningEvent.learningEventType
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = AnalyticEventType.WORD_LEARNING.getUploadCsvFile(
                    context = applicationContext,
                    androidId = wordLearningEvent.androidId,
                    versionCode = versionCode,
                    date = date)
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
                "timestamp",
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
                //   files/lang-HIN/word-assessment-events/7161a85a0e4751cd_3003002_word-assessment-events_2025-06-07.csv
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

                csvPrinter.printRecord(
                    wordAssessmentEvent.id,
                    wordAssessmentEvent.time.timeInMillis / 1_000,
                    wordAssessmentEvent.packageName,
                    wordAssessmentEvent.wordId,
                    wordAssessmentEvent.wordText,
                    wordAssessmentEvent.masteryScore,
                    wordAssessmentEvent.timeSpentMs
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = AnalyticEventType.WORD_ASSESSMENT.getUploadCsvFile(
                    context = applicationContext,
                    androidId = wordAssessmentEvent.androidId,
                    versionCode = versionCode,
                    date = date)
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
        val storyBookLearningEvents = storyBookLearningEventDao.loadAll(isDesc = false)
        Timber.i("storyBookLearningEvents.size(): %s", storyBookLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "timestamp",
                "package_name",
                "additional_data",
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
                //   files/lang-HIN/storybook-learning-events/7161a85a0e4751cd_3003002_storybook-learning-events_2025-06-07.csv
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

                csvPrinter.printRecord(
                    storyBookLearningEvent.id,
                    storyBookLearningEvent.time.timeInMillis / 1_000,
                    storyBookLearningEvent.packageName,
                    storyBookLearningEvent.additionalData,
                    storyBookLearningEvent.storyBookTitle,
                    storyBookLearningEvent.storyBookId,
                    storyBookLearningEvent.learningEventType
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = AnalyticEventType.STORY_BOOK_LEARNING.getUploadCsvFile(
                    context = applicationContext,
                    androidId = storyBookLearningEvent.androidId,
                    versionCode = versionCode,
                    date = date)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportVideoLearningEventsToCsv() {
        Timber.i("exportVideoLearningEventsToCsv")

        // Extract VideoLearningEvents from the database that have not yet been exported to CSV.
        val roomDb = RoomDb.getDatabase(applicationContext)
        val videoLearningEventDao = roomDb.videoLearningEventDao()
        val videoLearningEvents = videoLearningEventDao.loadAll(isDesc = false)
        Timber.i("videoLearningEvents.size(): %s", videoLearningEvents.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(
                "id",
                "timestamp",
                "package_name",
                "additional_data",
                "video_title",
                "video_id",
                "learning_event_type"
            )
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (videoLearningEvent in videoLearningEvents) {
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                val date = eventDateFormat.format(videoLearningEvent.time.time)
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date

                csvPrinter.printRecord(
                    videoLearningEvent.id,
                    videoLearningEvent.time.timeInMillis / 1_000,
                    videoLearningEvent.packageName,
                    videoLearningEvent.additionalData,
                    videoLearningEvent.videoTitle,
                    videoLearningEvent.videoId,
                    videoLearningEvent.learningEventType
                )
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = AnalyticEventType.VIDEO_LEARNING.getUploadCsvFile(
                    context = applicationContext,
                    androidId = videoLearningEvent.androidId,
                    versionCode = versionCode,
                    date = date)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }
}
