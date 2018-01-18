package com.media.app.UI;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.media.app.R;
import com.media.app.UI.Adapters.More_Adapter;
import com.media.app.UI.Models.More_Model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import static com.media.app.UI.Adapters.More_Adapter.pack_name;
import static com.media.app.UI.Adapters.More_Adapter.urlList;

public class More_Activity extends AppCompatActivity {

    ArrayList<String> data4 = new ArrayList<>();
    private Toolbar toolbar;
    String topic = null;
    String app_name = null;
    String image_url = null;
    String app_url = null;
    String app_package = null;
    String dataa = null;
    String DB = "db";
  ArrayList<More_Model> singleItem = null;
    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_);

        toolbar = (Toolbar) findViewById(R.id.more_toolbar);

        singleItem = new ArrayList<More_Model>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }

        try {
            data4= new  Json_Fetch().execute().get();
            Log.d("harshmore", "onCreate: "+data4.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        createDummyData();


        lLayout = new GridLayoutManager(More_Activity.this, 3);

        RecyclerView rView = (RecyclerView)findViewById(R.id.more_my_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        More_Adapter rcAdapter = new More_Adapter(this, singleItem);
        rView.setAdapter(rcAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.more, menu);

     //   final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
    //    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.installm) {

            Toast.makeText(getApplicationContext(), "This will install the selected Application ", Toast.LENGTH_SHORT).show();

            Intent intentm = new Intent(More_Activity.this,App_Download_Service_more.class);
            startService(intentm);

            Bundle x = new Bundle();

            x.putStringArrayList("urlMap",urlList);
            x.putStringArrayList("urlPack", pack_name);
            x.putString("check",DB);
            intentm.putExtras(x);
            startService(intentm);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void createDummyData() {
      //  ArrayList<More_Model> singleItem = null;
     //   More_Model dm = null;
        int i =0;
        for (Object object: data4) {

            String[] separated =  object.toString().trim().split("\\-");
            if((topic == null) || !topic.equals(separated[0])){
                topic = separated[0];
              //  dm = new More_Model();
              //  singleItem = new ArrayList<More_Model>();

                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "dataarr: "+dataa);
                app_name =  dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
                if (topic != null) {
                    Log.d("harsh", "topic!null: "+app_name);
                    singleItem.add(new More_Model(app_name,image_url,app_url,app_package));

                   // allSampleData.add(singleItem);
                }else {
                    Log.d("harsh", "topic-null: "+app_name);
                    singleItem.add(new More_Model(app_name, image_url,app_url,app_package));
                }

            }else{
                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "datasec: "+dataa);
                app_name =  dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
                if(singleItem != null) {
                    singleItem.add(new More_Model(app_name, image_url,app_url,app_package));
                }

            }
            i++;
        }
    }
}
