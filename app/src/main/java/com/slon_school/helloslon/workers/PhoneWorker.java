package com.slon_school.helloslon.workers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Mike on 16.07.2016.
 */
import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telecom.Call;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 14.07.2016.
 */
public class PhoneWorker extends Worker {

    private ArrayList<Key> keys;
    private enum State {Start, PhoneNumber, Call}
    private State state;
    private Call callPhone;

    private String phoneNumber;
    private String Call;

    public PhoneWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("набери"));
        keys.add(new Key("набрать"));
        keys.add(new Key("позвони"));
        keys.add(new Key("позвонить"));
        keys.add(new Key("дозвонись"));
        keys.add(new Key("звонок"));
        keys.add(new Key("звони"));
        keys.add(new Key("звонить"));

        state = State.Start;

        callPhone = Call.getDefault();
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return true;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Key error = new Key("ошибка");
        Key exit = new Key("отмена");

        if (arguments.get().size() == 0 && state == State.Start) {
            return start(arguments);
        } else if (state == State.PhoneNumber) {
            if (arguments.contains(exit)) {
                return exit();
            } else {
                return writePhoneNumber(arguments);
            }
        } else if (state == State.Writing) {
            if (arguments.contains(error)) {
                return start(arguments);
            } else if (arguments.contains(exit)){
                return exit();
            }
            return writeText(arguments);
        }

        return new Response("Повтори ещё раз, пожалуйста", true);
    }



    private Response start(Key arguments) {
        state = State.PhoneNumber;
        return new Response("Скажи мне номер телефона, на который ты хочешь позвонить", true);
    }

    }