package org.literacyapp.analytics.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.literacyapp.analytics.dao.converter.CalendarConverter;

import java.util.Calendar;

@Entity
public class NumberLearningEvent {

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

    // TODO: replace with Number?
    private Integer number;

    @Generated(hash = 1493158911)
    public NumberLearningEvent(Long id, @NotNull String deviceId,
            @NotNull Calendar time, @NotNull String packageName, String studentId,
            Integer number) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.packageName = packageName;
        this.studentId = studentId;
        this.number = number;
    }

    @Generated(hash = 1328464525)
    public NumberLearningEvent() {
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

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
