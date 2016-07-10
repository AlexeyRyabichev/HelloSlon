package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 10.07.2016.
 */

//делай свой воркер по образу и подобию этого
public class TestWorker extends Worker {


    private ArrayList<String> keys;
    private boolean isContinue;


    public TestWorker(Activity activity) {
        super(activity);
        isContinue = false;
        keys = new ArrayList<String>();
        keys.add("привет");
        keys.add("слон");
        keys.add("как");
        keys.add("дела");
        //etc
    }


    @Override
    public ArrayList<String> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue() {
        return isContinue;
    }

    @Override
    public String doWork(ArrayList<String> arguments) {
        String helloWorld = "Привет";
        return helloWorld;
    }
}
