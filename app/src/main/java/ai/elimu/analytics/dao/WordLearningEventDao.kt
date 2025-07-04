package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.WordLearningEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordLearningEventDao {
    @Insert
    fun insert(wordLearningEvent: WordLearningEvent)

    @Query("SELECT * FROM WordLearningEvent ORDER BY " +
            "CASE WHEN :isDesc = 1 THEN timestamp END DESC," +
            "CASE WHEN :isDesc = 0 THEN timestamp END ASC"
    )
    fun loadAllOrderedByTime(isDesc: Boolean = true): List<WordLearningEvent>

    @Query("SELECT * FROM WordLearningEvent ORDER BY timestamp")
    fun loadAllOrderedByTime(): Cursor

    @Query("SELECT COUNT(*) FROM WordLearningEvent")
    fun getCount(): Int
}
