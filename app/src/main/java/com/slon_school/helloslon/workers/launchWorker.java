package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Noob_upgraded on 19.07.2016.
 */
public class LaunchWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<>();
    private final static boolean finishSession = false;

    public LaunchWorker(Activity activity) {
        super(activity);
        keys.add(new Key(""));
    }

    @Override
    public ArrayList<Key> getKeys() { return null; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {

        return new Response("",finishSession);
    }
}
