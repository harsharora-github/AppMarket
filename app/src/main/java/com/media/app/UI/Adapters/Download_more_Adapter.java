package com.media.app.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.media.app.R;
import com.media.app.UI.Downloading_more;

import java.util.ArrayList;

/**
 * Created by harsh.arora on 10-01-2018.
 */

public class Download_more_Adapter extends RecyclerView.Adapter<Download_more_Adapter.downholdermore> {

    ArrayList imageList, nameList,pack_name;


    private Context context;

    public Download_more_Adapter(Downloading_more context, ArrayList imageList, ArrayList nameList, ArrayList pack_name) {
        this.context = context;
        this.imageList = imageList;
        this.nameList = nameList;
        this.pack_name=pack_name;


    }



    public static class downholdermore extends RecyclerView.ViewHolder {
        public CardView mCardView;
        TextView down_title,Installl;
        ImageView img_down;
        Button openbutton;
        ProgressBar prog;

        public downholdermore(View v) {
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
    public Download_more_Adapter.downholdermore onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_down, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Download_more_Adapter.downholdermore vh = new Download_more_Adapter.downholdermore(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Download_more_Adapter.downholdermore holder,final int i) {

        holder.down_title.setText(nameList.get(i).toString());
        Glide.with(context)
                .load(imageList.get(i))
                .fitCenter()
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

        holder.openbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp1(context,pack_name.get(i).toString());
                Log.d("open", "onClick: ");
            }
        });

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

    public boolean openApp1(Context context, String packageName) {


        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
        } else {

            context.startActivity(i);
            return true;
        }

    }
}
