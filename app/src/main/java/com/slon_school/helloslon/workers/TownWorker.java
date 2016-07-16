package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        super( activity );
        Init();
    }

    private void Init() {
        eog = false;
        keys = new ArrayList<Key>();
        used_towns = new HashMap<String, ArrayList<String>>();
        just_started = true;
        lastChar = '0';

        fillKeys( keys, activity );
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
    public Response doWork( ArrayList<Key> keys, Key arguments ) {
        if(eog || (arguments.get().size() == 0))
            Init();

        if ( just_started ) {
            used_towns.get( "п" ).add("пущино" );
            just_started = false;
            lastChar = 'о';
            return new Response( "Пущино", true );
        }
        String str = arguments.get().get( 0 ).toLowerCase().trim();

        // основное правило
        if ( just_started || ( str.charAt( 0 ) == lastChar ) )
            just_started = false;
        else
            return new Response( "Не та буква, ты проиграл", false );

        //проверка города и выбор
        String c = String.valueOf( str.charAt( 0 ) );
        switch ( checkTown(str, activity) ) {
            case 0:
                eog = true;
                return new Response( "Нет такого города, ты проиграл", !eog ); //break;
            case 1:
                eog = true;
                return new Response( "Ты проиграл, такой город уже был", !eog ); //break;
            case 2:
                char _bufChar = ( str.charAt( str.length() - 1 ) );

                String _bufTown = getTown( _bufChar, activity ).trim();

                used_towns.get( c ).add( str );
                used_towns.get( String.valueOf( _bufChar ) ).add( _bufTown.toLowerCase() );

                lastChar = _bufTown.charAt( _bufTown.length() - 1 );

                return new Response( _bufTown, !eog ); //break;
        }
        return new Response( "123error", false );
    }

    private void usedInit() {
        used_towns.clear();
        for ( char c = 'а'; c <= 'я'; c++ ) {
            used_towns.put( String.valueOf( c ), new ArrayList<String>() );
        }
    }

    private void fillKeys( ArrayList<Key> keys, Context context) {
        keys.add( new Key( "игра города" ) );
        keys.add( new Key( "поиграем города" ) );
        keys.add( new Key( "поиграй города" ) );
        keys.add( new Key( "поиграешь города" ) );
        keys.add( new Key( "играть города" ) );
        keys.add( new Key( "города" ) );
    }

    private String getTown( char c, Context context ) {
        Random r = new Random();
        String str = "";
        boolean flag = false;


        RawFileHelper f1 = new RawFileHelper( activity, c );

        while ( !flag ) {
            while ( ( ( str = f1.readln() ) != null ) && !flag || ( used_towns.get( String.valueOf(c) ).contains( str.toLowerCase() ) ))
                if ( r.nextInt( 99 ) + 1 < 15 ) {
                    flag = true;
                }
            if ( !flag ) {
                f1.dispose();
                f1 = new RawFileHelper( activity, c );
            }
        }
        f1.dispose();

        return str;
    }

    private short checkTown( String town, Context context ) {
        town = town.toLowerCase();
        if ( used_towns.get( String.valueOf( town.charAt( 0 ) ) ).contains( town) )
            return 1;


        RawFileHelper f1 = new RawFileHelper(activity, town.charAt( 0 ));
        String str = "";
        while(!(str = f1.readln()).equals( "" )) {
            if(str.toLowerCase().trim().equalsIgnoreCase( town.trim() )) {
                f1.dispose();
                return 2;
            }
        }

        f1.dispose();
        return 0;
    }

//********************************************************************** Helpers ************************************************************************
    public static class RawFileHelper {
        Resources r;
        InputStream is;
        Activity activity;
        final char[] chArr = new char[] {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
        final int[] rawTownResourceIds = new int[] {
                R.raw.town0_new, R.raw.town1_new, R.raw.town2_new,
                R.raw.town3_new, R.raw.town4_new, R.raw.town5_new,
                R.raw.town6_new, R.raw.town7_new, R.raw.town8_new,
                R.raw.town9_new, R.raw.town10_new, R.raw.town11_new,
                R.raw.town12_new, R.raw.town13_new, R.raw.town14_new,
                R.raw.town15_new, R.raw.town16_new, R.raw.town17_new,
                R.raw.town18_new, R.raw.town19_new, R.raw.town20_new,
                R.raw.town21_new, R.raw.town22_new, R.raw.town23_new,
                R.raw.town24_new, R.raw.town25_new, R.raw.town26_new,
                R.raw.town27_new, R.raw.town28_new, R.raw.town29_new,
                R.raw.town30_new, R.raw.town31_new, R.raw.town32_new};


        public RawFileHelper( Activity activity, char c ) {
            this.activity = activity;
            r = activity.getResources();
            is = r.openRawResource( getFileID(c) );
        }

    private int getFileID(char c) {
        int i;
        for(i = 0; (chArr[i] != c) && (i < 33); i++);
        return rawTownResourceIds[i];
    }

        public String readln() {
            String myText = null;
            try {
                myText = convertStreamToString( is );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            return myText;
        }

        private String convertStreamToString( InputStream is ) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((( i = is.read() ) != ( int ) '\n')    &&    (i != -1))
                baos.write( i );
            return baos.toString();
        }

        public void dispose() {
            try {
                is.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

    }
}
