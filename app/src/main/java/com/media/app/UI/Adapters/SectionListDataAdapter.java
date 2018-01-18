package com.media.app.UI.Adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.media.app.R;
import com.media.app.UI.Description;
import com.media.app.UI.MainActivity;
import com.media.app.UI.Models.SingleItemModel;


import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;

    public static ArrayList urlList;
    public static ArrayList pack_name;
    public static ArrayList imageList;
    public static ArrayList nameList;
    public static ArrayList description;


    private Context mContext;


    public void Add_to_list(String a){

        urlList.add(a);
    }

    public void Remove_from_list(String a){
        urlList.remove(a);
    }

    public void Add_to_list1(String a){

        pack_name.add(a);
    }

    public void Remove_from_list1(String a){
        pack_name.remove(a);
    }

// APPS images to be Downloaded will be add in the list to show in last activity
    public void Add_to_list_image(String a) {

        imageList.add(a);
    }
    public void Remove_from_list_image(String a){
        imageList.remove(a);
    }

    // APPS name to be Downloaded will be add in the list to show in last activity
    public void Add_to_list_name(String a) {

        nameList.add(a);
    }
    public void Remove_from_list_name(String a){
        nameList.remove(a);
    }


    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;

        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

        final SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());

        urlList = new ArrayList<>();
        pack_name = new ArrayList<>();
        imageList = new ArrayList<>();
        nameList = new ArrayList<>();
        description = new ArrayList();




        //    SingleItemModel singleItem1 = itemsList.get(i);
      //  holder.itemImage.setImageBitmap(singleItem.getImage());

        Glide.with(mContext)
                .load(singleItem.getUrl())
                .fitCenter()
                .placeholder(R.drawable.android)
                .error(R.drawable.android)
                .into(holder.itemImage);


        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "This will show App description ", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(v.getContext(), Description.class);
                Bundle x = new Bundle();

                x.putString("images",singleItem.getUrl());
                x.putString("desc",singleItem.getDescription());
                in.putExtras(x);
                v.getContext().startActivity(in);

            }
        });

        holder.itemcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    Add_to_list( singleItem.getAppURL());
                    Add_to_list1( singleItem.getAppPackage());
                    Add_to_list_image(singleItem.getUrl());
                    Add_to_list_name(singleItem.getName());

                    Toast.makeText(buttonView.getContext(), "Size of urlList" + urlList.size(), Toast.LENGTH_SHORT).show();
                    Log.d("harsha", "Size of urlList" + urlList.toString());
                 //   Log.d("harsha", "Size of urlList" + urlList.get(1).toString());
                    Log.d("harsha", "Size of urlList" + pack_name.toString());
                }

                else{
                    Remove_from_list(singleItem.getAppURL());
                    Remove_from_list1(singleItem.getAppPackage());
                    Remove_from_list_image(singleItem.getUrl());
                    Remove_from_list_name(singleItem.getName());

                    Toast.makeText(buttonView.getContext(), "Size of urlList" + urlList.size(), Toast.LENGTH_SHORT).show();
                    Log.d("harsha", "Size of urlList" + urlList.toString());
                    Log.d("harsha", "Size of pack_name" + pack_name.toString());
                    Log.d("harsha", "Size of imageList" + imageList.toString());
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;

        protected CheckBox itemcheckbox;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemcheckbox = (CheckBox)view.findViewById(R.id.checkboxx);
           //this.itemImage= new ImageDownloader((ImageView) view.findViewById(R.id.itemImage)).execute("http://192.168.43.166/face.png");


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.itemImage:
                            Toast.makeText(v.getContext(), "This will show App description ", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.checkboxx:


                    }
                }
            });


        }

    }

}