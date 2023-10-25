package ai.elimu.analytics.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import ai.elimu.analytics.entity.LetterSoundCorrespondenceLearningEvent;

@Dao
public interface LetterSoundCorrespondenceLearningEventDao {

    @Insert
    void insert(LetterSoundCorrespondenceLearningEvent letterSoundCorrespondenceLearningEvent);
}
