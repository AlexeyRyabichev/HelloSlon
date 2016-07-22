package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Helper;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.Random;

import static com.slon_school.helloslon.core.Helper.isDecimalNumber;
import static com.slon_school.helloslon.core.Helper.string2long;

public class ThrowDiceWorker extends Worker implements Helper.additionalInterface {
    private ArrayList<Key> keys = new ArrayList<>();

    public ThrowDiceWorker(Activity activity) {
        super(activity,"кости");
        keys.add(new Key(activity.getString(R.string.throw_dice_keyword0)));
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
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan(R.raw.throw_dice_help,activity).getHelp();
        }

        String output = "";
        Long diceCount;
        final long DICE_LIMIT = 50;
        final long DEFAULT_DICE_COUNT = 1;
        String args = arguments.toString();

        if (isDecimalNumber(args)) {
            diceCount = string2long(args);
        } else {
            diceCount = DEFAULT_DICE_COUNT;
        }
        diceCount = diceCount > DICE_LIMIT ? DICE_LIMIT : diceCount;
        for (int i = 1; i <= diceCount; ++i) {
            Long randomThrow = new Random().nextLong() % 6 + 1;
            output += "Бросок №" + diceCount.toString() + ": " + randomThrow.toString() + " очков.";
        }
        return new Response(output,FINISH_SESSION);
    }

    @Override
    public Response getHelp() {
        return null;
    }
}
