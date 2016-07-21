package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.slon_school.helloslon.core.Helper.BTS;

public class XKCDRandomComicWorker extends Worker implements Helper.additionalInterface {
    private String linkToImage = "";
    ArrayList<Key> keys = new ArrayList<>();
    private boolean hasImage = false;

    public XKCDRandomComicWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.xkcd_keyword0))); //TODO know how Yandex recognize "XKCD" and change string constant according to this info
    }

    public boolean getImage() throws Exception {
        String line;
        URL url = new URL(activity.getString(R.string.xkcd_url));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), activity.getString(R.string.cp1251)));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains(activity.getString(R.string.xkcd_indicator))) { //TODO check this new stuff
                linkToImage = washLink(line); //TODO implement Helper.isHttpLink method to this link
                return true;
            }
        }
        BTS(3);
        return false;
    }
    private String washLink(String link) {
        link = link.replace("<img border=0 src=\"","");
        link = link.replaceAll("\"(.)*","");
        return link;
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan(R.raw.xkcd_help,activity).getHelp();
        }

        final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    hasImage = getImage();
                    COUNT_DOWN_LATCH.countDown();
                } catch (Exception e) {
                    BTS(2);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            BTS(1);
            e.printStackTrace();
        }
        if (hasImage) {
            ArrayList<String> links = new ArrayList<>();
            links.add(linkToImage);
            return new Response("",FINISH_SESSION,links);
        } else {
            return new Response(activity.getString(R.string.xkcd_failed_image_load), FINISH_SESSION);
        }
    }
}
