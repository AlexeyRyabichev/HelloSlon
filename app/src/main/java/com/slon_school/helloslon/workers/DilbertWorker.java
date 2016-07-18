package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.TypedValue;

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
 * Created by Volha on 17.07.2016.
 */
public class DilbertWorker extends Worker
{
    private String link1;
    private String link2;
    private boolean isLink1Got;
    private boolean isLink2Got;
    private ArrayList<Key> keys = new ArrayList<Key>();
    private int picPointer;


    ArrayList<String> linkArr;

    public DilbertWorker( Activity activity ) {
        super( activity );
        keys.add(new Key("дилберт"));
        keys.add(new Key("гилберт"));
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
        if(keys.size() != 0) {
            // начать сканировать сайт
            //http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82
            //<li><a href='http://dilbertru.blogspot.ru

            if(keys.get(0).toString().equals("гилберт")) {
                return new Response("Ты хочешь сказать \"Дилберт\"?", true);
            }
            else {
                picPointer = 1;
            }
        }


        if(arguments.get().size() != 0){
            // работать с картинками
            //view-source:http://dilbertru.blogspot.ru/2016/07/20160716.html
            //<img src="http://assets.amuniversal.com

            String comand = arguments.get().get(0);
            if(comand.equals("следующий") || comand.equals("еще") || comand.equals("да")) {

                picPointer++;
                return null;
            }
            if(comand.equals("хватит")) {
                return new Response("Действительно хватит", false);
            }
            if(comand.equals("нет")) {
                return new Response("Ну ладно", false);
            }
        }
        return new Response("DilbertWorkerError", false);
    }


    private ArrayList<String> fillLinks() {
        ArrayList<String> res = new ArrayList<String>();

        boolean isLinkGot;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isLinkGot = getLink("http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82", res);//----------------------------------
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
        if (isLinkGot) {
            return res;
        } else {
            //--------------------------------------------------------------------------------
            return null;
        }

        return res;
    }


/*

    private String getPic() {
        boolean isLinkGot;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isLinkGot = getLink();
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
        if (isLinkGot) {
            return quote;
        } else {
            return "Не удалось загрузить картинку";
        }
    }



*/
public boolean getLink(String URLStr, ArrayList<String> res) throws Exception {//----------------------------------------------------------------
    String line;
        Random random = new Random();
        Integer tmp = random.nextInt(551);

        URL url = new URL(URLStr);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
            activity.getString( R.string.cp1251)));
        while ((line = reader.readLine()) != null) {
            if (line.contains("<li><a href='http://dilbertru.blogspot.ru")) {
                    res.add(washLink1( line ));
                }
            }
        return true;
        }
        return false;
    }
    private String washLink1( String line ) {
        line = line.replace("  <p><img src=\"","");
        line = line.replaceAll("\"(.)*","");
        line = "http://calvin-hobbs.ilost.ru/" + line;
        return line;
    }

    private String washLink2( String line ) {
        line = line.replace("  <p><img src=\"","");
        line = line.replaceAll("\"(.)*","");
        line = "http://calvin-hobbs.ilost.ru/" + line;
        return line;
    }
}
