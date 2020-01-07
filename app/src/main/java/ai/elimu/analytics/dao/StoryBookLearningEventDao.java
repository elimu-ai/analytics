package ai.elimu.analytics.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.StoryBookLearningEvent;

@Dao
public interface StoryBookLearningEventDao {

    @Insert
    void insert(StoryBookLearningEvent storyBookLearningEvent);

    @Query("SELECT * FROM StoryBookLearningEvent")
    List<StoryBookLearningEvent> loadAll();
}
