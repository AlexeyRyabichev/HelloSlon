package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Log;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

import static com.slon_school.helloslon.core.Helper.BTS;

/**
 * Created by I. Dmitry on 14.07.2016.
 */
public class HelpWorker extends Worker{
    private final static boolean finishSession = false;
    private final static int FAILED_HELP_RESPONSE = -1;
    private int helpBrowser = FAILED_HELP_RESPONSE;
    private int helpFateBall = FAILED_HELP_RESPONSE;
    private int helpBashOrg = FAILED_HELP_RESPONSE;
    private int helpID;

    private ArrayList<Key> keys = new ArrayList<>();
    private ArrayList<String> helpKeysList = new ArrayList<>();
    private String helpCommand = "";

    public HelpWorker(Activity activity) {
        super(activity);
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    public Response doWork(ArrayList<Key> keys, Key arguments) { return new Response(getHelpKeysList(), finishSession); }

    private void initHelpCommand(final int hid) {
        if (hid == helpBrowser) {
            //TODO strings -> strings.xml
            helpCommand += "";
            helpCommand += "\n";
            helpCommand += getKeywords(hid);
            helpCommand += "";
            helpCommand += "\n";
        } else if (hid == helpBashOrg) {
            helpCommand += activity.getString(R.string.help_bashorg0);
            helpCommand += "\n";
            helpCommand += getKeywords(hid);
            helpCommand += activity.getString(R.string.help_bashorg1);
            helpCommand += "\n";
        } else if (hid == helpFateBall) {
            //TODO it!!!
            helpCommand += "";
            helpCommand += "\n";
            helpCommand += getKeywords(hid);
            helpCommand += "";
            helpCommand += "\n";
        } else {
            BTS(9);
            Log.e("initHelpCommand:","variable hid is equal " + hid);
        }
    }

    private String getKeywords(int hid) {
        String result = "";
        if (hid == helpBrowser) {
            //TODO add all keywords into strings.xml
            result += "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
            result += "\n";
        } else if (hid == helpBashOrg) {
            result += activity.getString(R.string.bashorg_keyword0);
            result += "/";
            result += activity.getString(R.string.bashorg_keyword1);
            result += "/";
            result += activity.getString(R.string.bashorg_keyword2);
            result += "/";
            result += activity.getString(R.string.bashorg_keyword3);
            result += "\n";
        } else if (hid == helpFateBall) {
            result += activity.getString(R.string.fateball_keyword0);
            result += "/";
            result += activity.getString(R.string.fateball_keyword1);
            result += "/";
            result += "\n";
        } else {
            BTS(10);
            Log.e("getKeywords","variable hid is equal " + hid);
        }
        return result;
    }

    private int defineHelpCommand(String args) {
        for (int i = 0; i < helpKeysList.size(); ++i) {
            Integer tmp = i + 1;
            if (args.contains(tmp.toString())) return tmp;
        }
        BTS(11);
        return FAILED_HELP_RESPONSE;
    }

    private void initHelpKeysList() {
        helpKeysList.add(activity.getString(R.string.help_key_browser));
        helpBrowser = helpKeysList.size();
        helpKeysList.add(activity.getString(R.string.help_key_bashorg));
        helpBashOrg = helpKeysList.size();
        helpKeysList.add(activity.getString(R.string.help_key_fateball));
        helpFateBall = helpKeysList.size();
    }

    private String getHelpKeysList() {
        String result =
                activity.getString(R.string.help_get_all0) + "\n" +
                activity.getString(R.string.help_get_all1) + "\n";
        Integer tmp;
        for (int i = 0; i < helpKeysList.size(); ++i) {
            tmp = i + 1;
            result += tmp.toString();
            result += ". ";
            result += helpKeysList.get(i);
            result += "\n";
        }
        result += activity.getString(R.string.help_get_all2);
        result += "\n";
        return result;
    }

    /* TODO:
     * BrowserWorker ++
     * FateBallWorker ++
     * EmailWorker -
     * AlarmWorker ?
     * TownWorker +
     @ BashOrgRandomQuote
     * WeatherWorker ?
     * XKCDRandomComicWorker +
     * SMSWorker ?
     */
}
