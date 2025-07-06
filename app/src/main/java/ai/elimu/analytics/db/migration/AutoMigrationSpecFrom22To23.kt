package ai.elimu.analytics.db.migration

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn(tableName = "StoryBookLearningEvent", columnName = "learningEventType")
@DeleteColumn(tableName = "NumberLearningEvent", columnName = "learningEventType")
@DeleteColumn(tableName = "VideoLearningEvent", columnName = "learningEventType")
@DeleteColumn(tableName = "WordLearningEvent", columnName = "learningEventType")
class AutoMigrationSpecFrom22To23: AutoMigrationSpec