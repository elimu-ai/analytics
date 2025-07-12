package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LetterSoundLearningEvent
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LetterSoundLearningEventDao {
    @Insert
    fun insert(letterSoundLearningEvent: LetterSoundLearningEvent)

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY timestamp ASC")
    fun loadAllOrderedByTimestampAsc(): List<LetterSoundLearningEvent>

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY timestamp DESC")
    fun loadAllOrderedByTimestampDesc(): Cursor

    @Query("SELECT COUNT(*) FROM LetterSoundLearningEvent")
    fun getCount(): Int
}
