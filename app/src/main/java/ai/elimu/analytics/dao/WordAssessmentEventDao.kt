package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.WordAssessmentEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordAssessmentEventDao {
    @Insert
    fun insert(wordAssessmentEvent: WordAssessmentEvent)

    @Query("SELECT * FROM WordAssessmentEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimeAsc(): List<WordAssessmentEvent>

    @Query("SELECT * FROM WordAssessmentEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimeDesc(): Cursor

    @Query("SELECT * FROM WordAssessmentEvent WHERE wordId = :wordId ORDER BY timestamp DESC")
    fun loadAllOrderedByTimeDesc(wordId: Long?): Cursor

    @Query("SELECT COUNT(*) FROM WordAssessmentEvent")
    fun getCount(): Int
}
