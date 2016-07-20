package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Volha on 20.07.2016.
 */
public class TamagotchiWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    Date date = new Date(0);

    int Hunger = 100;
    int Energy = 100;
    int Hygiene = 100;
    int Fun = 100;


    public TamagotchiWorker( Activity activity ) {
        super( activity );
        keys.add(new Key("тамагочи"));
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
        if ( arguments.get().size() != 0 ) {
            if () {

                return new Response("", true);
            }
            if ( comand.equals( "хватит" ) ) {
                return new Response( "Действительно хватит", false );
            }
        }
        return new Response( "Ничего не понял.", true);
    }
}



/*

   ___      ___
  /   \____/   \
 /    / __ \    \
/    |  ..  |    \
\___/|      |\___/\
   | |_|  |_|      \
   | |/|__|\|       \
   |   |__|         |\
   |   |__|   |_/  /  \
   | @ |  | @ || @ |   '
   |   |~~|   ||   |     -Hamilton Furtado-
   'ooo'  'ooo''ooo'

 */