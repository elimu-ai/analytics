package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LiteracyAssessmentEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LiteracyAssessmentEventDao {
    @Insert
    fun insert(literacyAssessmentEvent: LiteracyAssessmentEvent)

    @Query("SELECT * FROM LiteracyAssessmentEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimestampAsc(): List<LiteracyAssessmentEvent>

    @Query("SELECT * FROM LiteracyAssessmentEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT COUNT(*) FROM LiteracyAssessmentEvent")
    fun getCount(): Int
}
