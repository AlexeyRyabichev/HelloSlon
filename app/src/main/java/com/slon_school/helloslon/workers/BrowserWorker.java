package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

public class BrowserWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<>();

    public BrowserWorker(Activity activity) {
        super(activity, "браузер");
        keys.add(new Key(activity.getString(R.string.browser_keyword0)));
        keys.add(new Key(activity.getString(R.string.browser_keyword1)));
        keys.add(new Key(activity.getString(R.string.browser_keyword2)));
        keys.add(new Key(activity.getString(R.string.browser_keyword3)));
        keys.add(new Key(activity.getString(R.string.browser_keyword4)));
        keys.add(new Key(activity.getString(R.string.browser_keyword5)));
        keys.add(new Key(activity.getString(R.string.browser_keyword6)));
        keys.add(new Key(activity.getString(R.string.browser_keyword_google)));
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop(){ return false; }

    @Override
    public Response doWork(ArrayList<Key> result, Key arguments) {
        /*if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan(R.raw.browser_help,activity).getHelp();
        }*/

        if (result.contains(new Key(activity.getString(R.string.browser_keyword_google)))){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(activity.getString(R.string.browser_google) + arguments.toString()));
            activity.startActivity(intent);
            return new Response("", false);
        }
        else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(activity.getString(R.string.browser_yandex) + arguments.toString()));
            activity.startActivity(intent);
            return new Response("", false);
        }
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.browser_help,activity).getHelp();
    }

}