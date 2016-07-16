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
public class PhoneWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    private final static boolean finishSession = false;

    public PhoneWorker (Activity activity) {
        super(activity);
        keys.add(new Key("позвони"));
        keys.add(new Key("позвонить"));
        keys.add(new Key("дозвнись"));
        keys.add(new Key("набери"));
        keys.add(new Key("набрать"));
        keys.add(new Key("звонок"));
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
            return new Response("Повтори номер, пожалуйста.",finishSession);
        }
        String phoneNumber = arguments.toString();

        if (Helper.isValidMobilePhoneNumber(phoneNumber)) {
            startActivity(phoneNumber);
            return new Response("Выполняю звонок наномер" + " " + phoneNumber, finishSession);
        } else {
            return new Response("Не могу найти такой номер" + " " + phoneNumber + "?", finishSession);
        }
    }

    private void startActivity(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        activity.startActivity(intent);
    }
}
