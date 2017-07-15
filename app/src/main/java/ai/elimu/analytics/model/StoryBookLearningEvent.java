package ai.elimu.analytics.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import ai.elimu.analytics.dao.converter.CalendarConverter;

import java.util.Calendar;

@Entity
public class StoryBookLearningEvent {

    @Id(autoincrement = true)
    private Long id;

    // TODO: replace with Device?
    @NotNull
    private String deviceId;

    @NotNull
    @Convert(converter = CalendarConverter.class, columnType = Long.class)
    private Calendar time;

    // TODO: replace with Application?
    @NotNull
    private String packageName;

    // TODO: replace with Student?
    private String studentId;

    // TODO: replace with StoryBook?
    // TODO: add @NotNull
    private Long storyBookId;

    @Generated(hash = 1450632809)
    public StoryBookLearningEvent(Long id, @NotNull String deviceId,
            @NotNull Calendar time, @NotNull String packageName, String studentId,
            Long storyBookId) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.packageName = packageName;
        this.studentId = studentId;
        this.storyBookId = storyBookId;
    }

    @Generated(hash = 333305170)
    public StoryBookLearningEvent() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Calendar getTime() {
        return this.time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getStoryBookId() {
        return this.storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }
}
