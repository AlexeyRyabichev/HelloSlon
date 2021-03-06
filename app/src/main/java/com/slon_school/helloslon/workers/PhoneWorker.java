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
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Mike on 14.07.2016.
 */
public class PhoneWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<>();

    public PhoneWorker(Activity activity) {
        super(activity, "телефон");
        keys.add(new Key("набери"));
        keys.add(new Key("набрать"));
        keys.add(new Key("позвони"));
        keys.add(new Key("позвонить"));
        keys.add(new Key("дозвонись"));
        keys.add(new Key("звонок"));
        keys.add(new Key("звони"));
        keys.add(new Key("звонить"));


    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + arguments.toString()));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
            return new Response("",false);
        }
        activity.startActivity(intent);
        return new Response("", false);
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.phone_help, activity).getHelp();
    }
}