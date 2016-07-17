package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

public class BrowserWorker extends Worker {
    private ArrayList<Key> keys;
    private boolean isContinue;

    public BrowserWorker(Activity activity) {
        super(activity);

        isContinue = false;
        keys = new ArrayList<Key>();
        keys.add(new Key("найди"));
        keys.add(new Key("поиск"));
        keys.add(new Key("интернет"));
        keys.add(new Key("браузер"));
        keys.add(new Key("яндекс"));
        keys.add(new Key("google"));
        keys.add(new Key("искать"));
        keys.add(new Key("найти"));

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
        //String request = arguments.get(0);
        if (arguments.contains(new Key("help")) || arguments.contains(new Key("helper"))) {
            HelpMan helpMan = new HelpMan("BrowserWorker");
        }

        Key google = this.keys.get(5);

        if(result.contains(this.keys.get(5))){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.ru/search?q=" + arguments.toString()));
            activity.startActivity(intent);
            return new Response("", false);
        }
        else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.yandex.ru?q=" + arguments.toString()));
            activity.startActivity(intent);
            return new Response("", false);
        }


    }

}