package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;
import com.slon_school.helloslon.handlers.YaLanguage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 1 on 15.07.2016.
 */
public class WeatherWorker extends Worker {

    final private String keyApi= "trnsl.1.1.20160716T141038Z.ded22c490fd9378b.4f9423484132be0cf2a3d5a6d1b94c112bc7fa6e";
    private String output;
    private ArrayList<Key> keys;
    boolean isQuoteGot;

    private String langFrom;
    private String langTo;


    public WeatherWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("переводчик"));
        keys.add(new Key("переведи"));
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
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        return post(arguments.toString());
    }



    private Response post(final String request) {
        output = "";
        isQuoteGot = false;





        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isQuoteGot = getLang(request); //getTranslate(request, "", "");
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
            return new Response(output, false);
        } else {
            Toast.makeText(activity, "bad", Toast.LENGTH_LONG).show();
            return new Response("bad", false);
        }


    }



    public boolean getLang(String request) throws Exception {
        String line;
        String get = "";

        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + keyApi + "&text="+ URLEncoder.encode(request,"UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                "UTF-8"));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            get += line;
        }


        if (!get.equals("")) {


            ObjectMapper mapper = new ObjectMapper();
            YaLanguage language = mapper.readValue(get, YaLanguage.class);
            output = language.lang;

            return true;
        } else {
            return false;
        }


    }


    public boolean getTranslate(String request, String from, String to) throws Exception {
        String line;
        output = "";

        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + keyApi + "&text="+ URLEncoder.encode(request,"UTF-8") +
                "&lang=" + from + "-" + to);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                "UTF-8"));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            output += line;

        }

        return !output.equals("");
    }



    public class YaLanguageText {
        public int code;
        public String lang;
        public ArrayList<String> text;
    }

}
