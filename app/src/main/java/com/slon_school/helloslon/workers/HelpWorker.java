package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Core;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by 1 on 21.07.2016.
 */
public class HelpWorker extends Worker {

    private ArrayList<Key> keys;
    private ArrayList<Key> close;

    private enum State {NotInHelp, Choose, InHelp};
    private State state;

    private Core core;

    public HelpWorker(Activity activity, Core core) {
        super(activity, "helper");

        keys = new ArrayList<Key>();
        keys.add(new Key("помощь"));
        keys.add(new Key("help"));
        keys.add(new Key("помоги"));
        keys.add(new Key("помогите"));
        keys.add(new Key("sos"));
        keys.add(new Key("сос"));

        close = new ArrayList<Key>();
        close.add(new Key("хватит"));
        close.add(new Key("закрой"));
        close.add(new Key("закройся"));
        close.add(new Key("отмена"));
        close.add(new Key("выход"));


        this.core = core;

        state = State.NotInHelp;
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
        if (isClose(arguments)) {
            return close();
        }

        if (state == State.NotInHelp) {
            state = State.Choose;
            return new Response("Вот всё что я умею, о чём ты хочешь спросить? \n" + listOfHelps(),true);
        } else if (state == State.Choose) {
            state = State.NotInHelp;
            return new Response(getHelp(arguments) +"\n\nПро что ещё ты хочешь спросить? Или же мы можем закончить, если ты скажешь: " + words(close), true);
        }

        return null;
    }

    @Override
    public Response getHelp() {
        return new Response("none", false);
    }


    private String listOfHelps() {
        String toReturn = "";
        for (Worker w : core.workers()) {
            toReturn += "- " + w.getName() + "\n";
        }
        return toReturn;
    }


    private String getHelp(Key arguments) {
        for (Worker w : core.workers()) {
            if (arguments.contains(new Key(w.getName()))) {
                return w.getHelp().getResponse();
            }
        }
        return "Я такого не умею, не понимаю тебя.";
    }


    private boolean isClose(Key arguments) {
        for (Key cl : close) {
            if (arguments.contains(cl)) {
                return true;
            }
        }
        return false;
    }


    private Response close() {
        return new Response("Хорошо, что дальше?",false);
    }

    private String words(ArrayList<Key> array) {
        String toReturn = "";
        for (int i = 0; i < array.size(); i++) {
            if (i != array.size() - 1) {

                for (int j = 0; j < array.get(i).get().size(); i++) {
                    if (j != array.get(i).get().size() - 1) {
                        toReturn += array.get(i).get().get(j) + " ";
                    } else {
                        toReturn += array.get(i).get().get(j);
                    }
                }

                toReturn += ", ";
            } else {
                for (int j = 0; j < array.get(i).get().size(); i++) {
                    if (j != array.get(i).get().size() - 1) {
                        toReturn += array.get(i).get().get(j) + " ";
                    } else {
                        toReturn += array.get(i).get().get(j);
                    }
                }

                toReturn += ".";
            }
        }
        return toReturn;
    }
}
