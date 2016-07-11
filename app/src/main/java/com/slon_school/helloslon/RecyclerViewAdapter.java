package com.slon_school.helloslon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by alexey_ryabichev on 11.07.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    ArrayList<String> items;

    public RecyclerViewAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ItemViewHolder(inflater.inflate(R.layout.item, null ) );
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textViewSlon.setText(items.get(position));
        holder.textViewUser.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUser;
        public TextView textViewSlon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewUser = (TextView) itemView.findViewById(R.id.userText);
            textViewSlon = (TextView) itemView.findViewById(R.id.slonText);
        }
    }
}
