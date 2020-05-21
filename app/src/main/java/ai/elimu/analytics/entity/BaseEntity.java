package ai.elimu.analytics.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public abstract class BaseEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
