package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.NumberLearningEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumberLearningEventDao {
    @Insert
    fun insert(numberLearningEvent: NumberLearningEvent)

    @Query("SELECT * FROM NumberLearningEvent ORDER BY " +
            "CASE WHEN :isDesc = 1 THEN timestamp END DESC," +
            "CASE WHEN :isDesc = 0 THEN timestamp END ASC"
    )
    fun loadAllOrderedByTime(isDesc: Boolean = true): List<NumberLearningEvent>

    @Query("SELECT * FROM NumberLearningEvent ORDER BY timestamp")
    fun loadAllOrderedByTime(): Cursor

    @Query("SELECT COUNT(*) FROM NumberLearningEvent")
    fun getCount(): Int
}
