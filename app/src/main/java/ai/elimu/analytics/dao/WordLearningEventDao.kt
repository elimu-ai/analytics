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

    @Query("SELECT * FROM WordLearningEvent ORDER BY time DESC")
    fun loadAllOrderedByTimeDesc(): List<WordLearningEvent>

    @Query("SELECT * FROM WordLearningEvent ORDER BY time")
    fun loadAllOrderedByTime(): Cursor

    @Query("SELECT COUNT(*) FROM WordLearningEvent")
    fun getCount(): Int
}
