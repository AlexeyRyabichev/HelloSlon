package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Volha on 17.07.2016.
 */
public class DilbertWorker extends Worker
{
    private String link1;
    private String link2;
    private boolean isLinkGot;
    private ArrayList<Key> keys = new ArrayList<Key>();
    private int PicPointer;

    public DilbertWorker( Activity activity ) {
        super( activity );
        keys.add(new Key("дилберт"));
        keys.add(new Key("гилберт"));
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
    public Response doWork( ArrayList<Key> keys, Key arguments ) {
        if(keys.size() != 0) {
            // начать сканировать сайт
            //http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82
            //<li><a href='http://dilbertru.blogspot.ru

            //if(keys.get(0).get())
        }


        if(arguments.get().size() != 0){
            // работать с картинками
            //view-source:http://dilbertru.blogspot.ru/2016/07/20160716.html
            //<img src="http://assets.amuniversal.com

            String comand = arguments.get().get(0);
            if(comand.equals("следующий") || comand.equals("еще") || comand.equals("да")) {

            }
            if(comand.equals("хватит")) {
                return new Response("Действительно хватит", false);
            }
            if(comand.equals("нет")) {
                return new Response("Ну ладно", false);
            }
        }
        return null;
    }
}
