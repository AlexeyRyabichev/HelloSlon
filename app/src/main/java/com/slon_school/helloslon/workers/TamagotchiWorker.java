package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Volha on 20.07.2016.
 */
public class TamagotchiWorker extends Worker {
    private ArrayList<Key> keys = new ArrayList<Key>();
    Date date = new Date(0);

    int Hunger =   100;
    int Energy =   100;
    int Hygiene =  100;
    int Fun =      100;
    int Healthy =  100;
    boolean EOG =  false;
    boolean Ill =  false;

    Date lastFeed = new Date();
    Date lastSleep = new Date();
    Date lastWash = new Date();
    Date lastPlay = new Date();
    Date lastVaccination = new Date();

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
        timers();
        EOG = checkEOG();
        printPet();
        if ( arguments.get().size() != 0 ) {
            if (cmdFeed(arguments)) {
                Hunger += 30;
                if(Hunger > 100) Hunger = 100;
            }
            if (cmdSleep(arguments)) {
                Energy += 30;
                if(Energy > 100) Energy = 100;
            }
            if (cmdWash(arguments)) {
                Hygiene += 30;
                if(Hygiene > 100) Hygiene = 100;
            }
            if (cmdPlay(arguments)) {
                Fun += 30;
                if(Fun > 100) Fun = 100;
            }
            if (cmdVaccination(arguments)) {
                Healthy = 100;
           }
        }
        return new Response( "Ничего не понял.", true);
    }

    private void timers() {
        Date Now = new Date();

        Hunger -= (int)((Now.getTime() - lastFeed.getTime()) / 1000 / 60 / 5);
        if(Hunger < 0) Hunger = 0;

        Energy -= (int)((Now.getTime() - lastSleep.getTime()) / 1000 / 60 / 3);
        if(Energy < 0) Energy = 0;

        Hygiene -= (int)((Now.getTime() - lastWash.getTime()) / 1000 / 60 / 3);
        if(Hygiene < 0) Hygiene = 0;

        Fun -= (int)((Now.getTime() - lastPlay.getTime()) / 1000 / 60 / 4);
        if(Fun < 0) Fun = 0;

        if(Hygiene < 40) {
            Random r = new Random();
            Ill = true;
            lastVaccination.setTime(Now.getTime() - (r.nextInt(Hygiene) + 1)*1000);
        }

        if(Ill) {
            Healthy -= (int)((Now.getTime() - lastVaccination.getTime()) / 1000 / 60 / 3);
            if(Healthy < 0) Healthy = 0;
        }
    }

    private void printPet() {
        if()
    }

    private boolean cmdFeed(Key arguments) {
        lastFeed = new Date();
        if(arguments.get().get( 0 ).equals("корми"))
            return true;
        return false;
    }

    private boolean cmdSleep(Key arguments) {
        lastSleep = new Date();
        if(arguments.get().get( 0 ).equals("спи"))
            return true;
        if(arguments.get().get( 0 ).equals("сон"))
            return true;
        if(arguments.get().get( 0 ).equals("спать"))
            return true;
        return false;
    }

    private boolean cmdWash(Key arguments) {
        lastWash = new Date();
        if(arguments.get().get( 0 ).equals("помойся"))
            return true;
        return false;
    }

    private boolean cmdPlay(Key arguments) {
        lastPlay = new Date();
        if(arguments.get().get( 0 ).equals("играй"))
            return true;
        return false;
    }

    private boolean cmdVaccination(Key arguments) {
        lastVaccination = new Date();
        Ill = false;
        if(arguments.get().get( 0 ).equals("прививка"))
            return true;
        return false;
    }

    private boolean checkEOG() {
        if(Hunger == 0)   return true;
        if(Energy == 0)   return true;
        if(Hygiene == 0)  return true;
        if(Fun == 0)      return true;
        if(Healthy == 0)  return true;
        return false;
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
       | @ |__| @ || @ |   '
       |   |__|   ||   |
       |   |~~|   ||   |
       'ooo'  'ooo''ooo'

 */

/*
   ___      ___
  /   \____/   \
 /    / __ \    \
/    |  00  |    \
\___/|      |\___/\
   | |_|  |_|      \
   | |/|__|\|       \
   |   |__|         |\
   |   |__|   |_/  /  \
   | @ |__| @ || @ |   '
   |   |__|   ||   |
   |   |~~|   ||   |
   'ooo'  'ooo''ooo'

 */


/*
   ___      ___
  /   \____/   \
 /    / __ \    \
/    |  ХХ  |    \
\___/|      |\___/\
   | |_|  |_|      \
   | |/|__|\|       \
   |   |__|         |\
   |   |__|   |_/  /  \
   | @ |__| @ || @ |   '
   |   |__|   ||   |
   |   |~~|   ||   |
   'ooo'  'ooo''ooo'

 */

/*
   ___      ___
  /   \____/   \
 /    / __ \    \
/    |  --  |    \
\___/|      |\___/\
   | |_|  |_|      \
   | |/|__|\|       \
   |   |__|         |\
   |   |__|   |_/  /  \
   | @ |__| @ || @ |   '
   |   |__|   ||   |
   |   |~~|   ||   |
   'ooo'  'ooo''ooo'

 */