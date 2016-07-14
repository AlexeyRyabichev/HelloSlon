package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by 1 on 12.07.2016.
 */
public class AlarmWorker extends Worker {

    private ArrayList<Key> keys;
    private HashMap<HSTime, Integer> alarms;
    private enum State {FirstTime, SetTime, SetIsLoop};
    private boolean deleteAlarm;
    private HSTime timeNow;


    private State state;

    public AlarmWorker(Activity activity) {
        super(activity);
        alarms = new HashMap<HSTime, Integer>();

        keys = new ArrayList<Key>();
        keys.add(new Key("будильник"));


        deleteAlarm = false;
        state = State.FirstTime;
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return true;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        Response response = new Response("Я не понял, что ты имеешь ввиду, скажи поточнее", true);

        if (state == State.FirstTime) {
            ArrayList<Key> argumentsToOnAlarm = new ArrayList<Key>();
            argumentsToOnAlarm.add(new Key("поставь"));
            argumentsToOnAlarm.add(new Key("установи"));
            argumentsToOnAlarm.add(new Key("установить"));

            ArrayList<Key> argumentsToOffAlarm = new ArrayList<Key>();
            argumentsToOffAlarm.add(new Key("выключи"));
            argumentsToOffAlarm.add(new Key("выключить"));
            argumentsToOffAlarm.add(new Key("отмени"));

            for ( Key key : argumentsToOffAlarm ) {
                if ( arguments.contains(key) ) {
                    state = State.SetTime;
                    deleteAlarm = true;
                    return new Response("На какое время он был установлен?", true);
                }
            }




            for ( Key key : argumentsToOnAlarm ) {
                if ( arguments.contains(key) ) {
                    state = State.SetTime;
                    deleteAlarm = false;
                    return new Response("Установи время", true);
                }
            }

            return new Response("Я не понял, что ты имеешь ввиду, скажи поточнее", true);

        } else if (state == State.SetTime) {

            if ( deleteAlarm ) {
                state = State.FirstTime;
                deleteAlarm = false;
                timeNow = HSTime.parseTime(arguments);
                cancelTimeAlarm(timeNow);
                return new Response("Удалён будильник на " + timeNow.hour + ":" + timeNow.minutes, false);
            } else {
                state = State.SetIsLoop;
                deleteAlarm = false;
                timeNow = HSTime.parseTime(arguments);
                return new Response("Одноразовый или многоразовый?", true);
            }

        } else if (state == State.SetIsLoop) {

            ArrayList<Key> args = new ArrayList<Key>();
            args.add(new Key("одноразовый"));
            args.add(new Key("многоразовый"));

            if (arguments.contains(args.get(0))) {
                state = State.FirstTime;
                deleteAlarm = false;
                setOneTimeAlarm(timeNow);
                return new Response("Установлен одноразовый будильник на " + timeNow.hour + ":" + timeNow.minutes, false);
            } else if (arguments.contains(args.get(1))) {
                state = State.FirstTime;
                deleteAlarm = false;
                setRepeatTimeAlarm(timeNow);
                return new Response("Установлен многоразовый будильник на " + timeNow.hour + ":" + timeNow.minutes, false);
            } else {
                return new Response("Я не понял, повтори пожалуйстаы", true);
            }
        }

        return response;
    }


    private void setOneTimeAlarm(HSTime time) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "HelloSlon");
        i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_TIME);
        i.putExtra(AlarmClock.EXTRA_IS_PM, true);
        i.putExtra(AlarmClock.EXTRA_HOUR, time.hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, time.minutes);

        alarms.put(time,alarms.size() + 1);
        i.putExtra("id", alarms.get(alarms.size()));

        activity.startActivity(i);
    }

    private void setRepeatTimeAlarm(HSTime time) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "HelloSlon");
        i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_TIME);
        i.putExtra(AlarmClock.EXTRA_IS_PM, true);
        i.putExtra(AlarmClock.EXTRA_HOUR, time.hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, time.minutes);

        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.MONDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.TUESDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.WEDNESDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.THURSDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.FRIDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.SATURDAY);
        i.putExtra(AlarmClock.EXTRA_DAYS, Calendar.SUNDAY);


        alarms.put(time,alarms.size() + 1);
        i.putExtra("id", alarms.get(alarms.size()));

        activity.startActivity(i);
    }

    private void cancelTimeAlarm(HSTime time){
        Intent i = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
        i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_ALL);
        i.putExtra(AlarmClock.EXTRA_IS_PM, true);
        i.putExtra(AlarmClock.EXTRA_HOUR, time.hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, time.minutes);

        i.putExtra("id", alarms.get(time));

        activity.startActivity(i);
    }

}

class HSTime {
    public int hour;
    public int minutes;

    static public HSTime parseTime(Key args) {
            HSTime time = new HSTime();

            String hour = args.get().get(0).substring(0,args.get().get(0).indexOf(":"));
            String minute = args.get().get(0).substring(args.get().get(0).indexOf(":") + 1,args.get().get(0).length());

            time.hour = Integer.valueOf(hour);
            time.minutes = Integer.valueOf(minute);
            return time;
    }

}