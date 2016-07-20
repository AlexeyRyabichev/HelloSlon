package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

import static com.slon_school.helloslon.core.Helper.BTS;
import static com.slon_school.helloslon.core.Helper.isDecimalNumber;
import static com.slon_school.helloslon.core.Helper.string2long;

/**
 * Created by Noob_upgraded on 19.07.2016.
 */
public class FlashlightWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<>();
    private final static boolean finishSession = false;
    private long time = 20000;
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
        String sTime = arguments.toString();
        if (isDecimalNumber(sTime)) {
            time = string2long(sTime) * 1000;
        }
        final Camera camera = Camera.open();
        Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        Thread thread = new Thread() {

            public void run() {
                super.run();
                try {
                    camera.startPreview();
                    sleep(time);
                    camera.stopPreview();
                } catch (Exception e) {
                    BTS(16);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return new Response("", finishSession);
    }
}
