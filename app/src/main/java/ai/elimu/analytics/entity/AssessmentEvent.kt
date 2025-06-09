package ai.elimu.analytics.entity

import androidx.room.Entity
import java.util.Calendar

@Entity
abstract class AssessmentEvent : BaseEntity() {
    @JvmField
    var androidId: String = ""

    @JvmField
    var packageName: String = ""

    lateinit var timestamp: Calendar
}
