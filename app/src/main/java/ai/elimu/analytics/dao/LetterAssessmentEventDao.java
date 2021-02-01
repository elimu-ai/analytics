package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.LetterAssessmentEvent;

@Dao
public interface LetterAssessmentEventDao {

    @Insert
    void insert(LetterAssessmentEvent letterAssessmentEvent);

    @Query("SELECT * FROM LetterAssessmentEvent ORDER BY time ASC")
    List<LetterAssessmentEvent> loadAllOrderedByTimeAsc();

    @Query("SELECT * FROM LetterAssessmentEvent ORDER BY time ASC")
    Cursor loadAllOrderedByTimeAscAsCursor();

    @Query("SELECT * FROM LetterAssessmentEvent WHERE letterId = :letterId ORDER BY time DESC")
    Cursor loadAll(Long letterId);
}
