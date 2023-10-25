package ai.elimu.analytics.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.LetterSoundCorrespondenceLearningEvent;

@Dao
public interface LetterSoundCorrespondenceLearningEventDao {

    @Insert
    void insert(LetterSoundCorrespondenceLearningEvent letterSoundCorrespondenceLearningEvent);

    @Query("SELECT * FROM LetterSoundCorrespondenceLearningEvent ORDER BY time")
    List<LetterSoundCorrespondenceLearningEvent> loadAllOrderedByTime();
}
