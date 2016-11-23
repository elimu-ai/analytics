package org.literacyapp.analytics.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UsageEvent {

    @Id(autoincrement = true)
    private Long id;

    // TODO: replace with Device
    @NotNull
    private String deviceId;

//    @NotNull
//    private Calendar time;

    // TODO: replace with Application
    @NotNull
    private String packageName;

    @Generated(hash = 206193768)
    public UsageEvent(Long id, @NotNull String deviceId,
            @NotNull String packageName) {
        this.id = id;
        this.deviceId = deviceId;
        this.packageName = packageName;
    }

    @Generated(hash = 2057329387)
    public UsageEvent() {
    }

    // TODO: Student

//    private LiteracySkill literacySkill;
//
//    private NumeracySkill numeracySkill;

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

//    public Calendar getTime() {
//        return time;
//    }
//
//    public void setTime(Calendar time) {
//        this.time = time;
//    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

//    public LiteracySkill getLiteracySkill() {
//        return literacySkill;
//    }
//
//    public void setLiteracySkill(LiteracySkill literacySkill) {
//        this.literacySkill = literacySkill;
//    }
//
//    public NumeracySkill getNumeracySkill() {
//        return numeracySkill;
//    }
//
//    public void setNumeracySkill(NumeracySkill numeracySkill) {
//        this.numeracySkill = numeracySkill;
//    }
}
