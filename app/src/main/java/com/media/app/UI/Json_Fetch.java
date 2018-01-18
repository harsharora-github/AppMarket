package com.media.app.UI;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.app.UI.PollRes.poll;
import static com.media.app.Utils.constants.URL13;


/**
 * Created by harsh.arora on 31-10-2017.
 */

public class Json_Fetch extends AsyncTask<String, String, ArrayList<String>> {


    String urldisplay =URL13;

    String title;

    ArrayList a;



    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        Log.d("harsh", "doInBackground:URL:"+urldisplay);

        String Str= poll(urldisplay);

        a = new ArrayList<>();

        Log.d("harsh", "doInBackground:Str:"+Str);
        try {
            Log.d("harsh", "doInBackground:entryto try ");
            JSONObject jObject = new JSONObject(Str);
            JSONArray json_Array = jObject.getJSONArray("Json");
            Log.d("harsh", "doInBackground: json_Array print to string"+json_Array.toString());

            for(int i=0;i<json_Array.length();i++) {
                JSONObject json_data = json_Array.getJSONObject(i);
                title = json_data.getString("app_catagory");

                Log.d("harsh", "doInBackground: json_Array print app_category"+title);

                JSONArray json_Array1 = json_data.getJSONArray("app_details");

                Log.d("harsh", "doInBackground: json_Array_1 print "+json_Array1.toString());
                Log.d("harsh", "doInBackground: json_Array_1 length "+json_Array1.length());
                for (int j = 0; j < json_Array1.length(); j++) {
                    JSONObject json_data1 = json_Array1.getJSONObject(j);

                    String app_name = json_data1.getString("appName");
                    String image_url = json_data1.getString("imageURL");
                    String app_url = json_data1.getString("APKlocation");
                    String app_package = json_data1.getString("APPPackage");
                    String description = json_data1.getString("Description");
                   // Log.d("harsh", "doInBackground data:" +title+"|"+app_name+"|"+image_url+"|"+app_url+"|"+app_package);
                    String x =  title+"-"+app_name+"|"+image_url+"|"+app_url+"|"+app_package+"|"+description;
                        a.add(x);
                }
            }

            // Isko thek karna hai..................
            String topic = null;
            String app_name = null;
            String image_url = null;
            String app_url = null;
            String app_package = null;
            String description = null;
            String data = null;
            for (Object object: a) {
                String[] separated =  object.toString().trim().split("\\-");
                if((topic == null) || !topic.equals(separated[0])){
                topic = separated[0];
                    data = separated[1];
                    String[] dataarr = data.toString().trim().split("\\|");
                    app_name =  dataarr[0];
                    image_url = dataarr[1];
                    app_url = dataarr[2];
                    app_package = dataarr[3];
                    description = dataarr[4];
                    Log.d("harsh", "topic"+topic);
                    Log.d("harsh", "data:"+app_name+","+image_url);
                }else{
                    data = separated[1];
                    String[] dataarr = data.toString().trim().split("\\|");
                    app_name =  dataarr[0];
                    image_url = dataarr[1];
                    app_url = dataarr[2];
                    app_package = dataarr[3];
                    description = dataarr[4];
                    //Log.d("harsh", "topic"+topic);
                    Log.d("harsh", "data:"+app_name+","+image_url);
                }

            }

        } catch (Exception e) {
            Log.d("Json", "doInBackground: " + e.getMessage());
        }
        return a;
    }
    protected void onPostExecute(ArrayList<String> result) {
       // super.onPostExecute(result);
    }
}
