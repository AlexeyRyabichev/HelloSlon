package com.slon_school.helloslon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.loadingImage.setVisibility(View.GONE);
        if (items.get(position).first.equals("Slon")) {

            holder.textViewSlon.setText(items.get(position).second.getResponse());
            if (items.get(position).second.isHaveImages())
                imageLoader.displayImage(items.get(position).second.getImages().get(0), holder.slonImage, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.loadingImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.loadingImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.loadingImage.setVisibility(View.GONE);
                        holder.slonImage.setTag(imageUri);
                        holder.slonImage.setImageBitmap( loadedImage );
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        holder.loadingImage.setVisibility(View.GONE);
                    }
                });
            else
                holder.slonImage.setVisibility(View.GONE);
            holder.slonCard.setVisibility(View.VISIBLE);
            holder.userCard.setVisibility(View.GONE);
        }
        else{
            holder.textViewUser.setText(items.get(position).second.getResponse());
            holder.userCard.setVisibility(View.VISIBLE);
            holder.slonCard.setVisibility(View.GONE);
        }
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
        public ImageView slonImage;
        public View.OnClickListener imageListener;
        ProgressWheel loadingImage;

            public ItemViewHolder(View itemView, final ImageLoader imageLoader, final MainActivity mainActivity) {
                super(itemView);
                slonCard = itemView.findViewById(R.id.slonCard);
                userCard = itemView.findViewById(R.id.userCard);
                textViewUser = (TextView) itemView.findViewById(R.id.userText);
                textViewSlon = (TextView) itemView.findViewById(R.id.slonText);
                slonImage = (ImageView) itemView.findViewById(R.id.slonImage);
                loadingImage = (ProgressWheel) itemView.findViewById(R.id.loadingImage);
                imageListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = (String) view.getTag();
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imageLoader.getDiskCache().get(uri).getAbsolutePath()), "image/png");
                        mainActivity.startActivity(intent);
                    }
                };
                slonImage.setOnClickListener(imageListener);
            }
    }
}
