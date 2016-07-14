package com.slon_school.helloslon.workers;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
/**
 * Created by Mike on 12.07.2016.
 */
public class PhoneWorker extends Worker {
    private ArrayList<Key> keys;
    private boolean isContinue;

    public PhoneWorker(Activity activity) {
        super(activity);

        isContinue = false;
        keys = new ArrayList<Key>();
        keys.add(new Key("позвони"));
        keys.add(new Key("набери"));
        keys.add(new Key("позвонить"));
        keys.add(new Key("набрать"));
        keys.add(new Key("дозвонись"));


        //etc
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop(){return false;}

    @Override
    public Response doWork(ArrayList<Key> result, Key arguments) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:8(903)635-88-99"));
        activity.startActivity(intent);
        return new Response("", false);
    }


}
