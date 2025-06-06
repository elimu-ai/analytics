package ai.elimu.analytics.util

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale


class DateHelperTest {

    private val currentDate: LocalDate = LocalDate.of(2025, 6, 6) // June 6, 2025, 3:48 PM HKT

    // Tests for isDateOlderThanSevenDays
    @Test
    fun testIsDateOlderThanSevenDays_dateExactlySevenDaysAgo_returnsFalse() {
        val fileName = "event_2025-05-30.csv" // 7 days before June 6, 2025
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Date exactly 7 days ago should not be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateMoreThanSevenDaysAgo_returnsTrue() {
        val fileName = "event_2025-05-29.csv" // 8 days before June 6, 2025
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertTrue("Date more than 7 days ago should be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateLessThanSevenDaysAgo_returnsFalse() {
        val fileName = "event_2025-06-01.csv" // 5 days before June 6, 2025
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Date less than 7 days ago should not be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateEqualsCurrentDate_returnsFalse() {
        val fileName = "event_2025-06-06.csv" // Same as current date
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Date equal to current date should not be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateInFuture_returnsFalse() {
        val fileName = "event_2025-06-07.csv" // 1 day after current date
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Future date should not be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_invalidDateFormat_returnsFalse() {
        val fileName = "event_2025-13-06.csv" // Invalid month
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Invalid date format should return false", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_emptyFilename_returnsFalse() {
        val fileName = ""
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Empty filename should return false", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_noDateInFilename_returnsFalse() {
        val fileName = "event_nodate.csv"
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Filename without date should return false", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_noCsvExtension_returnsFalse() {
        val fileName = "event_2025-06-06.txt"
        val result = DateHelper.isDateOlderThanSevenDays(fileName, currentDate)
        assertFalse("Filename without .csv should return false", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateAroundLeapYear_returnsCorrectResult() {
        val leapYearDate = LocalDate.of(2024, 3, 1) // March 1, 2024 (leap year)
        val fileName = "event_2024-02-22.csv" // 8 days before March 1, 2024
        val result = DateHelper.isDateOlderThanSevenDays(fileName, leapYearDate)
        println("testIsDateOlderThanSevenDays_dateAroundLeapYear_returnsCorrectResult: $result")
        assertTrue("Date 8 days before leap year date should be considered older", result)
    }

    @Test
    fun testIsDateOlderThanSevenDays_dateAcrossYearBoundary_returnsCorrectResult() {
        val yearBoundaryDate = LocalDate.of(2025, 1, 1) // Jan 1, 2025
        val fileName = "event_2024-12-24.csv" // 8 days before Jan 1, 2025
        val result = DateHelper.isDateOlderThanSevenDays(fileName, yearBoundaryDate)
        assertTrue("Date 8 days before year boundary should be considered older", result)
    }

    // Tests for eventDateFormat
    @Test
    fun testEventDateFormat_hasCorrectPattern() {
        val expectedPattern = "yyyy-MM-dd"
        val simpleDateFormat = DateHelper.eventDateFormat
        assertEquals("eventDateFormat should use yyyy-MM-dd pattern", expectedPattern, simpleDateFormat.toPattern() )
    }

    @Test
    fun testEventDateFormat_formatsDateCorrectly() {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse("2025-06-06")!!
        val formatted = DateHelper.eventDateFormat.format(date)
        assertEquals("eventDateFormat should format date correctly", "2025-06-06", formatted)
    }

    @Test
    fun testEventDateFormat_parsesDateCorrectly() {
        val dateString = "2025-06-06"
        val parsedDate = DateHelper.eventDateFormat.parse(dateString)
        val expectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateString)
        assertEquals("eventDateFormat should parse date correctly", expectedDate, parsedDate)
    }

    @Test
    fun testEventDateFormat_handlesInvalidDateFormat() {
        val invalidDateString = "2025-13-06" // Invalid month
        val parsedDate = try {
            DateHelper.eventDateFormat.parse(invalidDateString)
            null
        } catch (e: Exception) {
            null
        }
        assertEquals("eventDateFormat should throw exception for invalid date", parsedDate, null)
    }
}