package org.literacyapp.analytics.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.literacyapp.analytics.dao.converter.CalendarConverter;

import java.util.Calendar;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LetterLearningEvent {

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

    // TODO: replace with Letter?
    private String letter;

    @Generated(hash = 1587191339)
    public LetterLearningEvent(Long id, @NotNull String deviceId,
            @NotNull Calendar time, @NotNull String packageName, String studentId,
            String letter) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.packageName = packageName;
        this.studentId = studentId;
        this.letter = letter;
    }

    @Generated(hash = 2120429600)
    public LetterLearningEvent() {
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

    public String getLetter() {
        return this.letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
