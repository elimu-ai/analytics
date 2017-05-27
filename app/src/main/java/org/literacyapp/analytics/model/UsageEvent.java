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

@Deprecated
@Entity
public class UsageEvent {

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

    @Convert(converter = LiteracySkillConverter.class, columnType = String.class)
    private LiteracySkill literacySkill;

    @Convert(converter = NumeracySkillConverter.class, columnType = String.class)
    private NumeracySkill numeracySkill;

    // TODO: replace with Letter?
    private String letter;

    // TODO: replace with Number?
    private Integer number;

    // TODO: replace with Word?
    private String word;

    @Generated(hash = 1208691141)
    public UsageEvent(Long id, @NotNull String deviceId, @NotNull Calendar time,
            @NotNull String packageName, String studentId,
            LiteracySkill literacySkill, NumeracySkill numeracySkill, String letter,
            Integer number, String word) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.packageName = packageName;
        this.studentId = studentId;
        this.literacySkill = literacySkill;
        this.numeracySkill = numeracySkill;
        this.letter = letter;
        this.number = number;
        this.word = word;
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

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
