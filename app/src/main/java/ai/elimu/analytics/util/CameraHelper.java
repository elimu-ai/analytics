package ai.elimu.analytics.util;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * See https://developer.android.com/training/camera/cameradirect.html
 */
public class CameraHelper {

    private File pictureFile;

    public File takePicture(Context context, String picturePath) {
        Log.i(getClass().getName(), "takePicture");

        pictureFile = new File(picturePath);
        Log.i(DisplayHelper.class.getName(), "pictureFile: " + pictureFile);

//        String deviceModel = DeviceInfoHelper.getDeviceModel(context);
//        Log.i(getClass().getName(), "deviceModel: " + deviceModel);

        try {
            // http://stackoverflow.com/questions/32770723/capturing-image-from-camera-in-a-background-service
            int cameraId = 1; // Front camera
            Camera mCamera = Camera.open(cameraId); // Throws Exception if the camera is already in use by another application
            Log.i(getClass().getName(), "mCamera: " + mCamera);
            SurfaceView surfaceView = new SurfaceView(context);
            Log.i(getClass().getName(), "surfaceView: " + surfaceView);
            Camera.Parameters parameters = mCamera.getParameters();
            Log.i(getClass().getName(), "parameters: " + parameters);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.takePicture(null, null, mPictureCallback);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Failed to open camera", e);
        }

        return pictureFile;
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            Log.i(getClass().getName(), "onPictureTaken");

            Log.i(getClass().getName(), "bytes.length: " + bytes.length);

            // TODO: reduce image size

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.i(getClass().getName(), null, e);
            } catch (IOException e) {
                Log.i(getClass().getName(), null, e);
            } finally {
                camera.stopPreview();
                camera.release();
            }
        }
    };
}
