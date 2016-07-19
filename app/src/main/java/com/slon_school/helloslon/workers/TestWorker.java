package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 10.07.2016.
 */

//делай свой воркер по образу и подобию этого
public class TestWorker extends Worker {

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
    public boolean isLoop() {
        return isContinue;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
//        ArrayList<String> tmp;
//        tmp = new ArrayList<>();
//        tmp.add("https://im3-tub-ru.yandex.net/i?id=be1f6bd3ed95ee13fc4c810ea32f8cdd&n=33&h=215&w=296");
        Response helloWorld = new Response("Привет" , false);
        return helloWorld;
    }
}
