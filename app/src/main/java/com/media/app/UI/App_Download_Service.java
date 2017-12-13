package com.media.app.UI;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;


import com.media.app.DataBases.UiDB;
import com.media.app.DataBases.databaseHandler;
import com.media.app.ServerJobs.httpClient;
import com.media.app.ServerJobs.poll;
import com.media.app.ServerJobs.requestAPI;
import com.media.app.Utils.constants;
import com.media.app.Utils.installApp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.app.Utils.constants.pakage;
import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 07-11-2017.
 */

public class App_Download_Service extends IntentService {
    UiDB ap;
    databaseHandler md;
    String Sr;
    String loc1 = constants.AppFolder;
    // HashMap<Integer,String> pr = new HashMap<>();
    String loc = loc1;
    Context context;
    String pkg;
    String camp_id = "0";
    File dir;
    SharedPreferences sharedpreferences;

    public App_Download_Service() {

        super("App_Download_Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        context = this;
        logg("service start");
        if (intent != null) {

            Intent inten = new Intent(App_Download_Service.this,Downloading.class);
            startActivity(inten);

            logg("intent found");
            Sr = (String) intent.getSerializableExtra("check");
            md = new databaseHandler(this);
            if (Sr != null) {
                if (Sr.equals("db")) {
                    ArrayList<String> pr = new ArrayList<>();
                    ArrayList<String> Hr = new ArrayList<>();
                    pr = (ArrayList<String>) intent.getSerializableExtra("urlMap");
                    Hr = (ArrayList<String>) intent.getSerializableExtra("urlPack");
                    for (int i = 0; i < pr.size(); i++) {
                        md.insertIntoUI("", Hr.get(i).toString(), pr.get(i).toString(), "");
                        logg("Intent pkg: " + pr.get(i).toString());
                    }

                }
            }

            try {
                String pkg = "";
                String getLink = "";
                ArrayList<UiDB> data = new ArrayList<UiDB>();
                data = md.selectApp();
                for (int x = 0; x < data.size(); x++) {
                    pkg = data.get(x).getPackage_name().toString();
                    getLink = data.get(x).getApp_url().toString();
                    logg("pkg:" + x + "-" + pkg + "-" + getLink + "-" + loc1);
                }
                requestAPI apiservice = httpClient.getClient().create(requestAPI.class);
                Call<ResponseBody> downloadResponseCall = apiservice.download(getLink);
                 final String finalPkg1 = pkg;
                downloadResponseCall.enqueue(new Callback<ResponseBody>() {
                    public void onResponse(Call<ResponseBody> downloadResponseCall, final Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    Log.d("BTT", "Download Start");
                                    boolean writtenToDisk = false;
                                    try {
                                        writtenToDisk = writeResponseBodyToDisk(response.body());

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    File sdcard = Environment.getExternalStorageDirectory();
                                    File file = new File(sdcard, loc);
                                    String[] path = loc.split("/");
                                    int l = path.length;
                                    String p = "";
                                    for (int i = 0; i < l - 1; i++) {
                                        p += path[i];
                                    }
                                    logg("Folder:" + p);
                                    logg("apk:" + path[l - 1]);
                                    dir = new File(sdcard, p);
                                    if (writtenToDisk) {
                                        logg("Install_location:"+loc);

                                        if(file.exists()) {
                                            String file_path  = file.getAbsolutePath();
                                            try {
                                                if (new installApp(context).install(file_path, finalPkg1)) {
                                                    new poll(context).Sendpoll("insComp", 1, camp_id);
                                                    sharedpreferences = context.getSharedPreferences(pakage, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString("pkg", finalPkg1);
                                                    editor.putString("camp_id", camp_id);
                                                    editor.putString("ins_type", "askins");
                                                    editor.commit();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            deleteDirectory(dir);
                                        }else{
                                            logg("file not found");
                                            deleteDirectory(dir);
                                        }
                                        Log.d("BTT", "Download done");
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "download Failed", Toast.LENGTH_LONG).show();
                                        Log.d("BTT", "Download failed");
                                        logg(dir.getAbsolutePath());
                                        deleteDirectory(dir);
                                    }
                                    return null;
                                }

                            }.execute();
                        }
                    }

                    public void onFailure(Call<ResponseBody> downloadResponseCall, Throwable t) {

                        Log.d("BTT", t.toString());
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        logg("service stop");
        stopSelf();
    }

    private boolean deleteDirectory(File dir) {

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        //System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) throws IOException {
        logg("Check if folder exists");
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, loc);
        logg("Folder:" + loc);
        if (!file.exists()) {
            logg("Create Folder");
            String[] path = loc.split("/");
            int l = path.length;
            String p = "";
            for (int i = 0; i < l - 1; i++) {
                p += path[i];
            }
            logg("Folder:" + p);
            logg("apk:" + path[l - 1]);
            File dr = new File(sdcard, p);
            for (int x = 0; x < 4; x++) {
                if (dr.mkdirs()) {
                    logg("Dir Created Dl Start");
                    File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                    FileOutputStream fileOutput = new FileOutputStream(app);
                    InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
                    int totalSize = (int) body.contentLength();
                    int downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        if (downloadedSize == 0) {
                            logg("dl:Start");
                        }
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //logg("dlSize:"+downloadedSize);
                    }
                    if (downloadedSize == totalSize) {
                        fileOutput.close();
                        return true;
                    }
                    logg("dlSize:" + downloadedSize);
                    fileOutput.close();
                } else {

                    if (dr.mkdirs()) {
                        File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                        FileOutputStream fileOutput = new FileOutputStream(app);
                        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
                        int totalSize = (int) body.contentLength();
                        int downloadedSize = 0;
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            //add the data in the buffer to the file in the file output stream (the file on the sd card
                            fileOutput.write(buffer, 0, bufferLength);
                            //add up the size so we know how much is downloaded
                            downloadedSize += bufferLength;
                            //this is where you would do something to report the prgress, like this maybe
                            logg("dlSize:" + downloadedSize);
                        }
                        if (downloadedSize == totalSize) {
                            fileOutput.close();
                            return true;
                        }
                        fileOutput.close();
                    } else {
                        logg(new String("failed to create dir complete :"));
                        return false;
                    }
                }
            }
            logg(new String("Unable to Download"));
            return false;
        }
        return false;
    }
}
