package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Log;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.Random;

public class FateBallWorker extends Worker implements Helper.additionalInterface {

    private final static int MODE_GENERAL = 0;
    private final static int MODE_LUCK = 1;
    private final static int MODE_UNRECOGNIZED = 2;
    private ArrayList<String> predictionList = new ArrayList<>();
    private ArrayList<Key> luckKeys = new ArrayList<>();
    private ArrayList<Key> generalKeys = new ArrayList<>();
    ArrayList<Key> keys = new ArrayList<>();

    public FateBallWorker(Activity activity) {
        super(activity, "шар судьбы");
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
            return new Response(predictionList.get(Math.abs(new Random().nextInt() % predictionList.size())),FINISH_SESSION);
        } else if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return getHelp();
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

        return new Response(predictionList.get(Math.abs(new Random().nextInt() % predictionList.size())),FINISH_SESSION);
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.fateball_help, activity).getHelp();
    }

    private void initKeys(final int MODE) {
        switch(MODE) {
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
                Log.e("Unknown variable value:","FateBallWorker.initKeys.mode == " + MODE);
            }
        }
    }

    private void initList(final int MODE) {
        predictionList.clear();
        switch(MODE) {
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
                Log.e("Unknown variable value:","FateBallWorker.initList.mode == " + MODE);
            }
        }
    }
}
