package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Worker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by I. Dmitry on 10.07.2016.
 */

public class FateBallWorker extends Worker {

    private final static int MODE_GENERAL = 0;
    private final static int MODE_LUCK = 1;
    private final static int MODE_UNRECOGNIZED = 2;
    private ArrayList<String> predictionList = new ArrayList<String>();

    public FateBallWorker(Activity activity) { super(activity); }

    public ArrayList<Key> getKeys() {
        ArrayList<Key> result = new ArrayList<Key>();
        result.add(new Key("шар судьбы"));
        return result;
    }
    
    @Override
    public boolean isContinue() {
        return false;
    }

    private boolean hasKeys(ArrayList<Key> list, Key key) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).contains(key)) return true;
        } return false;
    }

    @Override
    public String doWork(ArrayList<Key> keys, Key arguments) {
        /* Generate keywords
         * Add them to @args
         */
        String keywords = "";
        for (String word : arguments.get()) {
            keywords += word;
            keywords += " ";
        }
        Key args = new Key(keywords);
        /* Create keys:
         * @generalKeys & @luckKeys
         */
        ArrayList<Key> generalKeys = new ArrayList<Key>(3);
        generalKeys.add(new Key("предскажи судьбу"));
        generalKeys.add(new Key("что меня сегодня ждёт"));
        generalKeys.add(new Key("debug"));

        ArrayList<Key> luckKeys = new ArrayList<Key>();
        luckKeys.add(new Key("сегодня удачный день"));
        luckKeys.add(new Key("удача"));
        luckKeys.add(new Key("колбаса"));
        /* Just log,
         * Safe delete
         */
        for (String word : arguments.get()) {
            Log.d("abracadabra", word);
        }
        /* Check keywords
         * According to them fill @predictionList by pattern
         */
        if (hasKeys(generalKeys,args)) {
            initList(MODE_GENERAL);
        } else if(hasKeys(luckKeys,args)) {
            initList(MODE_LUCK);
        } else {
            initList(MODE_UNRECOGNIZED);
        }
        /* Choose random word from @predictionList
         * And return it
         */
        Random randomIndex = new Random();
        return predictionList.get(randomIndex.nextInt(predictionList.size()));
    }
    private void initList(int mode) {
        predictionList.clear();
        if (mode == MODE_GENERAL) {
            predictionList.add("Сегодня тебя ждёт отличный день");
            predictionList.add("А, Что ты говоришь");
            predictionList.add("Не думай о плохом, и всё будек Ок");
            predictionList.add("Пойди, погуляй, может быть, встретишь хороших знакомых");
            predictionList.add("Всё очень туманно, спроси позже");
            predictionList.add("Сегодня твой день");
            predictionList.add("Пока что не могу тебе сказать, извини");
            predictionList.add("По моим источникам Фортуна сегодня тебе благоволит");
            predictionList.add("Не стоит сегодня слишком рисковать");
            predictionList.add("Предсказания звёзд весьма туманны, спроси попозже");
        } else if (mode == MODE_LUCK) {
            predictionList.add("Да");
            predictionList.add("Нет");
            predictionList.add("Не знаю");
            predictionList.add("Возможно");
            predictionList.add("Не очень");
            predictionList.add("Я устал");
        } else if (mode == MODE_UNRECOGNIZED) {
            predictionList.add("Повтори ещё раз, пожалуйста");
            predictionList.add("Не понял тебя, повтори ещё раз");
            predictionList.add("Эээ...что?");
        }
    }
}
