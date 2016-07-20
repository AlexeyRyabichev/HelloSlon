package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import java.util.ArrayList;

/**
 * Created by Volha on 17.07.2016.
 */
public class DilbertWorker extends Worker
{
    private String link1;
    private String link2;
    private boolean isLink1Got;
    private boolean isLink2Got;
    private ArrayList<Key> keys = new ArrayList<Key>();
    private int PicPointer;

    public DilbertWorker( Activity activity ) {
        super( activity );
        keys.add(new Key("дилберт"));
        keys.add(new Key("гилберт"));
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
        if(keys.size() != 0) {
            // начать сканировать сайт
            //http://dilbertru.blogspot.ru/search/label/%D0%94%D0%B8%D0%BB%D0%B1%D0%B5%D1%80%D1%82
            //<li><a href='http://dilbertru.blogspot.ru

            //if(keys.get(0).get())
        }


        if(arguments.get().size() != 0){
            // работать с картинками
            //view-source:http://dilbertru.blogspot.ru/2016/07/20160716.html
            //<img src="http://assets.amuniversal.com

            String comand = arguments.get().get(0);
            if(comand.equals("следующий") || comand.equals("еще") || comand.equals("да")) {

            }
            if(comand.equals("хватит")) {
                return new Response("Действительно хватит", false);
            }
            if(comand.equals("нет")) {
                return new Response("Ну ладно", false);
            }
        }
        return new Response("DilbertWorkerError", false);
    }





/*

    private String getPic() {
        boolean isLinkGot;
        final CountDownLatch countDownLatch = new CountDownLatch(gallow1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    isLinkGot = getLink();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isLinkGot) {
            return quote;
        } else {
            return "Не удалось загрузить картинку";
        }
    }


    public boolean getLink(boolean level, String URLStr) throws Exception {
        String line;
        /*Random random = new Random();
        Integer tmp = random.nextInt(551);

        URL url = new URL(URLStr);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                activity.getString( R.string.cp1251)));
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.contains("  <p><img src=")) {
                if(level) {
                    quote = washLink1( line );
                }
                else {
                    quote = washLink2(line);
                }

                return true;
            }
        }
        return false;
    }
*/
    private String washLink1( String line ) {
        line = line.replace("  <p><img src=\"","");
        line = line.replaceAll("\"(.)*","");
        line = "http://calvin-hobbs.ilost.ru/" + line;
        return line;
    }

    private String washLink2( String line ) {
        line = line.replace("  <p><img src=\"","");
        line = line.replaceAll("\"(.)*","");
        line = "http://calvin-hobbs.ilost.ru/" + line;
        return line;
    }
}
