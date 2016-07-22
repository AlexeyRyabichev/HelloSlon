package com.slon_school.helloslon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.slon_school.helloslon.core.Response;

import java.util.ArrayList;


/**
 * Created by alexey_ryabichev on 11.07.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    ArrayList<Pair<String, Response>> items;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    MainActivity mainActivity;

    public RecyclerViewAdapter(ArrayList<Pair<String, Response>> items, MainActivity mainActivity) {
        this.items = items;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        this.mainActivity = mainActivity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ItemViewHolder(inflater.inflate(R.layout.item, parent, false ), imageLoader, mainActivity);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        if (items.get(position).first.equals("Slon")) {

            holder.textViewSlon.setText(items.get(position).second.getResponse());
            if (items.get(position).second.isHaveImages()){
                holder.imageContainer.removeAllViews();
                for (String url:items.get(position).second.getImages()) {
                    holder.imageContainer.addView(addingViewForImage(holder.slonCard.getContext(), url));
                }
            }
            else
                holder.imageContainer.setVisibility(View.GONE);
            holder.slonCard.setVisibility(View.VISIBLE);
            holder.userCard.setVisibility(View.GONE);
        }
        else{
            holder.textViewUser.setText(items.get(position).second.getResponse());
            holder.userCard.setVisibility(View.VISIBLE);
            holder.slonCard.setVisibility(View.GONE);
        }
    }

    private View addingViewForImage(final Context context, final String url){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_holder, null, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.slonImage);
        final ProgressWheel progressWheel = (ProgressWheel) view.findViewById(R.id.loadingImage);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageOpenerActivity.class);
                intent.putExtra(ImageOpenerActivity.TAG, url);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(
                                (MainActivity) context,
                                view,
                                context.getString( R.string.imageTransitionName)
                        );
                context.startActivity(intent, options.toBundle());
            }
        };
        imageView.setOnClickListener(listener);
        imageLoader.displayImage(url, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressWheel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressWheel.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressWheel.setVisibility(View.GONE);
                imageView.setTag(imageUri);
                imageView.setImageBitmap( loadedImage );
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressWheel.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUser;
        public TextView textViewSlon;
        public View slonCard;
        public View userCard;
        public LinearLayout imageContainer;
        public View.OnClickListener imageListener;
        ProgressWheel progressWheel;

            public ItemViewHolder(View itemView, final ImageLoader imageLoader, final MainActivity mainActivity) {
                super(itemView);
                slonCard = itemView.findViewById(R.id.slonCard);
                userCard = itemView.findViewById(R.id.userCard);
                textViewUser = (TextView) itemView.findViewById(R.id.userText);
                textViewSlon = (TextView) itemView.findViewById(R.id.slonText);
                imageContainer = (LinearLayout) itemView.findViewById(R.id.imageContainer);
                progressWheel = (ProgressWheel) itemView.findViewById(R.id.loadingImage);
            }
    }
}
