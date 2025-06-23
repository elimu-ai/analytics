package ai.elimu.analytics.task

import ai.elimu.analytics.db.getAllEvents
import ai.elimu.analytics.entity.AnalyticEventType
import ai.elimu.analytics.entity.AssessmentEvent
import ai.elimu.analytics.entity.LearningEvent
import ai.elimu.analytics.entity.getCSVHeaders
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

        // Letter-sounds
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.LETTER_SOUND_ASSESSMENT)
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.LETTER_SOUND_LEARNING)

        // Words
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.WORD_ASSESSMENT)
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.WORD_LEARNING)

        // Numbers
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.NUMBER_LEARNING)

        // Storybooks
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.STORY_BOOK_LEARNING)

        // Videos
        exportAnalyticsEventsToCsv(eventType = AnalyticEventType.VIDEO_LEARNING)

        return Result.success()
    }

    private fun exportAnalyticsEventsToCsv(eventType: AnalyticEventType) {
        Timber.i("exportAnalyticsEventsToCsv")

        val events = eventType.getAllEvents(applicationContext)
        Timber.i("events.size(): %s", events.size)

        val csvFormat = CSVFormat.DEFAULT
            .withHeader(*eventType.getCSVHeaders())
        var stringWriter = StringWriter()
        try {
            var csvPrinter = CSVPrinter(stringWriter, csvFormat)

            // Generate one CSV file per day of events
            var dateOfPreviousEvent: String? = null
            for (event in events) {
                val versionCode = getAppVersionCode(
                    applicationContext
                )
                var androidId: String
                val date = if (event is AssessmentEvent) {
                    androidId = event.androidId
                    eventDateFormat.format(event.time.time)
                } else {
                    androidId = (event as LearningEvent).androidId
                    eventDateFormat.format(event.time.time)
                }
                if (date != dateOfPreviousEvent) {
                    // Reset file content
                    stringWriter = StringWriter()
                    csvPrinter = CSVPrinter(stringWriter, csvFormat)
                }
                dateOfPreviousEvent = date

                csvPrinter.printRecord(event.getCSVFields(eventType))
                csvPrinter.flush()

                val csvFileContent = stringWriter.toString()

                // Write the content to the CSV file
                val csvFile = eventType.getUploadCsvFile(
                    context = applicationContext,
                    androidId = androidId,
                    versionCode = versionCode,
                    date = date)
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }
}
