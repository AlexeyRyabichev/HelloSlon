package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.widget.Toast;

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

/**
 * Created by I. Dmitry on 14.07.2016.
 */

public class BashOrgRandomQuoteWorker extends Worker implements Helper.additionalInterface {
    //private ArrayList<Key> keys = new ArrayList<Key>();
    //private static final boolean finishSession = false;
    private String quote;
    private boolean hasQuote;
    //TODO Check it
    public BashOrgRandomQuoteWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.bashorg_keyword0)));
    }

    public boolean getQuote() throws Exception {
        String line;
        URL url = new URL(activity.getString(R.string.bashorg_url));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), activity.getString(R.string.cp1251)));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                BTS(4);
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
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan("BashOrgRandomQuoteWorker",activity).getHelp();
        }

        final CountDownLatch countDownLatch = new CountDownLatch(1);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        hasQuote = getQuote();
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        BTS(5);
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            BTS(6);
            e.printStackTrace();
        }
        if (hasQuote) {
            washQuote();
        } else {
            Toast.makeText(activity,quote, Toast.LENGTH_LONG).show();
            quote = activity.getString(R.string.bashorg_cannot_access_quote);
        }
        return new Response(quote,finishSession);
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
