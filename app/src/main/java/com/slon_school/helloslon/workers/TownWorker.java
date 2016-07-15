package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

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
        eog = false;
        keys = new ArrayList<Key>();
        used_towns = new HashMap<String, ArrayList<String>>();
        just_started = true;
        lastChar = '0';


        fillKeys( keys );
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
        //Toast.makeText( activity, "here", Toast.LENGTH_LONG ).show();
        //for ( char c = 'а'; c <= 'я'; c++ ) {
        //   Toast.makeText( activity, getTown( c, activity ), Toast.LENGTH_SHORT ).show();
        //}

        if ( arguments.get().size() == 0 ) {
            used_towns.get( "п" ).add( "пущино" );
            return new Response( "Пущино", true );//
        }
        String str = arguments.get().get( 0 ).toLowerCase().trim();


        // основное правило
        if ( just_started || ( str.charAt( 0 ) == lastChar ) )
            just_started = false;
        else
            return new Response( "не та буква", false );

        //проверка города и выбор
        String c = String.valueOf( str.charAt( 0 ) );
        switch (/* checkTown(str, activity)*/ 2 ) {
            case 0:
                eog = true;
                return new Response( "нет такого города", !eog ); //break;
            case 1:
                eog = true;
                return new Response( "такой город уже был", !eog ); //break;
            case 2:
                char _bufChar = ( str.charAt( str.length() - 1 ) );
                //_bufChar++;
                //_bufChar++;
                String _bufTown = getTown( _bufChar, activity ).trim();
                //Toast.makeText(activity, "here", Toast.LENGTH_LONG).show();
                // add used towns
                used_towns.get( c ).add( str );
                used_towns.get( String.valueOf( _bufChar ) ).add( _bufTown.toLowerCase() );

                lastChar = _bufTown.charAt( _bufTown.trim().length() - 1 );

                Toast.makeText( activity, lastChar + "", Toast.LENGTH_LONG ).show();
                //lastChar--;
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

    private void fillKeys( ArrayList<Key> keys/*, Context context*/ ) {
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

        keys.add( new Key( "игра города" ) );
        keys.add( new Key( "поиграем города" ) );
        keys.add( new Key( "поиграй города" ) );
        keys.add( new Key( "поиграешь города" ) );
        keys.add( new Key( "играть города" ) );
        keys.add( new Key( "города" ) );
        //comment
    }

    private String getTown( char c, Context context ) {
        /*rawFileHelper f1 = new rawFileHelper(activity, 'а');
        String res = f1.getStringFromRawFile();
        res = f1.getStringFromRawFile();
        f1.dispose();
        return new Response(res, false );*/
        Random r = new Random();
        String str = "";
        boolean flag = false;

        // открываем поток для чтения
        //Toast.makeText( activity, c+"", Toast.LENGTH_SHORT ).show();
        RawFileHelper f1 = new RawFileHelper( activity, c );
        // читаем содержимое
        while ( !flag ) {
            while ( ( ( str = f1.readln() ) != null ) /*&& ( used_towns.get( String.valueOf(c) ).contains( str.toLowerCase() ) )*/ && !flag )
                if ( r.nextInt( 99 ) + 1 < 15 ) {
                    flag = true;
                }
            if ( !flag ) {
                f1.dispose();//--------------------------------------------------------------------------------------------------------
                f1 = new RawFileHelper( activity, c );
            }
        }
        f1.dispose();//--------------------------------------------------------------------------------------------------------

        return str;
    }

    private short checkTown( String town, Context context ) {
        String str = "";
        char c = town.toLowerCase().charAt( 0 );
        RawFileHelper f1 = new RawFileHelper( activity, town.charAt( 0 ) );
        // открываем поток для чтения

        // читаем содержимое
        while ( ( ( str = f1.readln() ) != null ) && ( !str.toLowerCase().equals( town ) ) ) ;

        f1.dispose();

        if ( str == null ) return 0;
        if ( used_towns.get( String.valueOf( town.toLowerCase().charAt( 0 ) ) ).contains( str.toLowerCase() ) )
            return 1;
        return 2;
    }


    public static class RawFileHelper {
        Resources r;
        InputStream is;
        int currentFileID;
        Activity activity;
        final int[] rawTownResourceIds = new int[] {
                R.raw.town0, R.raw.town1, R.raw.town2,
                R.raw.town3, R.raw.town4, R.raw.town5,
                R.raw.town6, R.raw.town7, R.raw.town8,
                R.raw.town9, R.raw.town10, R.raw.town11,
                R.raw.town12, R.raw.town13, R.raw.town14,
                R.raw.town15, R.raw.town16, R.raw.town17,
                R.raw.town18, R.raw.town19, R.raw.town20,
                R.raw.town21, R.raw.town22, R.raw.town23,
                R.raw.town24, R.raw.town25, R.raw.town26,
                R.raw.town27, R.raw.town28, R.raw.town29,
                R.raw.town30, R.raw.town31, R.raw.town32 };


        public RawFileHelper( Activity activity, char c ) {
            this.activity = activity;
            r = activity.getResources();
            is = r.openRawResource( rawTownResourceIds[ ( int ) c - ( int ) 'а' ] );
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
            while ( ( i = is.read() ) != ( int ) '\n' )
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
