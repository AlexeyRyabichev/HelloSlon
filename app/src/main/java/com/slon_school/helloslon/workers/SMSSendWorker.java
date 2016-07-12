package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;
import java.util.ArrayList;

/**
 * Created by I. Dmitry on 12.07.2016.
 */
public class SMSSendWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    private final static boolean finishSession = false;

    public SMSSendWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.smssend_keyword0)));
        keys.add(new Key(activity.getString(R.string.smssend_keyword1)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    //TODO Delete THIS shit
    @Override
    public boolean isContinue() {
        return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.toString().isEmpty()) {
            return new Response(activity.getString(R.string.smssend_unrecognized_string0),finishSession);
        }
        //TODO Get permissions (Already got?)

        //TODO How to call Intents???
        Intent intent = new Intent();
        intent.setAction("WHAT???"); //TODO What I have to import to make it work???
        //TODO How to recognize numbers???
        activity.startActivity(intent);
        return new Response(activity.getString(R.string.smssend_calling) + "SOME_NUMBER_OR_CONTACT_NAME",finishSession);
    }
}
