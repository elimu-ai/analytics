package ai.elimu.analytics.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NumberAssessmentEventDao {
    @Query("SELECT COUNT(*) FROM NumberAssessmentEvent")
    fun getCount(): Int
}
