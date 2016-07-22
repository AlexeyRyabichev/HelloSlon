package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by gallow1 on 19.07.2016.
 */
public class GallowsWorker extends Worker {


    private HashMap<Integer, String> picsInR;

    private ArrayList<Key> keys;
    private ArrayList<Key> close;

    private enum State {StartGame, InGame}
    private State state;

    private String word;
    private int lives;
    private ArrayList<Boolean> haveLet;
    private int countOfCorrect;

    private String output;
    private boolean isQuoteGot;

    public GallowsWorker(Activity activity) {
        super(activity, "игра виселица");
        keys = new ArrayList<>();
        keys.add(new Key("Виселица"));
        keys.add(new Key("Виселицу"));


        close = new ArrayList<>();
        close.add(new Key("закончить"));
        close.add(new Key("отмена"));
        close.add(new Key("хватит"));
        close.add(new Key("завершить"));


        word = "";
        lives = 0;
        haveLet = new ArrayList<>();
        countOfCorrect = 0;

        picsInR = new HashMap<Integer, String>();
        picsInR.put(1,"drawable://" + String.valueOf(R.drawable.gallow1));
        picsInR.put(2,"drawable://" + String.valueOf(R.drawable.gallow2));
        picsInR.put(3,"drawable://" + String.valueOf(R.drawable.gallow3));
        picsInR.put(4,"drawable://" + String.valueOf(R.drawable.gallow4));
        picsInR.put(5,"drawable://" + String.valueOf(R.drawable.gallow5));
        picsInR.put(6,"drawable://" + String.valueOf(R.drawable.gallow6));
        picsInR.put(7,"drawable://" + String.valueOf(R.drawable.gallow7));
        picsInR.put(8,"drawable://" + String.valueOf(R.drawable.gallow8));
        picsInR.put(9,"drawable://" + String.valueOf(R.drawable.gallow9));
        picsInR.put(10,"drawable://" + String.valueOf(R.drawable.gallow10));
        picsInR.put(11,"drawable://" + String.valueOf(R.drawable.gallow11));

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


        //if ((state == State.StartGame) && (arguments.contains(new Key("помощь")) || arguments.contains(new Key("help"))) ) {
          //  return new HelpMan(R.raw.gallow_help, activity).getHelp();
        //} else
        if (state == State.StartGame) {
            state = State.InGame;
            word = getWord();
            countOfCorrect = 0;
            return new Response("Хорошо, давай сыграем, вот твоё слово: \n" + outputWord() + "\n\nПару условий: ь - мягкий знак, ъ - твёрдый знак, ы - мы", true);
        } else if (state == State.InGame){
            if (isClose(arguments)){
                return close();
        } else {

                ArrayList<Key> special = new ArrayList<>();
                special.add(new Key("мягкий"));
                special.add(new Key("твердый"));
                special.add(new Key("мы"));
                if (arguments.size() == 0) {
                    return notUnderstand();
                } else {
                    if (word.contains(arguments.get().get(0)) && (arguments.get().get(0).length() == 1)) {
                        return correct(arguments.get().get(0));
                    } else if (arguments.get().get(0).length() != 1) {
                        if (arguments.contains(special.get(0))) {
                            if (word.contains("ь")) {
                                return correct("ь");
                            } else {
                                return wrong();
                            }
                        } else if (arguments.contains(special.get(1))) {
                            if (word.contains("ъ")) {
                                return correct("ъ");
                            } else {
                                return wrong();
                            }
                        } else if (arguments.contains(special.get(2))) {
                            if (word.contains("ы")) {
                                return correct("ы");
                            } else {
                                return wrong();
                            }
                        } else if (word.contains(arguments.get().get(0).substring(0,1))) {
                            return correct(arguments.get().get(0).substring(0,1));
                        } else {
                           return wrong();
                        }
                    }  else {
                        return wrong();
                    }
                }


            }
        }

        return null;
    }

    @Override
    public Response getHelp() {
        return new HelpMan(R.raw.gallow_help, activity).getHelp();
    }


    private Response correct(String let) {
            for (int i = 0; i < haveLet.size(); i++) {
                if (let.equals(word.substring(i,i+1))) {
                    haveLet.set(i, true);
                    countOfCorrect++;
                }
            }

        if (countOfCorrect == word.length()) {
            state = State.StartGame;
            return new Response("Молодец, ты угадал это слово! Ты победил! :3", false);
        } else {
            return new Response("Отлично, есть такая буква! \n" + outputWord(), true);
        }
    }



    private Response wrong() {
        lives++;
        if (lives == 11) {
            state = State.StartGame;
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
            return new ArrayList<>();
        } else {
            ArrayList<String> pic = new ArrayList<>();
            pic.add(picsInR.get(lives));
            return pic;
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

        String toReturn = "привет";
        getRandomWordForGame();
        if (isQuoteGot) {
            toReturn = output;
        }

        haveLet.clear();
        for (int i = 0; i < toReturn.length(); i++) {
            if (toReturn.substring(i,i+1).equals(toReturn.substring(0,1)) || toReturn.substring(i,i+1).equals(toReturn.substring(toReturn.length() - 1,toReturn.length()))) {
                haveLet.add(true);
            } else {
                haveLet.add(false);
            }
        }

        return toReturn;
    }


    private void getRandomWordForGame() {
        isQuoteGot = false;

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isQuoteGot = getRWord(); //getTranslate(request, "", "");
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        thread.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean getRWord() throws Exception {
        String line;
        String get = "";


        URL url = new URL("http://dalvi.ru/Random.php");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                "windows-1251"));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            get += line;
        }


        if (!get.equals("")) {




            get = get.substring(get.indexOf("<title>") + 7,get.indexOf("</title>"));
            get = get.substring(0, get.indexOf(" ") - 1);
            Key k = new Key(get);
            output = k.get().get(0);
            return true;
        } else {
            return false;
        }


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
