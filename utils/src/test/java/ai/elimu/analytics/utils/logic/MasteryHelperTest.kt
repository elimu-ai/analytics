package ai.elimu.analytics.utils.logic

import ai.elimu.analytics.utils.logic.MasteryHelper.isWordMastered
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import java.util.Calendar

class MasteryHelperTest {
    @Test
    fun testIsWordMastered_False() {
        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGsonNow = WordAssessmentEventGson()
        val calendarNow = Calendar.getInstance()
        wordAssessmentEventGsonNow.timestamp = calendarNow
        wordAssessmentEventGsonNow.masteryScore = 0.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson10MinutesAgo = WordAssessmentEventGson()
        val calendar10MinutesAgo = Calendar.getInstance()
        calendar10MinutesAgo.add(Calendar.MINUTE, -10)
        wordAssessmentEventGson10MinutesAgo.timestamp = calendar10MinutesAgo
        wordAssessmentEventGson10MinutesAgo.masteryScore = 0.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson20MinutesAgo = WordAssessmentEventGson()
        val calendar20MinutesAgo = Calendar.getInstance()
        calendar20MinutesAgo.add(Calendar.MINUTE, -20)
        wordAssessmentEventGson20MinutesAgo.timestamp = calendar20MinutesAgo
        wordAssessmentEventGson20MinutesAgo.masteryScore = 0.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))
    }


    @Test
    fun testIsWordMastered_True() {
        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGsonNow = WordAssessmentEventGson()
        val calendarNow = Calendar.getInstance()
        wordAssessmentEventGsonNow.timestamp = calendarNow
        wordAssessmentEventGsonNow.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson10MinutesAgo = WordAssessmentEventGson()
        val calendar10MinutesAgo = Calendar.getInstance()
        calendar10MinutesAgo.add(Calendar.MINUTE, -10)
        wordAssessmentEventGson10MinutesAgo.timestamp = calendar10MinutesAgo
        wordAssessmentEventGson10MinutesAgo.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson20MinutesAgo = WordAssessmentEventGson()
        val calendar20MinutesAgo = Calendar.getInstance()
        calendar20MinutesAgo.add(Calendar.MINUTE, -20)
        wordAssessmentEventGson20MinutesAgo.timestamp = calendar20MinutesAgo
        wordAssessmentEventGson20MinutesAgo.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(true))

        val wordAssessmentEventGson30MinutesAgo = WordAssessmentEventGson()
        val calendar30MinutesAgo = Calendar.getInstance()
        calendar30MinutesAgo.add(Calendar.MINUTE, -30)
        wordAssessmentEventGson30MinutesAgo.timestamp = calendar30MinutesAgo
        wordAssessmentEventGson30MinutesAgo.masteryScore = 0.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson30MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(true))
    }

    @Test
    fun testIsWordMastered_NotRecent_False() {
        val wordAssessmentEventGsons: MutableList<WordAssessmentEventGson> = ArrayList()
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGsonNow = WordAssessmentEventGson()
        val calendarNow = Calendar.getInstance()
        wordAssessmentEventGsonNow.timestamp = calendarNow
        wordAssessmentEventGsonNow.masteryScore = 0.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson10MinutesAgo = WordAssessmentEventGson()
        val calendar10MinutesAgo = Calendar.getInstance()
        calendar10MinutesAgo.add(Calendar.MINUTE, -10)
        wordAssessmentEventGson10MinutesAgo.timestamp = calendar10MinutesAgo
        wordAssessmentEventGson10MinutesAgo.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson20MinutesAgo = WordAssessmentEventGson()
        val calendar20MinutesAgo = Calendar.getInstance()
        calendar20MinutesAgo.add(Calendar.MINUTE, -20)
        wordAssessmentEventGson20MinutesAgo.timestamp = calendar20MinutesAgo
        wordAssessmentEventGson20MinutesAgo.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))

        val wordAssessmentEventGson30MinutesAgo = WordAssessmentEventGson()
        val calendar30MinutesAgo = Calendar.getInstance()
        calendar30MinutesAgo.add(Calendar.MINUTE, -30)
        wordAssessmentEventGson30MinutesAgo.timestamp = calendar30MinutesAgo
        wordAssessmentEventGson30MinutesAgo.masteryScore = 1.00f
        wordAssessmentEventGsons.add(wordAssessmentEventGson30MinutesAgo)
        MatcherAssert.assertThat(isWordMastered(wordAssessmentEventGsons), CoreMatchers.`is`(false))
    }
}
