package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.NumberAssessmentEvent
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumberAssessmentEventDao {
    @Insert
    fun insert(numberAssessmentEvent: NumberAssessmentEvent)

    @Query("SELECT COUNT(*) FROM NumberAssessmentEvent")
    fun getCount(): Int
}
