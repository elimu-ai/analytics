package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LetterSoundAssessmentEventDao {
    @Insert
    fun insert(letterSoundAssessmentEvent: LetterSoundAssessmentEvent)

    @Query("SELECT * FROM LetterSoundAssessmentEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimestampAsc(): List<LetterSoundAssessmentEvent>

    @Query("SELECT * FROM LetterSoundAssessmentEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT * FROM LetterSoundAssessmentEvent WHERE letterSoundId = :letterSoundId ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(letterSoundId: Long): Cursor

    @Query("SELECT COUNT(*) FROM LetterSoundAssessmentEvent")
    fun getCount(): Int
}
