package ai.elimu.analytics.service;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import ai.elimu.analytics.util.EnvironmentSettings;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadApplicationOpenedEventsAsyncTask extends AsyncTask<File, String, Void> {

    @Override
    protected Void doInBackground(File... files) {
        Log.i(getClass().getName(), "doInBackground");

        File file = files[0];
        Log.i(getClass().getName(), "file: " + file);
        Log.i(getClass().getName(), "file.getName(): " + file.getName());

        try {
            // See https://stackoverflow.com/a/11826317

            URL url = new URL(EnvironmentSettings.getBaseRestUrl() + "/analytics/application-opened-event/create");
            Log.i(getClass().getName(), "url: " + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());

            request.writeBytes("--*****\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"multipartFile\";filename=\"" + file.getName() + "\"\r\n");
            request.writeBytes("\r\n");

            byte[] fileBytes = FileUtils.readFileToByteArray(file);
            Log.i(getClass().getName(), "fileBytes.length: " + fileBytes.length);
            request.write(fileBytes);

            request.writeBytes("\r\n");
            request.writeBytes("--*****--\r\n");

            int responseCode = connection.getResponseCode();
            Log.i(getClass().getName(), "responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
            Log.i(getClass().getName(), "response: " + response);
        } catch (MalformedURLException e) {
            Log.e(getClass().getName(), null, e);
        } catch (IOException e) {
            Log.e(getClass().getName(), null, e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.i(getClass().getName(), "onPostExecute");
        super.onPostExecute(v);
    }
}
