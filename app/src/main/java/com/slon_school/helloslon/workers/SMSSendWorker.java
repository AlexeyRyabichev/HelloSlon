package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Helper;
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
        keys.add(new Key(activity.getString(R.string.smssend_keyword3)));
        keys.add(new Key(activity.getString(R.string.smssend_keyword4)));
        keys.add(new Key(activity.getString(R.string.smssend_keyword5)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.get().size() == 0) {
            return new Response(activity.getString(R.string.smssend_unrecognized_string0),finishSession);
        }
        String phoneNumber = arguments.toString();
        //TODO Add contacts recognition
        if (Helper.isValidMobilePhoneNumber(phoneNumber)) {
            startActivity(phoneNumber);
            return new Response(activity.getString(R.string.smssend_sending) + " " + phoneNumber, finishSession);
        } else {
            return new Response(activity.getString(R.string.smssend_unknown) + " " + phoneNumber + "?", finishSession);
        }
    }

    private void startActivity(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(activity.getString(R.string.smssend_smsto) + phoneNumber));
        intent.putExtra("sms_body", phoneNumber); //TODO delete it, debug needs only
        activity.startActivity(intent);
    }
}
