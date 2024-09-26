package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ai.elimu.model.v2.enums.analytics.LearningEventType;
import timber.log.Timber;

public class VideoLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        Long videoId = intent.getLongExtra("videoId", 0);
        Timber.i("videoId: " + videoId);

        String videoTitle = intent.getStringExtra("videoTitle");
        Timber.i("videoTitle: \"" + videoTitle + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Timber.i("learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

        // TODO: new VideoLearningEvent()

        // TODO: Store in database
    }
}
