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
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Volha on 16.07.2016.
 */
public class CalvinHobbsWorker extends Worker{
    private String quote;
    private boolean isQuoteGot;
    private ArrayList<Key> keys = new ArrayList<>();

    public CalvinHobbsWorker( Activity activity ) {
        super( activity , "кельвин и хоббс");
        keys.add(new Key("кельвин и хоббс"));
        keys.add(new Key("келвин и хоббс"));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Response doWork( ArrayList<Key> keys, Key arguments ) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isQuoteGot = getQuote();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isQuoteGot) {
            ArrayList<String> images = new ArrayList<>();
            images.add( quote );
            return new Response("", false, images);
        } else {
            return new Response("Не удалось загрузить картинку", false);
        }
    }

    @Override
    public Response getHelp() {
       //TODO
       //instead R.raw.browser_help - must be id of your help
       //return new HelpMan(R.raw.browser_help, activity).getHelp();
        return null;
    }


    public boolean getQuote() throws Exception {
        String line;
        Random random = new Random();
        Integer tmp = Math.abs(random.nextInt(551));
        URL url = new URL("http://calvin-hobbs.ilost.ru/comix.php?num=" + tmp.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                activity.getString(R.string.cp1251)));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains("  <p><img src=")) {
                quote = washQuote(line);
                return true;
            }
        }
        return false;
    }

    private String washQuote( String line ) {
        line = line.replace("  <p><img src=\"","");
        line = line.replaceAll("\"(.)*","");
        line = "http://calvin-hobbs.ilost.ru/" + line;
        return line;
    }
}
