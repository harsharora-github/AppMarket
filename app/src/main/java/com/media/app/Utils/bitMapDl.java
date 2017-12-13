package com.media.app.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;


/**
 * Created by prabeer.kochar on 11-08-2017.
 */

public class bitMapDl extends AsyncTask<String, Void, Bitmap> {

    public bitMapDl(){
        super();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

        String imageURL = URL[0];

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
