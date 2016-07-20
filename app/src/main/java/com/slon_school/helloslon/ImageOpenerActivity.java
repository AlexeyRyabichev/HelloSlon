package com.slon_school.helloslon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by alexey_ryabichev on 19.07.16.
 */
public class ImageOpenerActivity extends AppCompatActivity implements View.OnTouchListener {

    public static final String TAG = "imageURL";

    float first_y = 0;
    float second_y = 0;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    RelativeLayout relativeLayout;
    TouchImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        String url = getIntent().getStringExtra(TAG);
        imageView = (TouchImageView) findViewById(R.id.image);
        assert imageView != null;
        imageLoader.displayImage(url, imageView);
        relativeLayout = (RelativeLayout) findViewById(R.id.relaytiveLayout);
        assert relativeLayout != null;
        relativeLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                first_y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                second_y = motionEvent.getX();
                break;
        }

        if(Math.abs(second_y - first_y) >= 30)
            onBackPressed();
        return true;

    }
}
