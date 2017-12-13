package com.media.app.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.media.app.R;


import java.util.ArrayList;

public class Download_Adapter extends RecyclerView.Adapter<Download_Adapter.downholder> {

    ArrayList imageList, nameList,pack_name;


    private Context context;

    public Download_Adapter(Context context, ArrayList imageList, ArrayList nameList, ArrayList pack_name) {
        this.context = context;
        this.imageList = imageList;
        this.nameList = nameList;
        this.pack_name=pack_name;


    }


    public static class downholder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        TextView down_title,Installl;
        ImageView img_down;
        Button openbutton;
        ProgressBar prog;

        public downholder(View v) {
            super(v);
            this.mCardView = (CardView) v.findViewById(R.id.card_down);
            this.down_title = (TextView) v.findViewById(R.id.downTitle);
            this.img_down = (ImageView) v.findViewById(R.id.downImage);
            this.openbutton = (Button) v.findViewById(R.id.openbutton);
            this.prog = (ProgressBar) v.findViewById(R.id.prog);
            this.Installl = (TextView)v.findViewById(R.id.ins);

        }
    }


    @Override
    public Download_Adapter.downholder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_down, parent, false);
        // set the view's size, margins, paddings and layout parameters
        downholder vh = new downholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(downholder holder, final int i) {

        holder.down_title.setText(nameList.get(i).toString());

        Glide.with(context)
                .load(imageList.get(i))
                .centerCrop()
                .placeholder(R.drawable.android)
                .error(R.drawable.android)
                .into(holder.img_down);
if(pack_name != null) {
    if (openApp(context, pack_name.get(i).toString())) {
        holder.openbutton.setVisibility(View.VISIBLE);
        holder.prog.setVisibility(View.INVISIBLE);
        holder.Installl.setVisibility(View.INVISIBLE);
    }
}

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public boolean openApp(Context context, String packageName) {


        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
        } else {
            return true;
        }

    }

}