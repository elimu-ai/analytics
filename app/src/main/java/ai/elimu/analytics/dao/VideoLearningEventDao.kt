package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.VideoLearningEvent
import android.database.Cursor
import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoLearningEventDao {
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoLearningEvent: VideoLearningEvent)

    @Query(
        "SELECT * FROM VideoLearningEvent ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN timestamp END DESC," +
                "CASE WHEN :isDesc = 0 THEN timestamp END ASC"
    )
    fun loadAll(isDesc: Boolean = true): List<VideoLearningEvent>

    @Query("SELECT * FROM VideoLearningEvent ORDER BY timestamp")
    fun loadAllToCursor(): Cursor

    @Query("SELECT COUNT(*) FROM VideoLearningEvent")
    fun getCount(): Int
}
