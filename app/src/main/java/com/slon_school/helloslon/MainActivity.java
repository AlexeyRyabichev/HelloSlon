package com.slon_school.helloslon;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ldoublem.loadingviewlib.LVCircularCD;
import com.ldoublem.loadingviewlib.LVCircularJump;
import com.ldoublem.loadingviewlib.LVLineWithText;
import com.lusfold.spinnerloading.SpinnerLoading;
import com.slon_school.helloslon.core.Core;

import java.util.ArrayList;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.PhraseSpotter;
import ru.yandex.speechkit.PhraseSpotterListener;
import ru.yandex.speechkit.PhraseSpotterModel;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Vocalizer;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements RecognizerListener, PhraseSpotterListener {

    private static final int REQUEST_PERMISSION_CODE = 1;

    private Recognizer recognizer;
    private Core core;
    private ArrayList<Pair<String, String>> dialogList;
    private RecyclerViewAdapter adapter;
    private LVCircularCD progressBar;
    private SpinnerLoading waitingForResponse;
    private TextView currentStatus;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechKit.getInstance().configure(getApplicationContext(), getString(R.string.api_key));

        //Variables
        core = new Core(this);
        Button recording_button = (Button) findViewById(R.id.recording_button);
        RecyclerView dialogWindow = (RecyclerView) findViewById(R.id.dialog_window);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        dialogList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(dialogList);
        waitingForResponse = (SpinnerLoading) findViewById(R.id.waitingForResponse);
        progressBar = (LVCircularCD) findViewById(R.id.progressBar);
        PhraseSpotterModel model = new PhraseSpotterModel("phrase-spotter/commands");
        Error loadResult = model.load();

        //"Waiting response from core" animation declaration
        waitingForResponse.setVisibility(View.GONE);
        waitingForResponse.setCircleRadius(10);
        waitingForResponse.setPaintMode(0);

        //"We're listening you" animation declaration
        progressBar.setVisibility(View.GONE);

        //"Dialog window" declaration
        assert dialogWindow != null;
        dialogWindow.setLayoutManager(layoutManager);
        dialogWindow.setItemAnimator(itemAnimator);
        dialogWindow.setAdapter(adapter);

        //Listener for button declaration
        assert recording_button != null;
        recording_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndStartRecognizer();
            }
        });

        //Phrase spotter declaration
        if (loadResult.getCode() != Error.ERROR_OK) {
            updateCurrentStatus("Error occurred during model loading: " + loadResult.getString());
        } else {
            // Set the listener.
            PhraseSpotter.setListener(this);
            // Set the model.
            Error setModelResult = PhraseSpotter.setModel(model);
            handleError(setModelResult);
        }
        
        if ( ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            PhraseSpotter.start();
        }

    }

    //Methods for Recognizer

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
        String answer = core.request(question);


        Pair<String, String> questionPair = Pair.create("Slon", question);
        dialogList.add(questionPair);
        adapter.notifyDataSetChanged();

        Vocalizer vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, answer, true, Vocalizer.Voice.JANE);
        vocalizer.start();

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
            resetRecognizer();
            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.QUERIES, this);
            recognizer.start();
           }
    }

    private void resetRecognizer() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer = null;
        }
    }

    //Methods for Spotter

    @Override
    public void onPhraseSpotted(String s, int i) {
        Toast.makeText(this, "I can hear you2" + s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPhraseSpotterStarted() {
        Toast.makeText(this, "I can hear you", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPhraseSpotterStopped() {

    }

    @Override
    public void onPhraseSpotterError(Error error) {

    }

    private void handleError(Error error) {
        if (error.getCode() != Error.ERROR_OK) {
            updateCurrentStatus("Error occurred: " + error.getString());
        }
    }

    private void updateCurrentStatus(String s) {
        Log.e("spotter", s);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startPhraseSpotter();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void startPhraseSpotter() {
        final Context context = this;
        if (context == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            Error startResult = PhraseSpotter.start();
            handleError(startResult);
        }
    }
}
