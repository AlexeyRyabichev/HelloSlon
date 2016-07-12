package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Toast;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;
import com.slon_school.helloslon.handlers.AlarmManagerBroadcastReceiver;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 1 on 12.07.2016.
 */
public class AlarmWorker extends Worker {

    private ArrayList<Key> keys;

    public AlarmWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("будильник"));

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

        Response response = new Response("????", false);

        ArrayList<Key> alarmArguments = new ArrayList<Key>();
        alarmArguments.add(new Key("включи"));
        alarmArguments.add(new Key("выключи"));


        Toast.makeText(activity, arguments.toString(), Toast.LENGTH_LONG).show();


        if (arguments.contains(alarmArguments.get(0))) {
            setOneTimeAlarm(25);

            response = new Response("Установлен одноразовый будильник", false);
        } else if (alarmArguments.contains(alarmArguments.get(1))) {

            //onetimeTimer();
            response = new Response("Отменён будильник", false);
        }

        return response;
    }


    private void setOneTimeAlarm(int time) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "HelloSlon");
        i.putExtra(AlarmClock.EXTRA_HOUR, 11);
        i.putExtra(AlarmClock.EXTRA_MINUTES, 38);
        activity.startActivity(i);
    }


}
