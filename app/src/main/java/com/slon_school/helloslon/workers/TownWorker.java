package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Context;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by Volha on 10.07.2016.
 */

public class TownWorker extends Worker {
    boolean eog;
    private ArrayList<Key> keys;
    Map<String, ArrayList<String>> used_towns;

    public TownWorker( Activity activity ) {
        super(activity);
        eog = false;
        keys = new ArrayList<Key>();
        used_towns = new HashMap<String, ArrayList<String>>();

        fillKeys(keys);
        usedInit();
    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isContinue() {
        return true;
    }

    @Override
    public Response doWork( ArrayList<Key> keys, Key arguments) {
        String str = arguments.get().get(0).toLowerCase();

        //arguments.get().size() == 0

        String c = String.valueOf( str.charAt(0) );
        switch ( checkTown(arguments.get().get(0), activity)) {
            case 0:
                eog = true; return new Response("нет такого города", !eog); //break;
            case 1:
                eog = true; return new Response("такой город уже был", !eog); //break;
            case 3:
                String _bufChar = String.valueOf(str.charAt( str.length() ));
                String _bufTown = getTown(_bufChar, activity);
                //used_towns.put(_bufChar, _bufTown);

                return new Response(_bufTown, !eog); //break;
        }
        return new Response("error", false);
    }

    private void usedInit() {
        used_towns.clear();
        for(char c = 'а'; c <= 'я'; c++) {
            used_towns.put(String.valueOf(c), new ArrayList<String>());
        }
    }

    private void fillKeys(ArrayList<Key> keys/*, Context context*/) {
        /*String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("raw/keysTownGame")));
            // читаем содержимое
            while (((str = br.readLine().toLowerCase()) != null))
                keys.add(new Key(str));

            br.close();//--------------------------------------------------------------------------------------------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        keys.add(new Key("игра города"));
        keys.add(new Key("поиграем города"));
        keys.add(new Key("поиграй города"));
        keys.add(new Key("поиграешь города"));
        keys.add(new Key("играть города"));
        keys.add(new Key("города"));
        //comment
    }

    private String getTown(String c, Context context) {
        Random r = new Random();
        String str = "";
        boolean flag = false;
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("raw/" + c)));
            // читаем содержимое
            while(!flag) {
                while ( ( ( str = br.readLine().toLowerCase() ) != null ) && ( used_towns.get( String.valueOf( c.charAt( 0 ) ) ).contains( str ) ) && !flag )
                    if ( r.nextInt( 99 ) + 1 < 15 ) {
                        flag = true;
                    }
                if(!flag) {
                    br.close();//--------------------------------------------------------------------------------------------------------
                    br = new BufferedReader(new InputStreamReader(context.openFileInput("raw/" + c)));
                }
            }
            br.close();//--------------------------------------------------------------------------------------------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    private short checkTown(String town, Context context) {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("raw/")));
            // читаем содержимое
            while (((str = br.readLine().toLowerCase()) != null) && (str != town)) ;

            br.close();//--------------------------------------------------------------------------------------------------------
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
