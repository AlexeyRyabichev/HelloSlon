package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by I. Dmitry on 16.07.2016.
 */
public class XKCDRandomComicWorker extends Worker{
    //TODO finish & add this to Core
    private ArrayList<Key> keys = new ArrayList<Key>();
    final static boolean finishSession = false;

    public XKCDRandomComicWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.xkcd_keyword0)));
        keys.add(new Key(activity.getString(R.string.xkcd_keyword1)));
        keys.add(new Key(activity.getString(R.string.xkcd_keyword2)));
        keys.add(new Key(activity.getString(R.string.xkcd_keyword3)));
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        return new Response("",finishSession);
    }
}
