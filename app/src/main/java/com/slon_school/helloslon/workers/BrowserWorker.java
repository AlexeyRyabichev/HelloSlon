package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.slon_school.helloslon.core.Key;
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
        keys.add(new Key("Яндекс"));
        keys.add(new Key("Гугл"));
        keys.add(new Key("искать"));
        //etc


    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue(){return false;}

    @Override
    public String doWork(ArrayList<Key> keys,ArrayList<String> arguments) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.yandex.ru?q="+arguments.get(0)));
        intent.setData(Uri.parse("https://www.google.ru/search?q="+arguments.get(0)));
        activity.startActivity(intent);
        return "";


    }

}