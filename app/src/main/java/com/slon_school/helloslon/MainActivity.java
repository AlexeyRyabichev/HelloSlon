package com.slon_school.helloslon;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements RecognizerListener{

    Recognizer recognizer;
    private static final int REQUEST_PERMISSION_CODE = 1;
    Button recording_button = (Button) findViewById(R.id.recording_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechKit.getInstance().configure(getApplicationContext(), getString(R.string.api_key));
        createAndStartRecognizer();

        recording_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndStartRecognizer();
            }
        });
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {

    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {

    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {

    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        Toast.makeText(this, recognition.getBestResultText(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void createAndStartRecognizer() {
        final Context context = getApplicationContext();
        if (context == null) {
            return;
        }

        if ( ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            // Reset the current recognizer.
            resetRecognizer();
            // To create a new recognizer, specify the language, the model - a scope of recognition to get the most appropriate results,
            // set the listener to handle the recognition events.
            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, this);
            // Don't forget to call start on the created object.
            recognizer.start();
           }
    }
    private void resetRecognizer() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer = null;
        }
    }
}
