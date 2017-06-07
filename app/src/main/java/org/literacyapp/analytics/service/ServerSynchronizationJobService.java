package org.literacyapp.analytics.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServerSynchronizationJobService extends Service {
    public ServerSynchronizationJobService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
