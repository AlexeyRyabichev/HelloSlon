package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

public class BrowserWorker extends Worker {
    private ArrayList<String> keys;
    private boolean isContinue;

    public BrowserWorker(Activity activity) {
        super(activity);

        isContinue = false;
        keys = new ArrayList<String>();
        keys.add("найди");
        keys.add("поиск");
        keys.add("интернет");
        keys.add("браузер");
        keys.add("Яндекс");
        keys.add("Гугл");
        keys.add("искать");
        //etc


    }

    @Override
    public ArrayList<String> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue(){return false;}

    @Override
    public String doWork(ArrayList<String> arguments) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.yandex.ru?q="+arguments.get(0)));
        activity.startActivity(intent);
        return "";


    }

}