package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

import static com.slon_school.helloslon.core.Helper.isDecimalNumber;
import static com.slon_school.helloslon.core.Helper.string2long;

public class FlashlightWorker extends Worker implements Helper.additionalInterface{
    private long time;
    ArrayList<Key> keys = new ArrayList<>();
    public FlashlightWorker(Activity activity) {
        super(activity, "фонарик");
        keys.add(new Key(activity.getString(R.string.flashlight_keyword0)));
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return getHelp();
        }
        final long MULTIPLE = 1000;
        final long DEFAULT_TIME = 60;
        String sTime = arguments.toString();
        if (isDecimalNumber(sTime)) {
            time = string2long(sTime) * MULTIPLE;
        } else {
            time = DEFAULT_TIME * MULTIPLE;
        }
            final Camera CAMERA = Camera.open();
            Parameters parameters = CAMERA.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            CAMERA.setParameters(parameters);
            Thread thread = new Thread() {

                public void run() {
                    super.run();
                    try {
                        CAMERA.startPreview();
                        sleep(time);
                        CAMERA.stopPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        return new Response("", FINISH_SESSION);
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.flashlight_help, activity).getHelp();
    }
}
