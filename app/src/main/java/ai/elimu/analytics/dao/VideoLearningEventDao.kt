package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.VideoLearningEvent
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoLearningEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(videoLearningEvent: VideoLearningEvent)

    @Query(
        "SELECT * FROM VideoLearningEvent ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN time END DESC," +
                "CASE WHEN :isDesc = 0 THEN time END ASC"
    )
    fun loadAll(isDesc: Boolean = true): List<VideoLearningEvent>

    @Query("SELECT COUNT(*) FROM VideoLearningEvent")
    fun getCount(): Int
}
