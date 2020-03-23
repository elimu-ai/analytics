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

import ai.elimu.analytics.dao.StoryBookLearningEventDao;
import ai.elimu.analytics.db.RoomDb;
import ai.elimu.analytics.entity.StoryBookLearningEvent;

public class ExportEventsToCsvWorker extends Worker {

    public ExportEventsToCsvWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(getClass().getName(), "doWork");

        // Extract StoryBookLearningEvents from the database that have not yet been exported to CSV.
        RoomDb roomDb = RoomDb.getDatabase(getApplicationContext());
        StoryBookLearningEventDao storyBookLearningEventDao = roomDb.storyBookLearningEventDao();
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.loadAll();
        Log.i(getClass().getName(), "storyBookLearningEvents.size(): " + storyBookLearningEvents.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
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
                // Export event to CSV file. Example format: "files/storybook-learning-events/storybook-learning-events_2020-03-21.csv"
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(storyBookLearningEvent.getTime().getTime());
                if (!date.equals(dateOfPreviousEvent)) {
                    // Reset file content
                    stringWriter = new StringWriter();
                    csvPrinter = new CSVPrinter(stringWriter, csvFormat);
                }
                dateOfPreviousEvent = date;
                String csvFilename = "storybook-learning-events_" + date + ".csv";
                Log.i(getClass().getName(), "csvFilename: " + csvFilename);

                csvPrinter.printRecord(
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

        return Result.success();
    }
}
