package ai.elimu.analytics.utils.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;
import ai.elimu.model.v2.gson.analytics.WordAssessmentEventGson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MasteryHelperTest {

    @Test
    public void testIsLetterMastered_False() {
        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGsonNow = new LetterAssessmentEventGson();
        Calendar calendarNow = Calendar.getInstance();
        letterAssessmentEventGsonNow.setTime(calendarNow);
        letterAssessmentEventGsonNow.setMasteryScore(0.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGsonNow);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGson10MinutesAgo = new LetterAssessmentEventGson();
        Calendar calendar10MinutesAgo = Calendar.getInstance();
        calendar10MinutesAgo.add(Calendar.MINUTE, -10);
        letterAssessmentEventGson10MinutesAgo.setTime(calendar10MinutesAgo);
        letterAssessmentEventGson10MinutesAgo.setMasteryScore(0.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGson10MinutesAgo);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGson20MinutesAgo = new LetterAssessmentEventGson();
        Calendar calendar20MinutesAgo = Calendar.getInstance();
        calendar20MinutesAgo.add(Calendar.MINUTE, -20);
        letterAssessmentEventGson20MinutesAgo.setTime(calendar20MinutesAgo);
        letterAssessmentEventGson20MinutesAgo.setMasteryScore(0.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGson20MinutesAgo);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));
    }

    @Test
    public void testIsLetterMastered_True() {
        List<LetterAssessmentEventGson> letterAssessmentEventGsons = new ArrayList<>();
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGsonNow = new LetterAssessmentEventGson();
        Calendar calendarNow = Calendar.getInstance();
        letterAssessmentEventGsonNow.setTime(calendarNow);
        letterAssessmentEventGsonNow.setMasteryScore(1.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGsonNow);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGson10MinutesAgo = new LetterAssessmentEventGson();
        Calendar calendar10MinutesAgo = Calendar.getInstance();
        calendar10MinutesAgo.add(Calendar.MINUTE, -10);
        letterAssessmentEventGson10MinutesAgo.setTime(calendar10MinutesAgo);
        letterAssessmentEventGson10MinutesAgo.setMasteryScore(1.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGson10MinutesAgo);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(false));

        LetterAssessmentEventGson letterAssessmentEventGson20MinutesAgo = new LetterAssessmentEventGson();
        Calendar calendar20MinutesAgo = Calendar.getInstance();
        calendar20MinutesAgo.add(Calendar.MINUTE, -20);
        letterAssessmentEventGson20MinutesAgo.setTime(calendar20MinutesAgo);
        letterAssessmentEventGson20MinutesAgo.setMasteryScore(1.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGson20MinutesAgo);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(true));

        LetterAssessmentEventGson letterAssessmentEventGson30MinutesAgo = new LetterAssessmentEventGson();
        Calendar calendar30MinutesAgo = Calendar.getInstance();
        calendar30MinutesAgo.add(Calendar.MINUTE, -30);
        letterAssessmentEventGson30MinutesAgo.setTime(calendar30MinutesAgo);
        letterAssessmentEventGson30MinutesAgo.setMasteryScore(0.00f);
        letterAssessmentEventGsons.add(letterAssessmentEventGson30MinutesAgo);
        assertThat(MasteryHelper.isLetterMastered(letterAssessmentEventGsons), is(true));
    }

    @Test
    public void testIsWordMastered_False() {
        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGsonNow = new WordAssessmentEventGson();
        Calendar calendarNow = Calendar.getInstance();
        wordAssessmentEventGsonNow.setTime(calendarNow);
        wordAssessmentEventGsonNow.setMasteryScore(0.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson10MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar10MinutesAgo = Calendar.getInstance();
        calendar10MinutesAgo.add(Calendar.MINUTE, -10);
        wordAssessmentEventGson10MinutesAgo.setTime(calendar10MinutesAgo);
        wordAssessmentEventGson10MinutesAgo.setMasteryScore(0.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson20MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar20MinutesAgo = Calendar.getInstance();
        calendar20MinutesAgo.add(Calendar.MINUTE, -20);
        wordAssessmentEventGson20MinutesAgo.setTime(calendar20MinutesAgo);
        wordAssessmentEventGson20MinutesAgo.setMasteryScore(0.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));
    }

    @Test
    public void testIsWordMastered_True() {
        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGsonNow = new WordAssessmentEventGson();
        Calendar calendarNow = Calendar.getInstance();
        wordAssessmentEventGsonNow.setTime(calendarNow);
        wordAssessmentEventGsonNow.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson10MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar10MinutesAgo = Calendar.getInstance();
        calendar10MinutesAgo.add(Calendar.MINUTE, -10);
        wordAssessmentEventGson10MinutesAgo.setTime(calendar10MinutesAgo);
        wordAssessmentEventGson10MinutesAgo.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson20MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar20MinutesAgo = Calendar.getInstance();
        calendar20MinutesAgo.add(Calendar.MINUTE, -20);
        wordAssessmentEventGson20MinutesAgo.setTime(calendar20MinutesAgo);
        wordAssessmentEventGson20MinutesAgo.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(true));

        WordAssessmentEventGson wordAssessmentEventGson30MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar30MinutesAgo = Calendar.getInstance();
        calendar30MinutesAgo.add(Calendar.MINUTE, -30);
        wordAssessmentEventGson30MinutesAgo.setTime(calendar30MinutesAgo);
        wordAssessmentEventGson30MinutesAgo.setMasteryScore(0.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson30MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(true));
    }

    @Test
    public void testIsWordMastered_NotRecent_False() {
        List<WordAssessmentEventGson> wordAssessmentEventGsons = new ArrayList<>();
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGsonNow = new WordAssessmentEventGson();
        Calendar calendarNow = Calendar.getInstance();
        wordAssessmentEventGsonNow.setTime(calendarNow);
        wordAssessmentEventGsonNow.setMasteryScore(0.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGsonNow);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson10MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar10MinutesAgo = Calendar.getInstance();
        calendar10MinutesAgo.add(Calendar.MINUTE, -10);
        wordAssessmentEventGson10MinutesAgo.setTime(calendar10MinutesAgo);
        wordAssessmentEventGson10MinutesAgo.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson10MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson20MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar20MinutesAgo = Calendar.getInstance();
        calendar20MinutesAgo.add(Calendar.MINUTE, -20);
        wordAssessmentEventGson20MinutesAgo.setTime(calendar20MinutesAgo);
        wordAssessmentEventGson20MinutesAgo.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson20MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));

        WordAssessmentEventGson wordAssessmentEventGson30MinutesAgo = new WordAssessmentEventGson();
        Calendar calendar30MinutesAgo = Calendar.getInstance();
        calendar30MinutesAgo.add(Calendar.MINUTE, -30);
        wordAssessmentEventGson30MinutesAgo.setTime(calendar30MinutesAgo);
        wordAssessmentEventGson30MinutesAgo.setMasteryScore(1.00f);
        wordAssessmentEventGsons.add(wordAssessmentEventGson30MinutesAgo);
        assertThat(MasteryHelper.isWordMastered(wordAssessmentEventGsons), is(false));
    }
}
