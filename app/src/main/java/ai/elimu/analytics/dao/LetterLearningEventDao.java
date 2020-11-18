package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.LetterLearningEvent;

@Dao
public interface LetterLearningEventDao {

    @Insert
    void insert(LetterLearningEvent letterLearningEvent);

    @Query("SELECT * FROM LetterLearningEvent ORDER BY time DESC")
    List<LetterLearningEvent> loadAllOrderedByTimeDesc();

    @Query("SELECT * FROM LetterLearningEvent ORDER BY time")
    Cursor loadAllOrderedByTime();
}
