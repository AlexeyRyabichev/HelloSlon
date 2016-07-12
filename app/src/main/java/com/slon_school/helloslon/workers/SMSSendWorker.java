package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

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
        keys.add(new Key(activity.getString(R.string.smssend_keyword2)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.get().size() == 0) {
            return new Response(activity.getString(R.string.smssend_unrecognized_string0),finishSession);
        }
        /*
         * Just Log
         */
        for (String string : arguments.get()) {
            Toast.makeText(activity,string,Toast.LENGTH_LONG).show();
        }
            /*
             * Calling an intent
             */
        Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:8(904)201-91-23" + ""));
        intent.putExtra("sms_body", arguments.get().get(0));
        activity.startActivity(intent);

        return new Response(activity.getString(R.string.smssend_calling) + " " + "8(904)201-91-23", finishSession);
    }
}
