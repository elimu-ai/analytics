package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LetterSoundAssessmentEventDao {
    @Insert
    fun insert(letterSoundAssessmentEvent: LetterSoundAssessmentEvent)

    @Query("SELECT * FROM LetterSoundAssessmentEvent ORDER BY id")
    fun loadAll(): List<LetterSoundAssessmentEvent>
}
