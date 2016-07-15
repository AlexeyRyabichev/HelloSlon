package com.slon_school.helloslon.workers;

import android.app.Activity;

import com.slon_school.helloslon.core.Key;
import com.slon_school.helloslon.core.Response;
import com.slon_school.helloslon.core.Worker;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 1 on 15.07.2016.
 */
public class WeatherWorker extends Worker {

    final private String keyApi= "trnsl.1.1.20160715T075701Z.a4d46525907a4a9b.a79bfd0a0d08f77d8e7b35baea67c228bd9df37f";

    private ArrayList<Key> keys;


    public WeatherWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("яндекс переводчик"));

    }

    @Override
    public ArrayList<Key> getKeys() {
        return keys;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {
        return post();
    }



    private Response post() {
        String vivod = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://translate.yandex.net/api/v1.5/tr.json/translate");// ? \n" +
                //"key=" + keyApi + "\n" +
                //" & text=Привет\n" +
                //" & lang=ru-en\n");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("key",
                    keyApi));
            nameValuePairs.add(new BasicNameValuePair("text",
                    "Привет"));
            nameValuePairs.add(new BasicNameValuePair("lang",
                    "ru-en"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                vivod += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Response(vivod, false);
    }


}
