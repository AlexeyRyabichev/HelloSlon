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

import static com.slon_school.helloslon.core.Helper.BTS;

/**
 * Created by I. Dmitry on 16.07.2016.
 */
public class XKCDRandomComicWorker extends Worker{
    private final static boolean finishSession = false;
    private ArrayList<Key> keys = new ArrayList<Key>();
    private String linkToImage = "";
    private boolean hasImage = false;

    public XKCDRandomComicWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.xkcd_keyword0)));
        keys.add(new Key(activity.getString(R.string.xkcd_keyword1)));
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
            if (line.contains("<img border=0 src=")) {
                linkToImage = washLink(line);
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
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    hasImage = getImage();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    BTS(2);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            BTS(1);
            e.printStackTrace();
        }
        if (hasImage) {
            ArrayList<String> links = new ArrayList<>();
            links.add(linkToImage);
            return new Response("",finishSession,links);
        } else {
            return new Response(activity.getString(R.string.xkcd_failed_image_load), finishSession);
        }
    }
}
