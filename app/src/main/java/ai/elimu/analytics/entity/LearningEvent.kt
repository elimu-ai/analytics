package ai.elimu.analytics.entity

import androidx.room.Entity
import java.util.Calendar

@Entity
abstract class LearningEvent : BaseEntity() {
    @JvmField
    var androidId: String = ""

    @JvmField
    var packageName: String = ""

    @JvmField
    var timestamp: Calendar = Calendar.getInstance()
}
