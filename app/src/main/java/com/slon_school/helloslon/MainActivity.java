package com.slon_school.helloslon;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.ConnectionRequest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lusfold.spinnerloading.SpinnerLoading;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.slon_school.helloslon.core.Core;
import com.slon_school.helloslon.core.Response;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
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
    private ArrayList<Pair<String, Response>> dialogList;
    private RecyclerViewAdapter adapter;
    //private SpinnerLoading waitingForResponse;
    private ShimmerTextView shimmerTextView;
    final int Network_Error= 7;

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
        RecyclerView.ItemAnimator itemAnimator = new SlideInUpAnimator();
        dialogList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(dialogList, this);
//        waitingForResponse = (SpinnerLoading) findViewById(R.id.waitingForResponse);
        PhraseSpotterModel model = new PhraseSpotterModel("phrase-spotter/commands");
        Error loadResult = model.load();

        //"Waiting response from core" animation declaration
//        waitingForResponse = (SpinnerLoading) findViewById(R.id.waitingForResponse);
//        assert waitingForResponse != null;
//        waitingForResponse.setVisibility(View.GONE);
//        waitingForResponse.setCircleRadius(10);
//        waitingForResponse.setPaintMode(0);

        //"We're listening you" animation declaration
        shimmerTextView = (ShimmerTextView) findViewById(R.id.progressBar);
        Shimmer shimmer = new Shimmer();
        shimmer.start(shimmerTextView);
        shimmerTextView.setVisibility(View.GONE);

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

        //Universal Image Loader init
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(2 * 1024 * 1024)
                .diskCacheFileCount(20)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

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

        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
//            PhraseSpotter.start();
        }
    }

    //Methods for Recognizer

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        shimmerTextView.setVisibility(View.VISIBLE);
        //waitingForResponse.setVisibility(View.GONE);
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        shimmerTextView.setVisibility(View.GONE);
//        waitingForResponse.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {

    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
//        waitingForResponse.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
//        waitingForResponse.setVisibility(View.GONE);
        shimmerTextView.setVisibility(View.GONE);

        Response question = new Response(recognition.getBestResultText(), false);
        Response answer = core.request(question);

        Pair<String, Response> questionPair = Pair.create("User", question);
        dialogList.add(questionPair);
        adapter.notifyItemInserted(dialogList.size());

        Vocalizer vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, answer.getResponse(), true, Vocalizer.Voice.ZAHAR);
        vocalizer.start();

        Pair<String, Response> answerPair = Pair.create("Slon", answer);

        dialogList.add(answerPair);

        adapter.notifyItemInserted(dialogList.size());

    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
//        waitingForResponse.setVisibility(View.GONE);
        if (error.getCode() == Network_Error)
            Toast.makeText(MainActivity.this, getString(R.string.no_connetion), Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void createAndStartRecognizer() {
        final Context context = getApplicationContext();
        if (context == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
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
