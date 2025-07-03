package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.NumberAssessmentEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumberAssessmentEventDao {
    @Insert
    fun insert(numberAssessmentEvent: NumberAssessmentEvent)

    @Query("SELECT * FROM NumberAssessmentEvent ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN time END ASC," +
            "CASE WHEN :isAsc = 0 THEN time END DESC"
    )
    fun loadAllOrderedByTimestamp(isAsc: Boolean): List<NumberAssessmentEvent>

    @Query("SELECT * FROM NumberAssessmentEvent ORDER BY " +
            "CASE WHEN :isDesc = 1 THEN time END DESC," +
            "CASE WHEN :isDesc = 0 THEN time END ASC"
    )
    fun loadAllOrderedByTimestampCursor(isDesc: Boolean): Cursor

    @Query("SELECT COUNT(*) FROM NumberAssessmentEvent")
    fun getCount(): Int
}
