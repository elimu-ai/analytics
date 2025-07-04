package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.StoryBookLearningEvent
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoryBookLearningEventDao {
    @Insert
    fun insert(storyBookLearningEvent: StoryBookLearningEvent)

    @Query("SELECT * FROM StoryBookLearningEvent ORDER BY " +
            "CASE WHEN :isDesc = 1 THEN timestamp END DESC," +
            "CASE WHEN :isDesc = 0 THEN timestamp END ASC"
    )
    fun loadAll(isDesc: Boolean = true): List<StoryBookLearningEvent>

    @Query("SELECT COUNT(*) FROM StoryBookLearningEvent")
    fun getCount(): Int
}
