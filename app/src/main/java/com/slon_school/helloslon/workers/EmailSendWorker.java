package com.slon_school.helloslon.workers;



        import android.Manifest;
        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.support.v4.app.ActivityCompat;


        import com.slon_school.helloslon.R;
        import com.slon_school.helloslon.core.Helper;
        import com.slon_school.helloslon.core.Key;
        import com.slon_school.helloslon.core.Response;
        import com.slon_school.helloslon.core.Worker;

        import java.util.ArrayList;

/**
 * Created by Mike on 19.07.2016.
 */
public class EmailSendWorker extends Worker {
    private ArrayList<Key> keys;
    private boolean isContinue;


    public EmailSendWorker(Activity activity) {
        super(activity);
        keys = new ArrayList<Key>();
        keys.add(new Key("email"));
        keys.add(new Key("E-mail"));
        keys.add(new Key("емаил"));
        keys.add(new Key("письмо"));
        keys.add(new Key("мыло"));
        keys.add(new Key("mail"));


    }

    @Override
    public ArrayList<Key> getKeys() {
        return null;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Response doWork(ArrayList<Key> keys, Key arguments) {


        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:" + arguments.toString()));
        intent.setType("text/plain");
        activity.startActivity(intent);
        return new Response("", false);
    }


}


