package com.slon_school.helloslon;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ldoublem.loadingviewlib.LVCircularCD;
import com.ldoublem.loadingviewlib.LVCircularJump;
import com.ldoublem.loadingviewlib.LVLineWithText;
import com.lusfold.spinnerloading.SpinnerLoading;
import com.slon_school.helloslon.core.Core;

import java.util.ArrayList;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Vocalizer;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements RecognizerListener{

    private static final int REQUEST_PERMISSION_CODE = 1;


    private Recognizer recognizer;
    private Core core;
    private ArrayList<Pair<String, String>> dialogList;
    private RecyclerViewAdapter adapter;
    private LVCircularCD progressBar;
    private SpinnerLoading waitingForResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechKit.getInstance().configure(getApplicationContext(), getString(R.string.api_key));

        waitingForResponse = (SpinnerLoading) findViewById(R.id.waitingForResponse);
        waitingForResponse.setVisibility(View.GONE);
        waitingForResponse.setCircleRadius(10);
        waitingForResponse.setPaintMode(0);

        progressBar = (LVCircularCD) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Button recording_button = (Button) findViewById(R.id.recording_button);
        core = new Core(this);
        RecyclerView dialogWindow = (RecyclerView) findViewById(R.id.dialog_window);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        dialogWindow.setLayoutManager(layoutManager);
        dialogWindow.setItemAnimator(itemAnimator);

        dialogList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(dialogList);

        dialogWindow.setAdapter(adapter);

        recording_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndStartRecognizer();
            }
        });
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        progressBar.startAnim();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        progressBar.stopAnim();
        progressBar.setVisibility(View.GONE);
        waitingForResponse.setVisibility(View.VISIBLE);
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

        waitingForResponse.setVisibility(View.GONE);

        String question = recognition.getBestResultText();
//        Toast.makeText(this, question, Toast.LENGTH_LONG).show();
        String answer = core.request(question);

        Pair<String, String> questionPair = Pair.create("Slon", question);
        dialogList.add(questionPair);
        adapter.notifyDataSetChanged();

        Vocalizer vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, answer, true, Vocalizer.Voice.JANE);
        vocalizer.start();

//        Toast.makeText(this, answer, Toast.LENGTH_LONG).show();

        Pair<String, String> answerPair = Pair.create("User", answer);

        dialogList.add(answerPair);

        adapter.notifyDataSetChanged();
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
            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.QUERIES, this);
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
