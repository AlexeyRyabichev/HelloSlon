package com.slon_school.helloslon.workers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.slon_school.helloslon.R;
import com.slon_school.helloslon.core.HelpMan;
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
    private ArrayList<String> picArr = new ArrayList<String>();

    private int Hunger;
    private int Energy;
    private int Hygiene;
    private int Fun;
    private int Healthy;
    private boolean EOG;
    private boolean Ill;
    private boolean Sleep;

    private Date lastFeed;
    private Date lastSleep;
    private Date lastWash;
    private Date lastPlay;
    private Date lastVaccination;

    SharedPreferences sPref;

    private void Init() {
        sPref = activity.getPreferences(Activity.MODE_PRIVATE);

        Hunger =  sPref.getInt("Hunger", 100);
        Energy =  sPref.getInt("Energy", 100);
        Hygiene = sPref.getInt("Hygiene", 100);
        Fun =     sPref.getInt("Fun", 100);
        Healthy = sPref.getInt("Healthy", 100);

        EOG =   sPref.getBoolean("EOG", false);
        Ill =   sPref.getBoolean("Ill", false);
        Sleep = sPref.getBoolean("Sleep", false);

        lastFeed =        new Date( sPref.getLong("lastFeed", new Date().getTime() ) );
        lastSleep =       new Date( sPref.getLong("lastSleep", new Date().getTime() ) );
        lastWash =        new Date( sPref.getLong("lastWash", new Date().getTime() ) );
        lastPlay =        new Date( sPref.getLong("lastPlay", new Date().getTime() ) );
        lastVaccination = new Date( sPref.getLong("lastVaccination", new Date().getTime() ) );
    }

    public TamagotchiWorker( Activity activity ) {
        super( activity );
        Init();
        keys.add(new Key("тамагочи"));
        InitPicArr();
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
        if (arguments.contains(new Key(activity.getString(R.string.help0))) || arguments.contains(new Key(activity.getString(R.string.help1)))) {
            return new HelpMan(R.raw.tamagotchi_help,activity).getHelp();
        }

        String str = new String();
        str = "";
        timers();
        EOG = checkEOG();
        if(EOG) {
            EOG = false;
            ArrayList<String> _buf = new ArrayList<String>();
            _buf.add(printPet());
            powerInit();
            saveAll();
            return new Response("\nЗаводим нового слона", true);
        }
        if ( arguments.get().size() != 0 ) {
            if (cmdFeed(arguments) && !Sleep) {
                Hunger += 30;
                if(Hunger > 100) Hunger = 100;
            }

            boolean _buf = Sleep;
            if (cmdSleep(arguments)) {
                if(_buf != Sleep)
                    lastSleep = new Date();
            }
            if (cmdWash(arguments) && !Sleep) {
                Hygiene += 30;
                if(Hygiene > 100) Hygiene = 100;
            }
            if (cmdPlay(arguments) && !Sleep) {
                Fun += 30;
                Energy -= 20;
                if(Energy < 0) Energy = 0;
                if(Fun > 100)  Fun = 100;
            }
            if (cmdVaccination(arguments)) {
                Healthy = 100;
                Ill = false;
            }
            if(arguments.get().get(0).equals("хватит")) {
                saveAll();
                return new Response("Действительно хватит", false);
            }
            if(arguments.get().get(0).equals("рисуй")) {
                ArrayList<String> _buf1 = new ArrayList<String>();
                _buf1.add(printPet());
                saveAll();
                return new Response(printText(), true, _buf1);
            }
            ArrayList<String> _buf2 = new ArrayList<String>();
            _buf2.add(printPet());
            saveAll();
            return new Response(printText(), true, _buf2);
        }
        ArrayList<String> _buf3 = new ArrayList<String>();
        _buf3.add(printPet());
        saveAll();
        return new Response(printText(), true, _buf3);
    }

    private void powerInit() {
        Hunger =   100;
        Energy =   100;
        Hygiene =  100;
        Fun =      100;
        Healthy =  100;

        EOG =   false;
        Ill =   false;
        Sleep = false;

        lastFeed =        new Date();
        lastSleep =       new Date();
        lastWash =        new Date();
        lastPlay =        new Date();
        lastVaccination = new Date();
    }

    private void saveAll() {
        sPref = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Hunger", Hunger);
        ed.putInt("Energy", Energy);
        ed.putInt("Hygiene", Hygiene);
        ed.putInt("Fun", Fun);
        ed.putInt("Healthy", Healthy);

        ed.putBoolean("EOG", EOG);
        ed.putBoolean("Ill", Ill);
        ed.putBoolean("Sleep", Sleep);

        ed.putLong("lastFeed", lastFeed.getTime());
        ed.putLong("lastSleep", lastSleep.getTime());
        ed.putLong("lastWash", lastWash.getTime());
        ed.putLong("lastPlay", lastPlay.getTime());
        ed.putLong("lastVaccination", lastVaccination.getTime());
        ed.apply();
    }

    private void timers() {
        Date Now = new Date();

        Hunger -= (int)((Now.getTime() - lastFeed.getTime()) / 1000 / 60 / 9);
        if(Hunger < 0) Hunger = 0;

        if(Sleep) {
            Energy += (int)((Now.getTime() - lastSleep.getTime()) / 1000 / 60 / 15);
            if(Energy > 100) Energy = 100;
        }
        else {
            Energy -= (int)((Now.getTime() - lastSleep.getTime()) / 1000 / 60 / 12);
            if(Energy < 0) Energy = 0;
        }

        Hygiene -= (int)((Now.getTime() - lastWash.getTime()) / 1000 / 60 / 12);
        if(Hygiene < 0) Hygiene = 0;

        Fun -= (int)((Now.getTime() - lastPlay.getTime()) / 1000 / 60 / 9);
        if(Fun < 0) Fun = 0;

        if(Hygiene < 40) {
            Random r = new Random();
            Ill = true;
            lastVaccination.setTime(Now.getTime() - (r.nextInt(40) + 1)*1000);
        }

        if(Ill) {
            Healthy -= (int)((Now.getTime() - lastVaccination.getTime()) / 1000 / 60 / 6);
            if(Healthy < 0) Healthy = 0;
        }
    }

    private String printPet() {
        String res = new String();
        res = "";
        if(Sleep) res += picArr.get(3);
        else if(Energy < 30) res += picArr.get(1);
        else res += picArr.get(0);
        return res;
    }
    private String printText(){
        String res = new String();
        res = "";
        if((Energy == 0) || (Hunger == 0) || (Hygiene == 0) || (Fun == 0) || (Healthy == 0)) {
            res += picArr.get(2) + "ты малёк запустил питомца, игра окончена\n";
        }
        res += "Hunger = " + String.valueOf( Hunger ) + "\n";
        res += "Energy = " + String.valueOf( Energy ) + "\n";
        res += "Hygiene = " + String.valueOf( Hygiene ) + "\n";
        res += "Fun = " + String.valueOf( Fun ) + "\n";
        res += "Healthy = " + String.valueOf( Healthy ) + "";
        if(Sleep)
            res += "\nSleep";
        if(Ill)
            res += "\nIll";
        return res;
    }

    private boolean cmdFeed(Key arguments) {
        lastFeed = new Date();
        if(arguments.get().get( 0 ).equals("корми"))
            return true;
        if(arguments.get().get( 0 ).equals("кормить"))
            return true;
        return false;
    }

    private boolean cmdSleep(Key arguments) {
        if(arguments.get().get( 0 ).equals("спи")) {
            Sleep = true;
            return true;
        }

        if(arguments.get().get( 0 ).equals("сон")) {
            Sleep = true;
            return true;
        }

        if(arguments.get().get( 0 ).equals("спать")) {
            Sleep = true;
            return true;
        }

        if(arguments.get().get( 0 ).equals("проснись")) {
            Sleep = false;
            return true;
        }

        if(arguments.get().get( 0 ).equals("вставай")) {
            Sleep = false;
            return true;
        }
        return false;
    }

    private boolean cmdWash(Key arguments) {
        lastWash = new Date();
        if(arguments.get().get( 0 ).equals("ванна")) {
            return true;
        }
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

    private void InitPicArr() {
        picArr.add("drawable://" + String.valueOf(R.drawable.slon0));
        picArr.add("drawable://" + String.valueOf(R.drawable.slon1));
        picArr.add("drawable://" + String.valueOf(R.drawable.slon2));
        picArr.add("drawable://" + String.valueOf(R.drawable.slon3));
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