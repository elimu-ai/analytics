package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.LetterSoundCorrespondenceLearningEvent;

@Dao
public interface LetterSoundLearningEventDao {

    @Insert
    void insert(LetterSoundCorrespondenceLearningEvent letterSoundCorrespondenceLearningEvent);

    @Query("SELECT * FROM LetterSoundCorrespondenceLearningEvent ORDER BY time")
    List<LetterSoundCorrespondenceLearningEvent> loadAllOrderedByTime();

    @Query("SELECT * FROM LetterSoundCorrespondenceLearningEvent ORDER BY time")
    Cursor loadAllOrderedByTimeCursor();
}
