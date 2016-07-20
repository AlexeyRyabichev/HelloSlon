package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Volha on 17.07.2016.
 */
public class DilbertWorker extends Worker
{
    private boolean isLinkGot;
    private int picPointer;
    private ArrayList<Key> keys = new ArrayList<Key>();

    CountDownLatch countDownLatch;

    Thread thread;

    volatile ArrayList<String> picLinks = new ArrayList<String>();


    ArrayList<String> linkArr;

    public DilbertWorker( Activity activity ) {
        super( activity );
        keys.add(new Key("дилберт"));
        keys.add(new Key("гилберт"));
        fillPicLinks();
        picPointer = 0;
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
        if ( keys.size() != 0 ) {
            // начать сканировать сайт
            //http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82?updated-max=2016-06-17T08:00:00%2B01:00&max-results=500&start=0&by-date=false
            //<li><a href='http://dilbertru.blogspot.ru

            if ( keys.get( 0 ).toString().trim().equals( "гилберт" ) ) {
                picPointer = 0;
                return new Response( "Ты хочешь сказать \"Дилберт\"?", true );
            } else {
                picPointer = 0;
                return new Response( "", true, takePicLink());
            }
        } else if ( arguments.get().size() != 0 ) {
            picPointer++;
            String comand = arguments.get().get( 0 );
            if ( comand.equals( "следующий" ) || comand.equals( "еще" ) || comand.equals( "да" ) ) {
                if(picPointer == 500) return new Response( "Хватит уже", false );
                if( takePicLink().get(0).equals("Error")) return new Response( "Error", false );
                return new Response( "", true, takePicLink());
            }
            if ( comand.equals( "хватит" ) ) {
                return new Response( "Действительно хватит", false );
            }
        }
        return new Response( "Ничего не понял.", true);
    }

    private void fillPicLinks(){
        countDownLatch = new CountDownLatch(1);
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isLinkGot = getLinks();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  ArrayList<String> takePicLink() {
        ArrayList<String> _buf = new ArrayList<String>();
        if (isLinkGot) {
            _buf.add(picLinks.get(picPointer));
        } else {
            _buf.add("Error");
        }
        return _buf;
    }

    public boolean getLinks() throws Exception {
        boolean res = false;

        String line;
        URL url = new URL("http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82?updated-max=2016-06-17T08:00:00%2B01:00&max-results=500&start=0&by-date=false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                activity.getString(R.string.cp1251)));
        while ((line = reader.readLine()) != null) {
            if (line.contains("<img src=\"http://assets.amuniversal.com/")) {
                picLinks.add(washQuote(line));
                res = true;
            }
        }
        return res;
    }

    private String washQuote( String line ) {
        line = line.replace("<img src=\"","");
        line = line.replaceAll("\"(.)*","");
        //line = "http://assets.amuniversal.com/" + line;
        return line;
    }
}
