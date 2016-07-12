package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 10.07.2016.
 */

//делай свой воркер по образу и подобию этого
public class    TestWorker extends Worker {

    private ArrayList<Key> keys;
    private boolean isContinue;


    public TestWorker(Activity activity) {
        super(activity);
        isContinue = false;
        keys = new ArrayList<Key>();
        keys.add(new Key("привет"));
        keys.add(new Key("слон"));
        keys.add(new Key("как"));
        keys.add(new Key("дела"));
        //etc
    }


    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue() {
        return isContinue;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Response helloWorld = new Response("Тестовый worker" , false);
        return helloWorld;
    }
}
