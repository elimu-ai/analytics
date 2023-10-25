package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.LetterSoundLearningEvent;

@Dao
public interface LetterSoundLearningEventDao {

    @Insert
    void insert(LetterSoundLearningEvent letterSoundLearningEvent);

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY time")
    List<LetterSoundLearningEvent> loadAllOrderedByTime();

    @Query("SELECT * FROM LetterSoundLearningEvent ORDER BY time")
    Cursor loadAllOrderedByTimeCursor();
}
