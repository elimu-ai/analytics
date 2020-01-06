package ai.elimu.analytics;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StoryBookLearningEventDao {

    @Insert
    void insert(StoryBookLearningEvent storyBookLearningEvent);

    @Query("SELECT * FROM StoryBookLearningEvent")
    List<StoryBookLearningEvent> loadAll();
}
