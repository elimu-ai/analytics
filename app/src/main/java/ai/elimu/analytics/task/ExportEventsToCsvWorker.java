package ai.elimu.analytics.task;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import ai.elimu.analytics.dao.LetterAssessmentEventDao;
import ai.elimu.analytics.dao.LetterLearningEventDao;
import ai.elimu.analytics.dao.LetterSoundLearningEventDao;
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.WordAssessmentEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterAssessmentEvent;
import ai.elimu.analytics.entity.LetterLearningEvent;
import ai.elimu.analytics.entity.LetterSoundCorrespondenceLearningEvent;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.WordAssessmentEvent;
import ai.elimu.analytics.entity.WordLearningEvent;
import ai.elimu.analytics.util.VersionHelper;
import timber.log.Timber;

/**
 * Exports events from the database into CSV files, that will later be uploaded to the server by
 * the {@link UploadEventsWorker}.
 */
public class ExportEventsToCsvWorker extends Worker {

    public ExportEventsToCsvWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Timber.i("doWork");

        exportLetterLearningEventsToCsv();
        exportLetterAssessmentEventsToCsv();
        exportLetterSoundLearningEventsToCsv();
        exportWordLearningEventsToCsv();
        exportWordAssessmentEventsToCsv();
        exportStoryBookLearningEventsToCsv();

        return Result.success();
    }

    private void exportLetterLearningEventsToCsv() {
        Timber.i("exportLetterLearningEventsToCsv");

        // Extract LetterLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        LetterLearningEventDao letterLearningEventDao = roomDb.letterLearningEventDao();
        List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.loadAllOrderedByTimeDesc();
        Timber.i("letterLearningEvents.size(): " + letterLearningEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "letter_id",
                        "letter_text",
                        "learning_event_type"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (LetterLearningEvent letterLearningEvent : letterLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/letter-learning-events/7161a85a0e4751cd_3001012_letter-learning-events_2020-03-21.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(letterLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = letterLearningEvent.getAndroidId() + "_" + versionCode + "_letter-learning-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        letterLearningEvent.getId(),
                        letterLearningEvent.getTime().getTimeInMillis(),
                        letterLearningEvent.getAndroidId(),
                        letterLearningEvent.getPackageName(),
                        letterLearningEvent.getLetterId(),
                        letterLearningEvent.getLetterText(),
                        letterLearningEvent.getLearningEventType()
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File letterLearningEventsDir = new File(versionCodeDir, "letter-learning-events");
                File csvFile = new File(letterLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void exportLetterAssessmentEventsToCsv() {
        Timber.i("exportLetterAssessmentEventsToCsv");

        // Extract LetterAssessmentEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        LetterAssessmentEventDao letterAssessmentEventDao = roomDb.letterAssessmentEventDao();
        List<LetterAssessmentEvent> letterAssessmentEvents = letterAssessmentEventDao.loadAllOrderedByTimeAsc();
        Timber.i("letterAssessmentEvents.size(): " + letterAssessmentEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "letter_id",
                        "letter_text",
                        "mastery_score",
                        "time_spent_ms"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (LetterAssessmentEvent letterAssessmentEvent : letterAssessmentEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/letter-assessment-events/7161a85a0e4751cd_3001012_letter-assessment-events_2020-03-21.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(letterAssessmentEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = letterAssessmentEvent.getAndroidId() + "_" + versionCode + "_letter-assessment-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        letterAssessmentEvent.getId(),
                        letterAssessmentEvent.getTime().getTimeInMillis(),
                        letterAssessmentEvent.getAndroidId(),
                        letterAssessmentEvent.getPackageName(),
                        letterAssessmentEvent.getLetterId(),
                        letterAssessmentEvent.getLetterText(),
                        letterAssessmentEvent.getMasteryScore(),
                        letterAssessmentEvent.getTimeSpentMs()
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File letterAssessmentEventsDir = new File(versionCodeDir, "letter-assessment-events");
                File csvFile = new File(letterAssessmentEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void exportLetterSoundLearningEventsToCsv() {
        Timber.i("exportLetterSoundLearningEventsToCsv");

        // Extract LetterSoundLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        LetterSoundLearningEventDao letterSoundLearningEventDao = roomDb.letterSoundLearningEventDao();
        List<LetterSoundCorrespondenceLearningEvent> letterSoundLearningEvents = letterSoundLearningEventDao.loadAllOrderedByTime();
        Timber.i("letterSoundLearningEvents.size(): " + letterSoundLearningEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "letter_sound_id",
                        "letter_sound_letter_texts",
                        "letter_sound_sound_values_ipa"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);


            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (LetterSoundCorrespondenceLearningEvent letterSoundLearningEvent : letterSoundLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001017/letter-sound-learning-events/7161a85a0e4751cd_3001017_letter-sound-learning-events_2023-10-25.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(letterSoundLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = letterSoundLearningEvent.getAndroidId() + "_" + versionCode + "_letter-sound-learning-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        letterSoundLearningEvent.getId(),
                        letterSoundLearningEvent.getTime().getTimeInMillis(),
                        letterSoundLearningEvent.getAndroidId(),
                        letterSoundLearningEvent.getPackageName(),
                        letterSoundLearningEvent.getId(),
                        null,
                        null
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File letterSoundLearningEventsDir = new File(versionCodeDir, "letter-sound-learning-events");
                File csvFile = new File(letterSoundLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void exportWordLearningEventsToCsv() {
        Timber.i("exportWordLearningEventsToCsv");

        // Extract WordLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
        List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.loadAllOrderedByTimeDesc();
        Timber.i("wordLearningEvents.size(): " + wordLearningEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "word_id",
                        "word_text",
                        "learning_event_type"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (WordLearningEvent wordLearningEvent : wordLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/word-learning-events/7161a85a0e4751cd_3001012_word-learning-events_2020-03-21.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(wordLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = wordLearningEvent.getAndroidId() + "_" + versionCode + "_word-learning-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        wordLearningEvent.getId(),
                        wordLearningEvent.getTime().getTimeInMillis(),
                        wordLearningEvent.getAndroidId(),
                        wordLearningEvent.getPackageName(),
                        wordLearningEvent.getWordId(),
                        wordLearningEvent.getWordText(),
                        wordLearningEvent.getLearningEventType()
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File wordLearningEventsDir = new File(versionCodeDir, "word-learning-events");
                File csvFile = new File(wordLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void exportWordAssessmentEventsToCsv() {
        Timber.i("exportWordAssessmentEventsToCsv");

        // Extract WordAssessmentEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        WordAssessmentEventDao wordAssessmentEventDao = roomDb.wordAssessmentEventDao();
        List<WordAssessmentEvent> wordAssessmentEvents = wordAssessmentEventDao.loadAllOrderedByTimeAsc();
        Timber.i("wordAssessmentEvents.size(): " + wordAssessmentEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "word_id",
                        "word_text",
                        "mastery_score",
                        "time_spent_ms"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (WordAssessmentEvent wordAssessmentEvent : wordAssessmentEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/word-assessment-events/7161a85a0e4751cd_3001012_word-assessment-events_2020-03-21.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(wordAssessmentEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = wordAssessmentEvent.getAndroidId() + "_" + versionCode + "_word-assessment-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        wordAssessmentEvent.getId(),
                        wordAssessmentEvent.getTime().getTimeInMillis(),
                        wordAssessmentEvent.getAndroidId(),
                        wordAssessmentEvent.getPackageName(),
                        wordAssessmentEvent.getWordId(),
                        wordAssessmentEvent.getWordText(),
                        wordAssessmentEvent.getMasteryScore(),
                        wordAssessmentEvent.getTimeSpentMs()
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File wordAssessmentEventsDir = new File(versionCodeDir, "word-assessment-events");
                File csvFile = new File(wordAssessmentEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }
    
    private void exportStoryBookLearningEventsToCsv() {
        Timber.i("exportStoryBookLearningEventsToCsv");
        
        // Extract StoryBookLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
        Timber.i("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "time",
                        "android_id",
                        "package_name",
                        "storybook_id",
                        "learning_event_type"
                );
        StringWriter stringWriter = new StringWriter();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Generate one CSV file per day of events
            String dateOfPreviousEvent = null;
            for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
                // Export event to CSV file. Example format:
                //   files/version-code-3001012/storybook-learning-events/7161a85a0e4751cd_3001012_storybook-learning-events_2020-03-21.csv
                Integer versionCode = VersionHelper.getAppVersionCode(getApplicationContext());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(storyBookLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = storyBookLearningEvent.getAndroidId() + "_" + versionCode + "_storybook-learning-events_" + date + ".csv";
                Timber.i("csvFilename: " + csvFilename);

                csvPrinter.printRecord(
                        storyBookLearningEvent.getId(),
                        storyBookLearningEvent.getTime().getTimeInMillis(),
                        storyBookLearningEvent.getAndroidId(),
                        storyBookLearningEvent.getPackageName(),
                        storyBookLearningEvent.getStoryBookId(),
                        storyBookLearningEvent.getLearningEventType()
                );
                csvPrinter.flush();

                String csvFileContent = stringWriter.toString();

                // Write the content to the CSV file
                File filesDir = getApplicationContext().getFilesDir();
                File versionCodeDir = new File(filesDir, "version-code-" + versionCode);
                File storyBookLearningEventsDir = new File(versionCodeDir, "storybook-learning-events");
                File csvFile = new File(storyBookLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }
}
