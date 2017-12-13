package com.media.app.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.media.app.R;
import com.media.app.UI.Models.More_Model;


import java.util.ArrayList;

/**
 * Created by harsh.arora on 30-11-2017.
 */

public class More_Adapter extends RecyclerView.Adapter<More_Adapter.moreholder> {

    ArrayList more_imageList, more_nameList,more_pack_name;
    private Context context;
    private ArrayList<More_Model> more_itemsList;

  /*  public More_Adapter(Context context, ArrayList moreimage_List, ArrayList morename_List, ArrayList morepack_name) {
        this.context = context;
        this.more_imageList = moreimage_List;
        this.more_nameList = morename_List;
        this.more_pack_name= morepack_name;

    } */

    public More_Adapter(Context context, ArrayList<More_Model> itemsList) {
        this.more_itemsList = itemsList;

        this.context = context;
    }

    public static class moreholder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        TextView down_title;
        ImageView img_down;
        CheckBox more_chk;

        public moreholder(View v) {
            super(v);
            this.mCardView = (CardView) v.findViewById(R.id.more_card);
            this.down_title = (TextView) v.findViewById(R.id.more_tvTitle);
            this.img_down = (ImageView) v.findViewById(R.id.more_itemImage);
            this.more_chk = (CheckBox) v.findViewById(R.id.more_check);


        }
    }


    @Override
    public More_Adapter.moreholder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_list_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        moreholder mvh = new moreholder(v);
        return mvh;

    }

    @Override
    public void onBindViewHolder(moreholder holder, int i) {

        final More_Model singleItem = more_itemsList.get(i);

        holder.down_title.setText(singleItem.getName());

        Glide.with(context)
                .load(singleItem.getUrl())
                .centerCrop()
                .placeholder(R.drawable.android)
                .error(R.drawable.android)
                .into(holder.img_down);
    }


    @Override
    public int getItemCount() {
        return (null != more_itemsList ? more_itemsList.size() : 0);
    }
}
