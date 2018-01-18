package com.media.app.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.media.app.UI.Models.More_Model;


import java.util.ArrayList;

import static com.media.app.UI.Adapters.SectionListDataAdapter.pack_name;
import static com.media.app.UI.Adapters.SectionListDataAdapter.urlList;

/**
 * Created by harsh.arora on 30-11-2017.
 */

public class More_Adapter extends RecyclerView.Adapter<More_Adapter.moreholder> {

    ArrayList more_imageList, more_nameList,more_pack_name;
    private Context context;
    private ArrayList<More_Model> more_itemsList;

    public static ArrayList urlList;
    public static ArrayList pack_name;
    public static ArrayList imageList;
    public static ArrayList nameList;


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

        urlList = new ArrayList<>();
        pack_name = new ArrayList<>();
        imageList = new ArrayList<>();
        nameList = new ArrayList<>();


        Glide.with(context)
                .load(singleItem.getUrl())
                .fitCenter()
                .placeholder(R.drawable.bs)
                .error(R.drawable.android)
                .into(holder.img_down);

        holder.more_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        return (null != more_itemsList ? more_itemsList.size() : 0);
    }
}
