package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LiteracyLearningEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LiteracyLearningEventDao {
    @Insert
    fun insert(literacyLearningEvent: LiteracyLearningEvent)

    @Query("SELECT * FROM LiteracyLearningEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimestampAsc(): List<LiteracyLearningEvent>

    @Query("SELECT * FROM LiteracyLearningEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT COUNT(*) FROM LiteracyLearningEvent")
    fun getCount(): Int
}
