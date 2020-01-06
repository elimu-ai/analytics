package ai.elimu.analytics;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface StoryBookLearningEventDao {

    @Insert
    void insert(StoryBookLearningEvent storyBookLearningEvent);
}
