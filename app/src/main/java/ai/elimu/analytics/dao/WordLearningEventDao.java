package ai.elimu.analytics.dao;

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
    List<WordLearningEvent> loadAll();
}
