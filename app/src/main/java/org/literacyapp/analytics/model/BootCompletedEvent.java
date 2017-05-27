package org.literacyapp.analytics.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.literacyapp.analytics.dao.converter.CalendarConverter;

import java.util.Calendar;

@Entity
public class BootCompletedEvent {

    @Id(autoincrement = true)
    private Long id;

    // TODO: replace with Device?
    @NotNull
    private String deviceId;

    @NotNull
    @Convert(converter = CalendarConverter.class, columnType = Long.class)
    private Calendar time;

    @Generated(hash = 1698024943)
    public BootCompletedEvent(Long id, @NotNull String deviceId,
            @NotNull Calendar time) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
    }

    @Generated(hash = 447047454)
    public BootCompletedEvent() {
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
}
