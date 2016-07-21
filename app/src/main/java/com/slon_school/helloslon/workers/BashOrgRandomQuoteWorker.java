package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Pair;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.slon_school.helloslon.core.Helper.getStringFromWeb;

public class BashOrgRandomQuoteWorker extends Worker implements Helper.additionalInterface {
    private String quote;
    Pair<String,Boolean> pair;
    ArrayList<Key> keys = new ArrayList<>();

    public BashOrgRandomQuoteWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.bashorg_keyword0)));
        keys.add(new Key("цитата из башорг"));
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan(R.raw.bashorg_random_quote_help,activity).getHelp();
        }

        final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        pair = getStringFromWeb(activity.getString(R.string.bashorg_url), "div class=\"text", activity.getString(R.string.cp1251));
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
        boolean hasQuote = pair.second;
        quote = pair.first;
        if (hasQuote) {
            washQuote();
        } else {
            Toast.makeText(activity,quote, Toast.LENGTH_LONG).show();
            quote = activity.getString(R.string.bashorg_cannot_access_quote);
        }
        return new Response(quote,FINISH_SESSION);
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
