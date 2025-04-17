package ai.elimu.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Calendar;

import ai.elimu.analytics.entity.VideoLearningEvent;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import timber.log.Timber;

public class VideoLearningEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive");

        Calendar timestamp = Calendar.getInstance();
        Timber.i("timestamp.getTime(): " + timestamp.getTime());

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Timber.i("androidId: \"" + androidId + "\"");

        String packageName = intent.getStringExtra("packageName");
        Timber.i("packageName: \"" + packageName + "\"");

        String learningEventTypeAsString = intent.getStringExtra("learningEventType");
        Timber.i("learningEventTypeAsString: \"" + learningEventTypeAsString + "\"");
        LearningEventType learningEventType = LearningEventType.valueOf(learningEventTypeAsString);
        Timber.i("learningEventType: " + learningEventType);

        Long videoId = intent.getLongExtra("videoId", 0);
        Timber.i("videoId: " + videoId);

        String videoTitle = intent.getStringExtra("videoTitle");
        Timber.i("videoTitle: \"" + videoTitle + "\"");

        VideoLearningEvent videoLearningEvent = new VideoLearningEvent();
        videoLearningEvent.time = timestamp;
        videoLearningEvent.androidId = androidId;
        videoLearningEvent.packageName = packageName;
        videoLearningEvent.setLearningEventType(learningEventType);
        videoLearningEvent.setVideoId(videoId);
        videoLearningEvent.setVideoTitle(videoTitle);

        // TODO: Store in database
    }
}
