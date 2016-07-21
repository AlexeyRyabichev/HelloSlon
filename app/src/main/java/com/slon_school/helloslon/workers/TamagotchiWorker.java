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
    private ArrayList<String> picArr = new ArrayList<String>();

    private int Hunger =   100;
    private int Energy =   100;
    private int Hygiene =  100;
    private int Fun =      100;
    private int Healthy =  100;
    private boolean EOG =  false;
    private boolean Ill =  false;
    private boolean Sleep = false;

    private Date lastFeed = new Date();
    private Date lastSleep = new Date();
    private Date lastWash = new Date();
    private Date lastPlay = new Date();
    private Date lastVaccination = new Date();

    private void Init() {
        Hunger =   100;
        Energy =   100;
        Hygiene =  100;
        Fun =      100;
        Healthy =  100;
        EOG =  false;
        Ill =  false;
        Sleep = false;

        lastFeed = new Date();
        lastSleep = new Date();
        lastWash = new Date();
        lastPlay = new Date();
        lastVaccination = new Date();
    }

    public TamagotchiWorker( Activity activity ) {
        super( activity );
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
        String str = new String();
        str = "";
        timers();
        EOG = checkEOG();
        if(EOG) {
            EOG = false;
            String _buf = printPet();
            Init();
            return new Response(_buf + "\nЗаводим нового слона", true);
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
                if(Fun > 100) Fun = 100;
            }
            if (cmdVaccination(arguments)) {
                Healthy = 100;
                Ill = false;
            }
            if(arguments.get().get(0).equals("хватит")) {
                return new Response("Действительно хватит", true);
            }
            if(arguments.get().get(0).equals("рисуй")) {
                return new Response(printPet(), true);
            }
            return new Response(printPet(), true);
        }
        return new Response( "Приветствие", true);
    }

    private void timers() {
        Date Now = new Date();

        Hunger -= (int)((Now.getTime() - lastFeed.getTime()) / 1000 / 60 / 3);
        if(Hunger < 0) Hunger = 0;

        if(Sleep) {
            Energy += (int)((Now.getTime() - lastSleep.getTime()) / 1000 / 60 / 5);
            if(Energy > 100) Energy = 100;
        }
        else {
            Energy -= (int)((Now.getTime() - lastSleep.getTime()) / 1000 / 60 / 3);
            if(Energy < 0) Energy = 0;
        }

        Hygiene -= (int)((Now.getTime() - lastWash.getTime()) / 1000 / 60 / 4);
        if(Hygiene < 0) Hygiene = 0;

        Fun -= (int)((Now.getTime() - lastPlay.getTime()) / 1000 / 60 / 3);
        if(Fun < 0) Fun = 0;

        if(Hygiene < 40) {
            Random r = new Random();
            Ill = true;
            lastVaccination.setTime(Now.getTime() - (r.nextInt(40) + 1)*1000);
        }

        if(Ill) {
            Healthy -= (int)((Now.getTime() - lastVaccination.getTime()) / 1000 / 60 / 2);
            if(Healthy < 0) Healthy = 0;
        }
    }

    private String printPet() {
        String res = new String();
        res = "";
        if((Energy == 0) || (Hunger == 0) || (Hygiene == 0) || (Fun == 0) || (Healthy == 0)) {
            res += picArr.get(2) + "ты малёк запустил питомца, игра окончена\n";
        }
        else if(Sleep) res += picArr.get(3);
        else if(Energy < 30) res += picArr.get(1);
        else res += picArr.get(0);

        res += "Hunger = " + String.valueOf( Hunger ) + "\n";
        res += "Energy = " + String.valueOf( Energy ) + "\n";
        res += "Hygiene = " + String.valueOf( Hygiene ) + "\n";
        res += "Fun = " + String.valueOf( Fun ) + "\n";
        res += "Healthy = " + String.valueOf( Healthy ) + "\n";
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
        picArr.add("       ___      ___\n" +
                "      /   \\____/   \\\n" +
                "     /    / __ \\    \\\n" +
                "    /    |  ..  |    \\\n" +
                "    \\___/|      |\\___/\\\n" +
                "       | |_|  |_|      \\\n" +
                "       | |/|__|\\|       \\\n" +
                "       |   |__|         |\\\n" +
                "       |   |__|   |_/  /  \\\n" +
                "       | @ |__| @ || @ |   '\n" +
                "       |   |__|   ||   |\n" +
                "       |   |~~|   ||   |\n" +
                "       'ooo'  'ooo''ooo'\n");

        picArr.add("   ___      ___\n" +
                "  /   \\____/   \\\n" +
                " /    / __ \\    \\\n" +
                "/    |  00  |    \\\n" +
                "\\___/|      |\\___/\\\n" +
                "   | |_|  |_|      \\\n" +
                "   | |/|__|\\|       \\\n" +
                "   |   |__|         |\\\n" +
                "   |   |__|   |_/  /  \\\n" +
                "   | @ |__| @ || @ |   '\n" +
                "   |   |__|   ||   |\n" +
                "   |   |~~|   ||   |\n" +
                "   'ooo'  'ooo''ooo'\n");

        picArr.add("   ___      ___\n" +
                "  /   \\____/   \\\n" +
                " /    / __ \\    \\\n" +
                "/    |  ХХ  |    \\\n" +
                "\\___/|      |\\___/\\\n" +
                "   | |_|  |_|      \\\n" +
                "   | |/|__|\\|       \\\n" +
                "   |   |__|         |\\\n" +
                "   |   |__|   |_/  /  \\\n" +
                "   | @ |__| @ || @ |   '\n" +
                "   |   |__|   ||   |\n" +
                "   |   |~~|   ||   |\n" +
                "   'ooo'  'ooo''ooo'\n");

        picArr.add("   ___      ___\n" +
                "  /   \\____/   \\\n" +
                " /    / __ \\    \\\n" +
                "/    |  --  |    \\\n" +
                "\\___/|      |\\___/\\\n" +
                "   | |_|  |_|      \\\n" +
                "   | |/|__|\\|       \\\n" +
                "   |   |__|         |\\\n" +
                "   |   |__|   |_/  /  \\\n" +
                "   | @ |__| @ || @ |   '\n" +
                "   |   |__|   ||   |\n" +
                "   |   |~~|   ||   |\n" +
                "   'ooo'  'ooo''ooo'\n");
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