package ai.elimu.analytics.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import ai.elimu.analytics.entity.LetterLearningEvent;

@Dao
public interface LetterLearningEventDao {

    @Insert
    void insert(LetterLearningEvent letterLearningEvent);
}
