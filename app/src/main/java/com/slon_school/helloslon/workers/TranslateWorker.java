package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;
import com.slon_school.helloslon.handlers.YaLanguage;
import com.slon_school.helloslon.handlers.YaLanguageText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 1 on 15.07.2016.
 */
public class TranslateWorker extends Worker {

    final private String keyApi= "trnsl.1.1.20160716T141038Z.ded22c490fd9378b.4f9423484132be0cf2a3d5a6d1b94c112bc7fa6e";
    private String output;
    private ArrayList<Key> keys;


    private boolean isQuoteGot;

    private String langFrom;
    private String langTo;
    private String request;


    private enum State {FirstTime, Translate};
    private State state;


    public TranslateWorker(Activity activity) {
        super(activity,"переводчик");
        keys = new ArrayList<Key>();
        keys.add(new Key("переводчик"));
        keys.add(new Key("переведи"));
        keys.add(new Key("перевод"));

        state = State.FirstTime;
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
        //if ((state == State.FirstTime) && (arguments.contains(new Key("помощь")) || arguments.contains(new Key("help"))) ) {
         //   return new HelpMan(R.raw.translate_help, activity).getHelp();
        //} else
        if (state == State.Translate) {
            state = State.FirstTime;

            return post(arguments.toString());
        } else {
            if (arguments.get().size() == 0) {
                state = State.Translate;
                return new Response("Скажи мне текст, который ты хочешь перевести", true);
            } else {
                state = State.FirstTime;

                return post(arguments.toString());
            }
        }
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.translate_help,activity).getHelp();
    }

    private Response post(String request) {
        output = "";
        isQuoteGot = false;

        this.request = request;

        sendRequestLang();
        if (isQuoteGot) {

            sendTranslate();
            if (isQuoteGot) {
                return new Response("Перевод с " + langFrom +
                        " на " + langTo + ": " + request + " - " + output + "\nЯндекс.Переводчик", false);
            } else {
                return new Response("Не удалось перевести", false);
            }

        } else {
            return new Response("Не удалось определить язык", false);
        }
    }

    private void sendRequestLang() {
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

            langFrom = language.lang;
            if (!langFrom.equals("ru")) {
                langTo = "ru";
            } else {
                langTo = "en";
            }

            return true;
        } else {
            return false;
        }
    }

    private void sendTranslate() {
        isQuoteGot = false;

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isQuoteGot = getTranslate(request, langFrom, langTo); //getTranslate(request, "", "");
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

        if (!output.equals("")) {
            ObjectMapper mapper = new ObjectMapper();
            YaLanguageText text = mapper.readValue(output, YaLanguageText.class);
            output = "";

            for (String word : text.text)
               output += word;

            return true;
        } else {
            return false;
        }
    }
}
