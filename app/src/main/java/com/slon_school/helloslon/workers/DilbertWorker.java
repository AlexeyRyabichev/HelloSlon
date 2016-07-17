package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Volha on 17.07.2016.
 */
public class DilbertWorker extends Worker
{


    public DilbertWorker( Activity activity ) {
        super( activity );
    }

    @Override
    public ArrayList<Key> getKeys() {
        return null;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Response doWork( ArrayList<Key> keys, Key arguments ) {
        return null;
    }
}
