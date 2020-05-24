package ai.elimu.analytics.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.elimu.analytics.entity.WordAssessmentEvent;

@Dao
public interface WordAssessmentEventDao {

    @Insert
    void insert(WordAssessmentEvent wordAssessmentEvent);

    @Query("SELECT * FROM WordAssessmentEvent ORDER BY time ASC")
    List<WordAssessmentEvent> loadAllOrderedByTimeAsc();

    @Query("SELECT * FROM WordAssessmentEvent ORDER BY time ASC")
    Cursor loadAllOrderedByTimeAscAsCursor();
}
