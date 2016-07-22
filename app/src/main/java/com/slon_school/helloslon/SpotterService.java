package com.slon_school.helloslon;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.PhraseSpotter;
import ru.yandex.speechkit.PhraseSpotterListener;
import ru.yandex.speechkit.PhraseSpotterModel;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by alexey_ryabichev on 21.07.16.
 */
public class SpotterService extends Service implements PhraseSpotterListener {

    private ResultReceiver resultReceiver;
    private PhraseSpotter phraseSpotter;

    public synchronized void registerReceiver(ResultReceiver resultReceiver) {
        if (resultReceiver != null)
            this.resultReceiver = resultReceiver;
    }

    public synchronized void unregisterReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = null;
    }

    public synchronized void send(int code, Bundle bundle) {
        if (resultReceiver != null)
            resultReceiver.send(code, bundle);
    }

    public class SpotterBinder extends Binder {
        SpotterService getService() {
            return SpotterService.this;
        }
    }

    SpotterBinder spotterBinder = new SpotterBinder();

    @Override
    public void onPhraseSpotted(String s, int i) {
        send(0, null);
    }

    @Override
    public void onPhraseSpotterStarted() {
        Toast.makeText(SpotterService.this, "I'm starting recognizing at SpotterService", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhraseSpotterStopped() {

    }

    @Override
    public void onPhraseSpotterError(Error error) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        PhraseSpotterModel model = new PhraseSpotterModel("phrase-spotter/commands");
        Error loadResult = model.load();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Error error = PhraseSpotter.start();
        Toast.makeText(SpotterService.this, error.getString(), Toast.LENGTH_SHORT).show();
        return spotterBinder;
    }
}