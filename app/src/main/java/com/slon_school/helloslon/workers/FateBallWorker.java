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

    private final static int MODE_GENERAL = 0;
    private final static int MODE_LUCK = 1;
    private final static int MODE_UNRECOGNIZED = 2;
    private ArrayList<String> predictionList = new ArrayList<String>();
    private ArrayList<Key> keys = new ArrayList<Key>();
    private final static boolean finishSession = false;

    public FateBallWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.fateball_keyword0)));
        keys.add(new Key(activity.getString(R.string.fateball_keyword1)));
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    //TODO delete THIS shit
    @Override
    public boolean isLoop() {
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
            return new Response(predictionList.get(randomIndex.nextInt(predictionList.size())),finishSession);
        }
        /* Create keys:
         * @generalKeys & @luckKeys
         */
        ArrayList<Key> generalKeys = new ArrayList<Key>();
        generalKeys.add(new Key(activity.getString(R.string.fateball_general_key0)));
        generalKeys.add(new Key(activity.getString(R.string.fateball_general_key1)));
        generalKeys.add(new Key(activity.getString(R.string.fateball_general_key2)));

        ArrayList<Key> luckKeys = new ArrayList<Key>();
        luckKeys.add(new Key(activity.getString(R.string.fateball_luck_key0)));
        luckKeys.add(new Key(activity.getString(R.string.fateball_luck_key1)));
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
        return new Response(predictionList.get(randomIndex.nextInt(predictionList.size())),finishSession);
    }

    private void initList(int mode) {
        predictionList.clear();
        if (mode == MODE_GENERAL) {
            predictionList.add(activity.getString(R.string.fateball_general_string0));
            predictionList.add(activity.getString(R.string.fateball_general_string1));
            predictionList.add(activity.getString(R.string.fateball_general_string2));
            predictionList.add(activity.getString(R.string.fateball_general_string3));
            predictionList.add(activity.getString(R.string.fateball_general_string4));
            predictionList.add(activity.getString(R.string.fateball_general_string5));
            predictionList.add(activity.getString(R.string.fateball_general_string6));
            predictionList.add(activity.getString(R.string.fateball_general_string7));
            predictionList.add(activity.getString(R.string.fateball_general_string8));
            predictionList.add(activity.getString(R.string.fateball_general_string9));
        } else if (mode == MODE_LUCK) {
            predictionList.add(activity.getString(R.string.fateball_luck_string0));
            predictionList.add(activity.getString(R.string.fateball_luck_string1));
            predictionList.add(activity.getString(R.string.fateball_luck_string2));
            predictionList.add(activity.getString(R.string.fateball_luck_string3));
            predictionList.add(activity.getString(R.string.fateball_luck_string4));
            predictionList.add(activity.getString(R.string.fateball_luck_string5));
        } else if (mode == MODE_UNRECOGNIZED) {
            predictionList.add(activity.getString(R.string.fateball_unrecognized_string0));
            predictionList.add(activity.getString(R.string.fateball_unrecognized_string1));
            predictionList.add(activity.getString(R.string.fateball_unrecognized_string2));
        }
    }
}
