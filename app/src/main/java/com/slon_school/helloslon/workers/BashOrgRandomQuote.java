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

/**
 * Created by Noob_upgraded on 14.07.2016.
 */
public class BashOrgRandomQuote extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    private static final boolean finishSession = false;
    private String quote;

    public BashOrgRandomQuote(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.bashorg_keyword0)));
        keys.add(new Key(activity.getString(R.string.bashorg_keyword1)));
        keys.add(new Key(activity.getString(R.string.bashorg_keyword2)));
    }

    public boolean getQuote() throws Exception {
            URL url = new URL(activity.getString(R.string.bashorg_url));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream(), activity.getString(R.string.bashorg_cp1251)));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
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
        boolean isQuoteGot = false;
        try {
            isQuoteGot = getQuote();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isQuoteGot) {
            washQuote();
            censorQuote();
        } else {
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
