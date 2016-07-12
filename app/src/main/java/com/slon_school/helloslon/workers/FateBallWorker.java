package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by I. Dmitry on 10.07.2016.
 */

public class FateBallWorker extends Worker {

    private ArrayList<Key> keys = new ArrayList<Key>;
    private final static int MODE_GENERAL = 0;
    private final static int MODE_LUCK = 1;
    private final static int MODE_UNRECOGNIZED = 2;
    private ArrayList<String> predictionList = new ArrayList<String>();

    public FateBallWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.fateballkeyword0)));
        keys.add(new Key(activity.getString(R.string.fateballkeyword1)));
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }
    
    @Override
    public boolean isContinue() {
        return false;
    }

    private boolean hasKeys(ArrayList<Key> list, Key key) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).contains(key)) return true;
        } return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Random randomIndex = new Random();
        if (arguments.toString().isEmpty()) {
            initList(MODE_UNRECOGNIZED);
            return new Response(predictionList.get(randomIndex.nextInt(predictionList.size())),false);
        }
        /* Create keys:
         * @generalKeys & @luckKeys
         */
        ArrayList<Key> generalKeys = new ArrayList<Key>();
        generalKeys.add(new Key(activity.getString(R.string.fateballgeneralkey0)));
        generalKeys.add(new Key(activity.getString(R.string.fateballgeneralkey1)));
        generalKeys.add(new Key(activity.getString(R.string.fateballgeneralkey2)));

        ArrayList<Key> luckKeys = new ArrayList<Key>();
        luckKeys.add(new Key(activity.getString(R.string.fateballluckkey0)));
        luckKeys.add(new Key(activity.getString(R.string.fateballluckkey1)));
        /* Check keywords
         * According to them fill @predictionList by pattern
         */
        if (hasKeys(generalKeys,arguments)) {
            initList(MODE_GENERAL);
        } else if(hasKeys(luckKeys,arguments)) {
            initList(MODE_LUCK);
        } else {
            initList(MODE_UNRECOGNIZED);
        }
        /* Choose random word from @predictionList
         * And return it
         */
        return new Response(predictionList.get(randomIndex.nextInt(predictionList.size())),false);
    }
    private void initList(int mode) {
        predictionList.clear();
        if (mode == MODE_GENERAL) {
            predictionList.add(activity.getString(R.string.fateballgeneralstring0));
            predictionList.add(activity.getString(R.string.fateballgeneralstring1));
            predictionList.add(activity.getString(R.string.fateballgeneralstring2));
            predictionList.add(activity.getString(R.string.fateballgeneralstring3));
            predictionList.add(activity.getString(R.string.fateballgeneralstring4));
            predictionList.add(activity.getString(R.string.fateballgeneralstring5));
            predictionList.add(activity.getString(R.string.fateballgeneralstring6));
            predictionList.add(activity.getString(R.string.fateballgeneralstring7));
            predictionList.add(activity.getString(R.string.fateballgeneralstring8));
            predictionList.add(activity.getString(R.string.fateballgeneralstring9));
        } else if (mode == MODE_LUCK) {
            predictionList.add(activity.getString(R.string.fateballluckstring0));
            predictionList.add(activity.getString(R.string.fateballluckstring1));
            predictionList.add(activity.getString(R.string.fateballluckstring2));
            predictionList.add(activity.getString(R.string.fateballluckstring3));
            predictionList.add(activity.getString(R.string.fateballluckstring4));
            predictionList.add(activity.getString(R.string.fateballluckstring5));
        } else if (mode == MODE_UNRECOGNIZED) {
            predictionList.add(activity.getString(R.string.fateballunrecognizedstring0));
            predictionList.add(activity.getString(R.string.fateballunrecognizedstring1));
            predictionList.add(activity.getString(R.string.fateballunrecognizedstring2));
        }
    }
}
