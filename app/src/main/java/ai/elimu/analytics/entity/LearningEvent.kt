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
    var time: Calendar = Calendar.getInstance()

    var additionalData: String? = null
}
