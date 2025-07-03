package ai.elimu.analytics.task

import ai.elimu.analytics.BuildConfig
import ai.elimu.analytics.db.RoomDb
import ai.elimu.analytics.db.getAllEvents
import ai.elimu.analytics.enum.EventType
import ai.elimu.analytics.entity.AssessmentEvent
import ai.elimu.analytics.entity.LearningEvent
import ai.elimu.analytics.enum.getCSVHeaders
import ai.elimu.analytics.enum.getUploadCsvFile
import ai.elimu.analytics.util.SharedPreferencesHelper
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

        for (eventType in EventType.entries) {
            exportAnalyticsEventsToCsv(eventType = eventType)
        }
        exportNumberAssessmentEvents()

        return Result.success()
    }

    private fun exportAnalyticsEventsToCsv(eventType: EventType) {
        Timber.i("exportAnalyticsEventsToCsv: ${eventType}")

        val events = eventType.getAllEvents(applicationContext)
        Timber.i("events.size(): %s", events.size)

        val csvFormat = CSVFormat.DEFAULT.withHeader(*eventType.getCSVHeaders())
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
                        date = date
                )
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8")
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun exportNumberAssessmentEvents() {
        Timber.i("exportNumberAssessmentEvents")

        // Read all the events from the database
        val roomDb = RoomDb.getDatabase(applicationContext)
        val numberAssessmentEventDao = roomDb.numberAssessmentEventDao()
        val events = numberAssessmentEventDao.loadAllOrderedByTimestamp(isAsc = true)
        Timber.i("events.size: ${events.size}")

        // Generate one CSV file per day of events, e.g:
        //   lang-THA/number-assessment-events/5b7c682a12ecbe2e_4000021_number-assessment-events_2025-06-29.csv
        //   lang-THA/number-assessment-events/5b7c682a12ecbe2e_4000021_number-assessment-events_2025-06-30.csv
        var stringWriter: StringWriter? = null
        var csvPrinter: CSVPrinter? = null
        var dateOfPreviousEvent: String? = null
        for (event in events) {
            // Get the event's date in ISO format, e.g. "2025-06-29"
            val date: String = eventDateFormat.format(event.time.time)

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
                    "number_id"
                ).get())
            }
            csvPrinter?.printRecord(
                event.id,
                event.time.timeInMillis / 1_000,
                event.packageName,
                event.masteryScore,
                event.timeSpentMs,
                event.additionalData,
                event.researchExperiment?.ordinal,
                event.experimentGroup?.ordinal,
                event.numberValue,
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
}
