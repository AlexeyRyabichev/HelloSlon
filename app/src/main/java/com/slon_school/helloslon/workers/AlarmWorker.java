package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;
import com.slon_school.helloslon.handlers.AlarmManagerBroadcastReceiver;

import java.util.ArrayList;

/**
 * Created by 1 on 12.07.2016.
 */
public class AlarmWorker extends Worker {

    private ArrayList<Key> keys;
    private AlarmManagerBroadcastReceiver alarm;

    public AlarmWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("будильник"));

        alarm = new AlarmManagerBroadcastReceiver();

    }

    private void startRepeatingTimer() {
        if (alarm != null) {
            alarm.setAlarm(activity);
        } else {
            Toast.makeText(activity,"Alarm is null",Toast.LENGTH_LONG).show();
        }
    }

    private void cancelRepeatingTimer() {
        if (alarm != null) {
            alarm.cancelAlarm(activity);
        } else {
            Toast.makeText(activity,"Alarm is null",Toast.LENGTH_LONG).show();
        }
    }

    private void onetimeTimer() {
        if (alarm != null) {
            alarm.setOnetimeTimer(activity);
        } else {
            Toast.makeText(activity,"Alarm is null",Toast.LENGTH_LONG).show();
        }
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

        Response response = new Response("????",false);

        ArrayList<Key> alarmArguments = new ArrayList<Key>();
        alarmArguments.add(new Key("включи"));
        alarmArguments.add(new Key("выключи"));

        if (arguments.contains(alarmArguments.get(0))) {
            cancelRepeatingTimer();
            response = new Response("Установлен одноразовый будильник",false);
        } else if (alarmArguments.contains(alarmArguments.get(1))){
            onetimeTimer();
            response = new Response("Отменён будильник",false);
        }

        return response;
    }

}
