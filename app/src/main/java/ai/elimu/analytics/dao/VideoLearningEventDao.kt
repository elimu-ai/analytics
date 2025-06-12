package ai.elimu.analytics.dao

import ai.elimu.analytics.entity.VideoLearningEvent
import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoLearningEventDao {
    /**
     * Inserts a VideoLearningEvent into the database, replacing any existing record with the same primary key.
     *
     * Should be called from a background thread.
     */
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoLearningEvent: VideoLearningEvent)

    /**
     * Retrieves all VideoLearningEvent records ordered by time.
     *
     * @param isDesc If true, results are ordered by time in descending order; if false, in ascending order.
     * @return A list of all VideoLearningEvent records sorted by time.
     */
    @Query(
        "SELECT * FROM VideoLearningEvent ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN time END DESC," +
                "CASE WHEN :isDesc = 0 THEN time END ASC"
    )
    fun loadAll(isDesc: Boolean = true): List<VideoLearningEvent>

    /**
     * Returns the total number of VideoLearningEvent records in the database.
     *
     * @return The count of VideoLearningEvent entities.
     */
    @Query("SELECT COUNT(*) FROM VideoLearningEvent")
    fun getCount(): Int
}
