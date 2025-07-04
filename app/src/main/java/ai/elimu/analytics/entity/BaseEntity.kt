package ai.elimu.analytics.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
