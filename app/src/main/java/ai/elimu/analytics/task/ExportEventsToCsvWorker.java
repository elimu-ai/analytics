package ai.elimu.analytics.task;

import android.content.Context;
import android.util.Log;

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

import ai.elimu.analytics.dao.LetterLearningEventDao;
import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.dao.WordLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.LetterLearningEvent;
import ai.elimu.analytics.entity.StoryBookLearningEvent;
import ai.elimu.analytics.entity.WordLearningEvent;

public class ExportEventsToCsvWorker extends Worker {

    public ExportEventsToCsvWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(getClass().getName(), "doWork");

        exportLetterLearningEventsToCsv();
        exportWordLearningEventsToCsv();
        exportStoryBookLearningEventsToCsv();

        return Result.success();
    }

    private void exportLetterLearningEventsToCsv() {
        Log.i(getClass().getName(), "exportLetterLearningEventsToCsv");

        // Extract LetterLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        LetterLearningEventDao letterLearningEventDao = roomDb.letterLearningEventDao();
        List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.loadAllOrderedByTimeDesc();
        Log.i(getClass().getName(), "letterLearningEvents.size(): " + letterLearningEvents.size());

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
                // Export event to CSV file. Example format: "files/letter-learning-events/7161a85a0e4751cd_letter-learning-events_2020-03-21.csv"
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(letterLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = letterLearningEvent.getAndroidId() + "_letter-learning-events_" + date + ".csv";
                Log.i(getClass().getName(), "csvFilename: " + csvFilename);

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
                File letterLearningEventsDir = new File(filesDir, "letter-learning-events");
                File csvFile = new File(letterLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }

    private void exportWordLearningEventsToCsv() {
        Log.i(getClass().getName(), "exportWordLearningEventsToCsv");

        // Extract WordLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        WordLearningEventDao wordLearningEventDao = roomDb.wordLearningEventDao();
        List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.loadAllOrderedByTimeDesc();
        Log.i(getClass().getName(), "wordLearningEvents.size(): " + wordLearningEvents.size());

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
                // Export event to CSV file. Example format: "files/word-learning-events/7161a85a0e4751cd_word-learning-events_2020-03-21.csv"
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(wordLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = wordLearningEvent.getAndroidId() + "_word-learning-events_" + date + ".csv";
                Log.i(getClass().getName(), "csvFilename: " + csvFilename);

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
                File wordLearningEventsDir = new File(filesDir, "word-learning-events");
                File csvFile = new File(wordLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
    
    private void exportStoryBookLearningEventsToCsv() {
        Log.i(getClass().getName(), "exportStoryBookLearningEventsToCsv");
        
        // Extract StoryBookLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
        Log.i(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());

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
                // Export event to CSV file. Example format: "files/storybook-learning-events/7161a85a0e4751cd_storybook-learning-events_2020-03-21.csv"
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(storyBookLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = storyBookLearningEvent.getAndroidId() + "_storybook-learning-events_" + date + ".csv";
                Log.i(getClass().getName(), "csvFilename: " + csvFilename);

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
                File storyBookLearningEventsDir = new File(filesDir, "storybook-learning-events");
                File csvFile = new File(storyBookLearningEventsDir, csvFilename);
                FileUtils.writeStringToFile(csvFile, csvFileContent, "UTF-8");
            }
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }
    }
}
