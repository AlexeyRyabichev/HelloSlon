package com.slon_school.helloslon.workers;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Mike on 19.07.2016.
 */
public class VkpostWorker extends Worker {

    private boolean isContinue;
    private ArrayList<Key> keys;
    private enum State {Start, Writing, Send}
    private State state;

    public VkpostWorker(Activity activity) {
        super(activity, "вконтакте");
        keys = new ArrayList<Key>();
        keys.add(new Key("пост"));
        keys.add(new Key("вк"));
        keys.add(new Key("вконтакте"));
        keys.add(new Key("запись"));

        state = State.Start;
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
        if (arguments.get().size() == 0 && state == State.Start) {
            return Text(arguments);
        }
        else if (state == State.Writing){

            return writeText(arguments);
        }

        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:" + arguments.toString()));
        intent.setType("text/plain");
        activity.startActivity(intent);
        return new Response("", false);
    }

    @Override
    public Response getHelp() {
        return null;
    }

    private Response writeText(Key arguments){
        state = State.Start;
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, arguments.toString());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
        return new Response("Пост опубликован ", false);
    }

    private Response Text(Key arguments){
        state = State.Writing;
        return new Response("Скажи мне, что ты хочешь отправить",true);
    }

}


