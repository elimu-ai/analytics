package ai.elimu.analytics.db

import ai.elimu.analytics.enum.EventType
import ai.elimu.analytics.entity.BaseEntity
import ai.elimu.analytics.entity.LetterSoundAssessmentEvent
import ai.elimu.analytics.entity.LetterSoundLearningEvent
import ai.elimu.analytics.entity.NumberLearningEvent
import ai.elimu.analytics.entity.StoryBookLearningEvent
import ai.elimu.analytics.entity.VideoLearningEvent
import ai.elimu.analytics.entity.WordAssessmentEvent
import ai.elimu.analytics.entity.WordLearningEvent
import android.content.Context
import androidx.annotation.WorkerThread

/**
 * Load all analytics events of a specific type from database.
 */
@WorkerThread
fun EventType.getAllEvents(context: Context): List<BaseEntity> {

    // Extract events from the database that have not yet been exported to CSV.
    val roomDb = RoomDb.getDatabase(context)
    return when(this) {
        EventType.LETTER_SOUND_ASSESSMENT -> {
            roomDb.letterSoundAssessmentEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.LETTER_SOUND_LEARNING -> {
            roomDb.letterSoundLearningEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.STORY_BOOK_LEARNING -> {
            roomDb.storyBookLearningEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.WORD_ASSESSMENT -> {
            roomDb.wordAssessmentEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.WORD_LEARNING -> {
            roomDb.wordLearningEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.VIDEO_LEARNING -> {
            roomDb.videoLearningEventDao().loadAllOrderedByTimestampAsc()
        }
        EventType.NUMBER_LEARNING -> {
            roomDb.numberLearningEventDao().loadAllOrderedByTimestampAsc()
        }
    }
}

/**
 * Persist an analytics event to the database asynchronously.
 */
fun BaseEntity.persistEvent(context: Context) {

    val roomDb = RoomDb.getDatabase(context)
    RoomDb.databaseWriteExecutor.execute {
        when(this) {
            is LetterSoundAssessmentEvent -> {
                roomDb.letterSoundAssessmentEventDao().insert(this)
            }
            is LetterSoundLearningEvent -> {
                roomDb.letterSoundLearningEventDao().insert(this)
            }

            is WordAssessmentEvent -> {
                roomDb.wordAssessmentEventDao().insert(this)
            }
            is WordLearningEvent -> {
                roomDb.wordLearningEventDao().insert(this)
            }

            is NumberLearningEvent -> {
                roomDb.numberLearningEventDao().insert(this)
            }

            is StoryBookLearningEvent -> {
                roomDb.storyBookLearningEventDao().insert(this)
            }

            is VideoLearningEvent -> {
                roomDb.videoLearningEventDao().insert(this)
            }
        }
    }
}
