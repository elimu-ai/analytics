package ai.elimu.analytics.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import ai.elimu.analytics.dao.converter.CalendarConverter;
import java.util.Calendar;

import ai.elimu.analytics.model.VideoLearningEvent;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO_LEARNING_EVENT".
*/
public class VideoLearningEventDao extends AbstractDao<VideoLearningEvent, Long> {

    public static final String TABLENAME = "VIDEO_LEARNING_EVENT";

    /**
     * Properties of entity VideoLearningEvent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DeviceId = new Property(1, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property Time = new Property(2, long.class, "time", false, "TIME");
        public final static Property PackageName = new Property(3, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property StudentId = new Property(4, String.class, "studentId", false, "STUDENT_ID");
        public final static Property VideoId = new Property(5, Long.class, "videoId", false, "VIDEO_ID");
    }

    private final CalendarConverter timeConverter = new CalendarConverter();

    public VideoLearningEventDao(DaoConfig config) {
        super(config);
    }
    
    public VideoLearningEventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO_LEARNING_EVENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"DEVICE_ID\" TEXT NOT NULL ," + // 1: deviceId
                "\"TIME\" INTEGER NOT NULL ," + // 2: time
                "\"PACKAGE_NAME\" TEXT NOT NULL ," + // 3: packageName
                "\"STUDENT_ID\" TEXT," + // 4: studentId
                "\"VIDEO_ID\" INTEGER);"); // 5: videoId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO_LEARNING_EVENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, VideoLearningEvent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDeviceId());
        stmt.bindLong(3, timeConverter.convertToDatabaseValue(entity.getTime()));
        stmt.bindString(4, entity.getPackageName());
 
        String studentId = entity.getStudentId();
        if (studentId != null) {
            stmt.bindString(5, studentId);
        }
 
        Long videoId = entity.getVideoId();
        if (videoId != null) {
            stmt.bindLong(6, videoId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, VideoLearningEvent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDeviceId());
        stmt.bindLong(3, timeConverter.convertToDatabaseValue(entity.getTime()));
        stmt.bindString(4, entity.getPackageName());
 
        String studentId = entity.getStudentId();
        if (studentId != null) {
            stmt.bindString(5, studentId);
        }
 
        Long videoId = entity.getVideoId();
        if (videoId != null) {
            stmt.bindLong(6, videoId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public VideoLearningEvent readEntity(Cursor cursor, int offset) {
        VideoLearningEvent entity = new VideoLearningEvent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // deviceId
            timeConverter.convertToEntityProperty(cursor.getLong(offset + 2)), // time
            cursor.getString(offset + 3), // packageName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // studentId
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // videoId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, VideoLearningEvent entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDeviceId(cursor.getString(offset + 1));
        entity.setTime(timeConverter.convertToEntityProperty(cursor.getLong(offset + 2)));
        entity.setPackageName(cursor.getString(offset + 3));
        entity.setStudentId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVideoId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(VideoLearningEvent entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(VideoLearningEvent entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(VideoLearningEvent entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}