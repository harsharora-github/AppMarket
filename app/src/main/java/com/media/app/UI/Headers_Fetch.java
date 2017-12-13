package com.media.app.UI;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.app.UI.Constant.URL12;
import static com.media.app.UI.PollRes.poll;


/**
 * Created by harsh.arora on 31-10-2017.
 */

public class Headers_Fetch extends AsyncTask<String, String, ArrayList<String>> {

    String urldisplay =URL12;

    String header_name;


    ArrayList<String> hs ;

    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        Log.d("harsh", "doInBackground:entry");
        // urldisplay = urls[0];
        String Str= poll(urldisplay);
        hs=new ArrayList<>();
        Log.d("harsh", "doInBackground:"+Str);
        try {
            Log.d("harsh", "doInBackground:entryto try ");
            JSONObject jObject = new JSONObject(Str);
            JSONArray json_Array = jObject.getJSONArray("headers");
            Log.d("harsh", "doInBackground: json_Array print to string"+json_Array.toString());

            for(int i=0;i<json_Array.length();i++){
                JSONObject json_data = json_Array.getJSONObject(i);
                header_name= json_data.getString("title");
                Log.d("harsh", "doInBackground title:"+json_data.getString("title"));



                hs.add(header_name);
            }

            Log.d("harsh", "doInBackground:"+hs.size());
        } catch (Exception e) {
            Log.d("Json", "doInBackground: " + e.getMessage());
        }
        return hs;
    }
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
    }
}
