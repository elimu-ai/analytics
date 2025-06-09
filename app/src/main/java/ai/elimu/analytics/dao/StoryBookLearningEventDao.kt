package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.StoryBookLearningEvent
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoryBookLearningEventDao {
    @Insert
    fun insert(storyBookLearningEvent: StoryBookLearningEvent)

    @Query("SELECT * FROM StoryBookLearningEvent ORDER BY time")
    fun loadAll(): List<StoryBookLearningEvent>

    @Query("SELECT COUNT(*) FROM StoryBookLearningEvent")
    fun getCount(): Int
}
