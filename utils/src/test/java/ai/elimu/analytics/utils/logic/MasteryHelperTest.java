package ai.elimu.analytics.utils.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ai.elimu.model.v2.gson.analytics.LetterAssessmentEventGson;

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
}
