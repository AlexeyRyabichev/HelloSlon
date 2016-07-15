package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Noob_upgraded on 14.07.2016.
 */
public class HelpWorker extends Worker{
    final static boolean finishSession = false;
    private ArrayList<Key> keys = new ArrayList<Key>();

    public HelpWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.help_keyword0)));
        keys.add(new Key(activity.getString(R.string.help_keyword1)));
        keys.add(new Key(activity.getString(R.string.help_keyword2)));
        keys.add(new Key(activity.getString(R.string.help_keyword3)));
        keys.add(new Key(activity.getString(R.string.help_keyword4)));
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

        return new Response(
                activity.getString(R.string.help_get_all0) + "\n" +
                activity.getString(R.string.help_get_all1) + "\n" +
                activity.getString(R.string.help_browser) + "\n" +
                activity.getString(R.string.help_get_all2) + "\n",
                finishSession);
    }
    /* TODO:
     * BrowserWorker ++
     * FateBallWorker +
     * EmailWorker -
     * AlarmWorker ?
     * TownWorker -
     * BashOrgRandomQuote +
     */
}
