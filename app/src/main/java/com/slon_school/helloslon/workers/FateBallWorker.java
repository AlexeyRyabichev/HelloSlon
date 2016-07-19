package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Log;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.Random;

import static com.slon_school.helloslon.core.Helper.BTS;

/**
 * Created by I. Dmitry on 10.07.2016.
 */

public class FateBallWorker extends Worker {

    private final static int MODE_GENERAL = 0;
    private final static int MODE_LUCK = 1;
    private final static int MODE_UNRECOGNIZED = 2;
    private final static boolean finishSession = false;
    private ArrayList<String> predictionList = new ArrayList<String>();
    private ArrayList<Key> keys = new ArrayList<Key>();
    private ArrayList<Key> luckKeys = new ArrayList<Key>();
    private ArrayList<Key> generalKeys = new ArrayList<Key>();

    public FateBallWorker(Activity activity) {
        super(activity);
        keys.add(new Key(activity.getString(R.string.fateball_keyword0)));
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    private boolean hasNeededKeys(ArrayList<Key> list, Key key) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).contains(key)) return true;
        } return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        if (arguments.toString().isEmpty()) {
            initList(MODE_UNRECOGNIZED);
            return new Response(predictionList.get(Math.abs(new Random().nextInt() % predictionList.size())),finishSession);
        }

        initKeys(MODE_GENERAL);
        initKeys(MODE_LUCK);

        if (hasNeededKeys(generalKeys,arguments)) {
            initList(MODE_GENERAL);
        } else if(hasNeededKeys(luckKeys,arguments)) {
            initList(MODE_LUCK);
        } else {
            initList(MODE_UNRECOGNIZED);
        }

        return new Response(predictionList.get(Math.abs(new Random().nextInt() % predictionList.size())),finishSession);
    }

    private void initKeys(final int mode) {
        switch(mode) {
            case MODE_GENERAL: {
                generalKeys.add(new Key(activity.getString(R.string.fateball_general_key0)));
                generalKeys.add(new Key(activity.getString(R.string.fateball_general_key1)));
            }
            break;
            case MODE_LUCK: {
                luckKeys.add(new Key(activity.getString(R.string.fateball_luck_key0)));
                luckKeys.add(new Key(activity.getString(R.string.fateball_luck_key1)));
            }
            break;
            default: {
                BTS(7);
                Log.e("Unknown variable value:","FateBallWorker.initKeys.mode == " + mode);
            }
        }
    }

    private void initList(final int mode) {
        predictionList.clear();
        switch(mode) {
            case MODE_GENERAL: {
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
                predictionList.add(activity.getString(R.string.fateball_general_string10));
                predictionList.add(activity.getString(R.string.fateball_general_string11));
            }
            break;
            case MODE_LUCK: {
                predictionList.add(activity.getString(R.string.fateball_luck_string0));
                predictionList.add(activity.getString(R.string.fateball_luck_string1));
                predictionList.add(activity.getString(R.string.fateball_luck_string2));
                predictionList.add(activity.getString(R.string.fateball_luck_string3));
                predictionList.add(activity.getString(R.string.fateball_luck_string4));
                predictionList.add(activity.getString(R.string.fateball_luck_string5));
                predictionList.add(activity.getString(R.string.fateball_luck_string6));
            }
            break;
            case MODE_UNRECOGNIZED: {
                predictionList.add(activity.getString(R.string.fateball_unrecognized_string0));
                predictionList.add(activity.getString(R.string.fateball_unrecognized_string1));
                predictionList.add(activity.getString(R.string.fateball_unrecognized_string2));
                predictionList.add(activity.getString(R.string.fateball_unrecognized_string3));
                predictionList.add(activity.getString(R.string.fateball_unrecognized_string4));
            }
            break;
            default: {
                BTS(8);
                Log.e("Unknown variable value:","FateBallWorker.initList.mode == " + mode);
            }
        }
    }
}
