package com.slon_school.helloslon.workers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

import static com.slon_school.helloslon.core.Helper.isDecimalNumber;
import static com.slon_school.helloslon.core.Helper.string2long;

public class FlashlightWorker extends Worker implements Helper.additionalInterface {
    private long time;
    ArrayList<Key> keys = new ArrayList<>();

    public FlashlightWorker(Activity activity) {
        super(activity, "фонарик");
        keys.add(new Key(activity.getString(R.string.flashlight_keyword0)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return getHelp();
        }
        final long MULTIPLE = 1000;
        final long DEFAULT_TIME = 60;
        String sTime = arguments.toString();
        Toast.makeText(activity, sTime, Toast.LENGTH_LONG).show();
        if (isDecimalNumber(sTime.trim())) {
            time = string2long(sTime) * MULTIPLE;
        } else {
            time = DEFAULT_TIME * MULTIPLE;
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
            return new Response("Попробуйте ещё раз", false);
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
        public Response getHelp () {
            return new HelpMan(R.raw.flashlight_help, activity).getHelp();
        }
    }