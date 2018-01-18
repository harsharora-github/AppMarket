package com.media.app.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.media.app.R;
import com.media.app.UI.Models.SingleItemModel;

import java.util.ArrayList;

/**
 * Created by harsh.arora on 01-11-2017.
 */

public class Description extends AppCompatActivity {

    ImageView img;
    TextView destxt;
    ScrollView sc;
    ArrayList<SingleItemModel> desList;
    SingleItemModel sn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        img = (ImageView) findViewById(R.id.des_image);
        destxt = (TextView) findViewById(R.id.des_txt);
        sc = (ScrollView) findViewById(R.id.scrol);


        String prd = null;
        String Hrd = null;

        try {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                prd = extras.getString("images");
                Hrd = extras.getString("desc");
            }
            Glide.with(this)
                    .load(prd)
                    .fitCenter()
                    .placeholder(R.drawable.android)
                    .error(R.drawable.android)
                    .into(img);
            destxt.setText(Hrd);

//        Log.d("des", "onCreate: "+sn.getDescription().toString());
            //    Log.d("des", "onCreate: "+sn.getUrl().toString());
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}
