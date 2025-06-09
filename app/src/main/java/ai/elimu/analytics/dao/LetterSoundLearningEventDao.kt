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

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY time")
    fun loadAllOrderedByTime(): List<LetterSoundLearningEvent>

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY time")
    fun loadAllOrderedByTimeCursor(): Cursor

    @Query("SELECT COUNT(*) FROM LetterSoundLearningEvent")
    fun getCount(): Int
}
