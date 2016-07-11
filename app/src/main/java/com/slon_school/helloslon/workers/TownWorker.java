package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import 	android.content.Context;


/**
 * Created by Volha on 10.07.2016.
 */

public class TownWorker extends Worker {
    boolean eog;
    ArrayList<String> keys;
    Map<String, ArrayList<String>> used_towns;

    public TownWorker( Activity activity ) {
        super(activity);
        eog = false;
        keys = new ArrayList<String>();
        used_towns = new HashMap<String, ArrayList<String>>();

        fillKeys(keys);
        usedInit(used_towns);
    }

    @Override
    public ArrayList<String> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue() {
        return !eog;
    }

    @Override
    public String doWork( ArrayList<String> arguments ) {
        String c = String.valueOf( arguments.get(0).charAt(0) );

        return null;
    }

    private void usedInit(Map<String, ArrayList<String>> used_towns) {
        //used_towns.clear();
        for(char c = 'А'; c <= 'Я'; c++) {
            used_towns.put(String.valueOf(c), new ArrayList<String>());
        }
    }

    private void fillKeys(ArrayList<String> keys, Context context) {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("FILENAME")));
            // читаем содержимое
            while (((str = br.readLine()) != null))
                keys.add(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTown(String c, Context context) {
        Random r = new Random();
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("FILENAME" + c)));
            // читаем содержимое
            //while (((str = br.readLine()) != null) && (str != town)) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private short checkTown(String town, Context context) {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("FILENAME")));
            // читаем содержимое
            while (((str = br.readLine()) != null) && (str != town)) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(str == null) return 0;
        if(used_towns.get(String.valueOf(town.charAt(0))).contains(str)) return 1;
        return 2;
    }
}
