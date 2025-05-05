package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.WordLearningEvent;

@Dao
public interface WordLearningEventDao {

    @Insert
    void insert(WordLearningEvent wordLearningEvent);

    @Query("SELECT * FROM WordLearningEvent ORDER BY time DESC")
    List<WordLearningEvent> loadAllOrderedByTimeDesc();

    @Query("SELECT * FROM WordLearningEvent ORDER BY time")
    Cursor loadAllOrderedByTime();
}
