package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Pair;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.slon_school.helloslon.core.Helper.getStringFromWeb;
import static com.slon_school.helloslon.core.Helper.isHttpLink;

public class XKCDRandomComicWorker extends Worker implements Helper.additionalInterface {
    ArrayList<Key> keys = new ArrayList<>();
    private Pair<String,Boolean> pair;
    public XKCDRandomComicWorker(Activity activity) {
        super(activity, "комиксы");
        keys.add(new Key(activity.getString(R.string.xkcd_keyword0))); //TODO know how Yandex recognize "XKCD" and change string constant according to this info
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
            return getHelp();
        }

        final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    pair = getStringFromWeb(activity.getString(R.string.xkcd_url),activity.getString(R.string.xkcd_indicator),activity.getString(R.string.cp1251));
                    COUNT_DOWN_LATCH.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean hasImage = pair.second;
        String linkToImage = washLink(pair.first);
        if (hasImage && isHttpLink(linkToImage)) {
            ArrayList<String> links = new ArrayList<>();
            links.add(linkToImage);
            return  new Response("",FINISH_SESSION,links);
        } else {
            return new Response(activity.getString(R.string.xkcd_failed_image_load), FINISH_SESSION);
        }
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.xkcd_help, activity).getHelp();
    }
}
