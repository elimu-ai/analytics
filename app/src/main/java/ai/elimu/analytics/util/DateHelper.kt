package ai.elimu.analytics.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateHelper {

    private val dateFormatPattern by lazy { "yyyy-MM-dd" }
    val eventDateFormat by lazy { SimpleDateFormat(dateFormatPattern, Locale.US) }

    fun isDateOlderThanSevenDays(fileName: String, currentDate: LocalDate): Boolean {
        // Step 1: Extract the date from the filename
        val datePart = fileName.substringAfterLast("_").substringBefore(".csv")

        // Step 2: Parse the date (yyyy-MM-dd)
        val formatter = DateTimeFormatter.ofPattern(dateFormatPattern)
        val fileDate = try {
            LocalDate.parse(datePart, formatter)
        } catch (e: Exception) {
            // Handle invalid date format
            Timber.w("Invalid date format in filename: $fileName")
            return false
        }

        // Step 3: Calculate the date 7 days ago from current date
        val sevenDaysAgo = currentDate.minusDays(7)

        // Step 4: Check if fileDate is older than 7 days
        return fileDate.isBefore(sevenDaysAgo)
    }
}