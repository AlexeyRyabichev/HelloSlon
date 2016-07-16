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
public class EmailWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    private final static boolean finishSession = false;

    public EmailWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.email_keyword0)));
        keys.add(new Key(activity.getString(R.string.email_keyword1)));
        keys.add(new Key(activity.getString(R.string.email_keyword2)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.toString().isEmpty()) {
            return new Response(activity.getString(R.string.email_unrecognized_string0),finishSession);
        }
        String phoneNumber = arguments.toString();
        //TODO something with this
        if (Helper.isValidMobilePhoneNumber(phoneNumber)) {
            startActivity(phoneNumber);
            return new Response(activity.getString(R.string.email_sending) + " " + phoneNumber, finishSession);
        } else {
            return new Response(activity.getString(R.string.email_unknown) + " " + phoneNumber + "?", finishSession);
        }
    }

    private void startActivity(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(activity.getString(R.string.email_smsto) + phoneNumber));
        intent.putExtra("sms_body", phoneNumber); //TODO delete it, debug needs only
        activity.startActivity(intent);
    }
}
