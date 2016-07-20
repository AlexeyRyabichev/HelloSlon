package com.slon_school.helloslon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by alexey_ryabichev on 19.07.16.
 */
public class ImageOpenerActivity extends AppCompatActivity{

    public static final String TAG = "imageURL";

    ImageLoader imageLoader;
    DisplayImageOptions options;
    ScaleImageView imageView;

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
        //imageView = (ImageView) findViewById(R.id.image);
        imageView = (ScaleImageView) findViewById(R.id.image);
        imageLoader.displayImage(url, imageView);
    }
}
