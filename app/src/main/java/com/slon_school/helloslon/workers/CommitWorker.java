package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
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
 * Created by Noob_upgraded on 19.07.2016.
 */
public class CommitWorker extends Worker implements Helper.additionalInterface {
    private String commit;
    private boolean hasCommit;

    public CommitWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.commit_keyword0)));
    }

    @Override
    public ArrayList<Key> getKeys() { return keys; }

    @Override
    public boolean isLoop() { return false; }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            public void run() {
                super.run();
                try {
                    hasCommit = getCommit();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    BTS(15);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            BTS(14);
            e.printStackTrace();
        }
        commit = hasCommit ? commit : activity.getString(R.string.commit_cannot_access);
        return new Response(commit,finishSession);
    }

    public boolean getCommit() throws Exception {
        String line;
        URL url = new URL(activity.getString(R.string.commit_url));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), activity.getString(R.string.cp1251)));
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
}