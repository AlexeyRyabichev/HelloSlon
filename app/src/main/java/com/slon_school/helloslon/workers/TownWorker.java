package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    boolean just_started;
    char lastChar;

    public TownWorker( Activity activity ) {
        super(activity);
        eog = false;
        keys = new ArrayList<Key>();
        used_towns = new HashMap<String, ArrayList<String>>();
        just_started = true;
        lastChar = '0';


        fillKeys(keys);
        usedInit();
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
    public Response doWork( ArrayList<Key> keys, Key arguments) {
        Toast.makeText(activity, "here", Toast.LENGTH_LONG).show();
        return new Response(helper1('а', activity), false);/*
        String str = arguments.get().get(0).toLowerCase();

        if(str.equals("")) {
            used_towns.get("п").add("пущино");
            return new Response("Пущино", true );//
        }

        // основное правило
        if(just_started || (str.charAt(0) == lastChar))
            just_started = false;
        else
            return new Response("не та буква", false);

        //проверка города и выбор
        String c = String.valueOf( str.charAt(0) );
        switch ( checkTown(str, activity)) {
            case 0:
                eog = true; return new Response("нет такого города", !eog); //break;
            case 1:
                eog = true; return new Response("такой город уже был", !eog); //break;
            case 2:
                char _bufChar = (str.charAt( str.length()-1 ));
                String _bufTown = getTown(_bufChar, activity);
                Toast.makeText(activity, "here", Toast.LENGTH_LONG).show();
                // add used towns
                used_towns.get(c).add(str);
                used_towns.get(String.valueOf(_bufChar)).add(_bufTown.toLowerCase());

                lastChar = _bufTown.charAt( _bufTown.length()-1 );

                return new Response(_bufTown, !eog); //break;
        }
        return new Response("123error", false);*/
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

    private String getTown(char c, Context context) {
        Random r = new Random();
        String str = "";
        boolean flag = false;
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("raw\\town" + String.valueOf((int)c - (int)'а'))));
            // читаем содержимое
            while(!flag) {
                while ( ( ( str = br.readLine()) != null ) /*&& ( used_towns.get( String.valueOf(c) ).contains( str.toLowerCase() ) )*/ && !flag )
                    if ( r.nextInt( 99 ) + 1 < 15 ) {
                        flag = true;
                        Toast.makeText(activity, "here2", Toast.LENGTH_LONG).show();
                        Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
                    }
                if(!flag) {
                    br.close();//--------------------------------------------------------------------------------------------------------
                    br = new BufferedReader(new InputStreamReader(context.openFileInput("raw\\town" + String.valueOf((int)c - (int)'а'))));
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
        char c = town.toLowerCase().charAt( 0 );
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("raw\\town" + String.valueOf((int)c - (int)'а'))));
            // читаем содержимое
            while (((str = br.readLine()) != null) && (!str.toLowerCase().equals( town ) )) ;

            br.close();//--------------------------------------------------------------------------------------------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(str == null) return 0;
        if(used_towns.get(String.valueOf(town.toLowerCase().charAt(0))).contains(str.toLowerCase())) return 1;
        return 2;
    }

    private String  getStringFromRawFile(Activity activity) {
        Resources r = activity.getResources();
        InputStream is = r.openRawResource( R.raw.test);
        String myText = null;
        try {
            myText = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  myText;
    }

    private String  convertStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while( i != -1)
        {
            baos.write(i);
            i = is.read();
        }
        return  baos.toString();
    }


    String helper1(char c, Context context) {
        String line = "123456";
        try {
            InputStream inputStream = context.openFileInput("raw\\town0");

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();

                /*while ((line = reader.readLine()) != null) {
                }*/
                line = reader.readLine();

                inputStream.close();
            }
        } catch (Throwable t) {
            return "1234error";
        }

        return line;
    }
}
