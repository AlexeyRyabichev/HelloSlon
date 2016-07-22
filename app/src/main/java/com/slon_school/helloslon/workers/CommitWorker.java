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

public class CommitWorker extends Worker implements Helper.additionalInterface {
    private Pair<String,Boolean> pair;
    ArrayList<Key> keys = new ArrayList<>();

    public CommitWorker(Activity activity) {
        super(activity, "commit");
        keys.add(new Key(activity.getString(R.string.commit_keyword0)));
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
            public void run() {
                super.run();
                try {
                    pair = getStringFromWeb(activity.getString(R.string.commit_url),"" ,activity.getString(R.string.cp1251));
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
        String commit = pair.second ? pair.first : activity.getString(R.string.commit_cannot_access);
        return new Response(commit,FINISH_SESSION);
    }
<<<<<<< HEAD

=======
>>>>>>> develop
    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.commit_help, activity).getHelp();
    }
<<<<<<< HEAD
/*
    public boolean getCommit() throws Exception {
        String line;
        URL url = new URL();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), ));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                BTS(16);
                break;
            }
            if (!line.isEmpty()) {
                commit = line;
                return true;
            }
        }
        return false;
    }
    */

=======
>>>>>>> develop
}