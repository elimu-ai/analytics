package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface LetterSoundAssessmentEventDao {
    @Insert
    fun insert(letterSoundAssessmentEvent: LetterSoundAssessmentEvent)
}
