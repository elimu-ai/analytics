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

    @Query("SELECT * FROM NumberAssessmentEvent ORDER BY time ASC")
    fun loadAllOrderedByTimestampAsc(): List<NumberAssessmentEvent>

    @Query("SELECT * FROM NumberAssessmentEvent ORDER BY time DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT COUNT(*) FROM NumberAssessmentEvent")
    fun getCount(): Int
}
