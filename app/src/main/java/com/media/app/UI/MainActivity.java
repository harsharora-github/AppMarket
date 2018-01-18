package com.media.app.UI;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.media.app.R;
import com.media.app.UI.Adapters.RecyclerViewDataAdapter;
import com.media.app.UI.Models.SectionDataModel;
import com.media.app.UI.Models.SingleItemModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.media.app.UI.Adapters.SectionListDataAdapter.pack_name;
import static com.media.app.UI.Adapters.SectionListDataAdapter.urlList;
import static com.media.app.Utils.GeneralUtil.isNetworkAvailable;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    String topic = null;
    String app_name = null;
    String image_url = null;
    String app_url = null;
    String app_package = null;
    String description = null;
    String dataa = null;
    String DB = "db";
    String TAG = "btt";


    ArrayList<String> data3 = new ArrayList<>();



    ArrayList<SectionDataModel> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (isNetworkAvailable(this)){

            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar);

            allSampleData = new ArrayList<SectionDataModel>();

            if (toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.setTitle("G PlayStore");

            }




            try {
                data3= new  Json_Fetch().execute().get();
                Log.d("harsh", "onCreate: "+data3.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            createDummyData();

            RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

            my_recycler_view.setHasFixedSize(true);

            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);



        }
        //do whatever you want to do
    else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            }
            catch(Exception e)
            {
                Log.d(TAG, "Show Dialog: "+e.getMessage());
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
      //  final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
     //   searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.install) {

            Toast.makeText(getApplicationContext(), "This will install the selected Application ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this,App_Download_Service.class);

            Bundle x = new Bundle();

            x.putStringArrayList("urlMap",urlList);
            x.putStringArrayList("urlPack", pack_name);
            x.putString("check",DB);
            intent.putExtras(x);
            startService(intent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    public void createDummyData() {
        ArrayList<SingleItemModel> singleItem = null;
        SectionDataModel dm = null;
        int i =0;
        for (Object object: data3) {

            String[] separated =  object.toString().trim().split("\\-");
            if((topic == null) || !topic.equals(separated[0])){
                topic = separated[0];
                dm = new SectionDataModel();
                singleItem = new ArrayList<SingleItemModel>();
                dm.setHeaderTitle(topic);
                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "dataarr: "+dataa);
                app_name =  dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
                description = dataarr[4];
               if (topic != null) {
                   Log.d("harsh", "topic!null: "+app_name);
                    singleItem.add(new SingleItemModel(app_name,image_url,app_url,app_package,description));
                    dm.setAllItemsInSection(singleItem);
                    allSampleData.add(dm);
                }else {
                   Log.d("harsh", "topic-null: "+app_name);
                   singleItem.add(new SingleItemModel(app_name, image_url,app_url,app_package,description));
               }

            }else{
                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "datasec: "+dataa);
                app_name =  dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
                description = dataarr[4];
                if(singleItem != null) {
                    singleItem.add(new SingleItemModel(app_name, image_url,app_url,app_package,description));
                }
                /*
                if(i == data3.size()-1){
                    Log.d("harsh", "last-line: "+app_name);
                    if(dm != null) {
                        dm.setAllItemsInSection(singleItem);
                        allSampleData.add(dm);
                    }
                }
                */
            }
            i++;
        }



        }


    }
