package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.slon_school.helloslon.core.Helper.BTS;

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
        final Camera camera = Camera.open();
        Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    camera.startPreview();
                    //sleep(3000);
                    //camera.stopPreview();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    BTS(5);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            BTS(6);
            e.printStackTrace();
        }
        return new Response("", finishSession);
    }
}
