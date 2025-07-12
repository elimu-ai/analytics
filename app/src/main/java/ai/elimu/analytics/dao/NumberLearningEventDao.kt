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

    @Query("SELECT * FROM NumberLearningEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimestampAsc(): List<NumberLearningEvent>

    @Query("SELECT * FROM NumberLearningEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT COUNT(*) FROM NumberLearningEvent")
    fun getCount(): Int
}
