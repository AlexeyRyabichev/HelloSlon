package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Noob_upgraded on 19.07.2016.
 */
public class FlashlightWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<>();
    private final static boolean finishSession = false;

    public FlashlightWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.flashlight_keyword0)));
        keys.add(new Key(activity.getString(R.string.flashlight_keyword1)));
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        boolean badReturn = false;
        Camera camera = Camera.open();
        Parameters parameters = camera.getParameters();
        Toast.makeText(activity, parameters.getFlashMode(), Toast.LENGTH_LONG).show();
        if (arguments.contains(new Key(activity.getString(R.string.flashlight_on)))) {
            Toast.makeText(activity, "IF", Toast.LENGTH_LONG).show();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
        } else if (arguments.contains(new Key(activity.getString(R.string.flashlight_off)))) {
            Toast.makeText(activity, "ELSE", Toast.LENGTH_LONG).show();
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
        } else {
            badReturn = true;
        }
        return new Response(badReturn ? activity.getString(R.string.flashlight_unaccessible_camera) : "", finishSession);
    }
}
