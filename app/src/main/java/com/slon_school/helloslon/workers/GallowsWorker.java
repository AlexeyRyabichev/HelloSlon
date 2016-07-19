package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gallow1 on 19.07.2016.
 */
public class GallowsWorker extends Worker {


    private HashMap<Integer, Integer> picsInR;

    private ArrayList<Key> keys;
    private ArrayList<Key> close;

    private enum State {StartGame, InGame, End};
    private State state;

    private String word;
    private int lives;
    private ArrayList<Boolean> haveLet;


    public GallowsWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("Виселица"));

        close = new ArrayList<Key>();
        close.add(new Key("закончить"));
        close.add(new Key("отмена"));
        close.add(new Key("хватит"));
        close.add(new Key("завершить"));


        word = "";
        lives = 0;
        haveLet = new ArrayList<Boolean>();


        picsInR = new HashMap<Integer, Integer>();

        state = State.StartGame;
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

        if (state == State.StartGame) {
            state = State.InGame;
            word = getWord();
            return new Response("Хорошо, давай сыграем, вот твоё слово: \n" + outputWord(), true);
        } else if (state == State.InGame){
            if (isClose(arguments)){
                return close();
            } else {

                if (arguments.size() == 0) {
                    return notUnderstand();
                } else {
                    if (word.contains(arguments.get().get(0)) && (arguments.get().get(0).length() == 1)) {
                        return correct(arguments.get().get(0));
                    } else if (word.contains(arguments.get().get(0)) && (arguments.get().get(0).length() != 1)) {
                        return notUnderstand();
                    } else {
                        return wrong();
                    }
                }


            }
        }

        return null;
    }



    private Response correct(String let) {
            for (int i = 0; i < haveLet.size(); i++) {
                if (let.equals(word.substring(i,i+1))) {
                    haveLet.add(i, true);
                }
            }
            return new Response("Отлично, есть такая буква! \n" + outputWord(), true);
    }


    private Response wrong() {
        lives++;
        if (lives == 11) {
            return new Response("К сожалению, ты проиграл :( \nЭто слово было: " + word, false, getPicture());
        } else {
            ArrayList<String> pic = getPicture();
            if (pic.size() == 0) {
                return new Response("Такой буквы нет \n" + outputWord(), true);
            } else {
                return new Response("Такой буквы нет \n" + outputWord(), true, pic);
            }
        }
    }




    private ArrayList<String> getPicture() {

        if (lives == 0) {
            return new ArrayList<String>();
        } else {

            String path = "drawable://" + String.valueOf(R.drawable.gallow1; // from drawables (non-9patch images)
            //TODO normal get Pic
            return new ArrayList<String>();
        }


    }


    private String outputWord() {
        String toReturn = "";
        for (int i = 0; i < haveLet.size(); i++) {
            if (haveLet.get(i)) {
                toReturn += word.substring(i,i+1) + " ";
            } else {
                toReturn += "_ ";
            }
        }
        return toReturn;
    }



    private String getWord() {
        //TODO норм получение слова здесь нужно
        String toReturn = "привет";



        haveLet.clear();
        for (int i = 0; i < toReturn.length(); i++) {
            if ((i != 0) && (i != toReturn.length() - 1)) {
                haveLet.add(false);
            } else {
                haveLet.add(true);
            }
        }
        return "привет";
    }



    private Response notUnderstand() {

        String toReturn = "Я тебя к сожалению не понял, скажи ещё раз. (Если хочешь закончить игру скажи: ";
        for (int i = 0; i < close.size(); i++) {
            if (i != close.size() - 1)
                toReturn += close.get(i).toString() + ", ";
            else
                toReturn += close.get(i).toString();
        }
        toReturn += ")";

        return new Response( toReturn, true);
    }

    private boolean isClose(Key args) {
        for (Key k : close) {
            if (args.contains(k)) {
                return true;
            }
        }
        return false;
    }

    private Response close() {
        return new Response("Окей, не будем, что дальше?", false);
    }
}
