package com.slon_school.helloslon;

import android.content.Context;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.slon_school.helloslon.core.Response;

import java.util.ArrayList;


/**
 * Created by alexey_ryabichev on 11.07.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    ArrayList<Pair<String, Response>> items;

    public RecyclerViewAdapter(ArrayList<Pair<String, Response>> items) {
        this.items = items;
    }
    public ArrayList<String> linkArray = new ArrayList<>();

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ItemViewHolder(inflater.inflate(R.layout.item, parent, false ) );
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (items.get(position).first.equals("Slon")) {

            holder.textViewSlon.setText(items.get(position).second.getResponse());
            if(items.get(position).second.isHaveImages() == true){
                linkArray = items.get(position).second.getImages();
//                for(int i = 0; )
                //holder.slonImage.setImageURI(Uri.parse(linkArray.get(0)));
            }
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
       // public SimpleDraweeView userImage;
       // public SimpleDraweeView slonImage;

            public ItemViewHolder(View itemView) {
                super(itemView);
                slonCard = itemView.findViewById(R.id.slonCard);
                userCard = itemView.findViewById(R.id.userCard);
                textViewUser = (TextView) itemView.findViewById(R.id.userText);
                textViewSlon = (TextView) itemView.findViewById(R.id.slonText);
                //userImage = (SimpleDraweeView) itemView.findViewById(R.id.userImage);
               // slonImage = (SimpleDraweeView) itemView.findViewById(R.id.slonImage);
            }
    }
}
