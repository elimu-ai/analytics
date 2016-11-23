package org.literacyapp.analytics.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.literacyapp.analytics.dao.converter.CalendarConverter;
import org.literacyapp.analytics.dao.converter.LiteracySkillConverter;
import org.literacyapp.analytics.dao.converter.NumeracySkillConverter;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

import java.util.Calendar;

@Entity
public class UsageEvent {

    @Id(autoincrement = true)
    private Long id;

    // TODO: replace with Device
    @NotNull
    private String deviceId;

    @NotNull
    @Convert(converter = CalendarConverter.class, columnType = Long.class)
    private Calendar time;

    // TODO: replace with Application
    @NotNull
    private String packageName;

    // TODO: Student

    @Convert(converter = LiteracySkillConverter.class, columnType = String.class)
    private LiteracySkill literacySkill;

    @Convert(converter = NumeracySkillConverter.class, columnType = String.class)
    private NumeracySkill numeracySkill;

    @Generated(hash = 1321395035)
    public UsageEvent(Long id, @NotNull String deviceId, @NotNull Calendar time,
            @NotNull String packageName, LiteracySkill literacySkill,
            NumeracySkill numeracySkill) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.packageName = packageName;
        this.literacySkill = literacySkill;
        this.numeracySkill = numeracySkill;
    }

    @Generated(hash = 2057329387)
    public UsageEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public LiteracySkill getLiteracySkill() {
        return literacySkill;
    }

    public void setLiteracySkill(LiteracySkill literacySkill) {
        this.literacySkill = literacySkill;
    }

    public NumeracySkill getNumeracySkill() {
        return numeracySkill;
    }

    public void setNumeracySkill(NumeracySkill numeracySkill) {
        this.numeracySkill = numeracySkill;
    }
}
