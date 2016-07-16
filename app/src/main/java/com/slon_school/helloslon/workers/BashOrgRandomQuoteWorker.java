package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.widget.Toast;

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
 * Created by Noob_upgraded on 14.07.2016.
 */
public class BashOrgRandomQuoteWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    private static final boolean finishSession = false;
    private String quote;
    private boolean isQuoteGot;

    public BashOrgRandomQuoteWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.bashorg_keyword0)));
        keys.add(new Key(activity.getString(R.string.bashorg_keyword1)));
        keys.add(new Key(activity.getString(R.string.bashorg_keyword2)));
        keys.add(new Key(activity.getString(R.string.bashorg_keyword3)));
    }

    public boolean getQuote() throws Exception {
        String line;
        URL url = new URL(activity.getString(R.string.bashorg_url));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                activity.getString(R.string.bashorg_cp1251)));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains("<div class=\"text\">")) {
                quote = line;
                return true;
            }
        }
        return false;
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
            washQuote();
            censorQuote();
        } else {
            Toast.makeText(activity,quote, Toast.LENGTH_LONG).show();
            quote = activity.getString(R.string.bashorg_cannot_access_quote);
        }
        return new Response(quote,finishSession);
    }

    private void censorQuote() {
        //TODO Do we need it?
    }

    private void washQuote() {
        quote = quote.replace("<div class=\"text\">","");
        quote = quote.replace("</div>","");
        quote = quote.replace("&quot;","\"");
        quote = quote.replace("&lt;","<");
        quote = quote.replace("&gt;",">");
        quote = quote.replace("&amp;","&");
        quote = quote.replace("<br>", "\n");
        quote = quote.replace("<br />","\n");
    }
}
