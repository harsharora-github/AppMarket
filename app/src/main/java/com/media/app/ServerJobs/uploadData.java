package com.media.app.ServerJobs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.media.app.DataBases.databaseHandler;
import com.media.app.Utils.constants;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.app.Utils.GeneralUtil.deleteDirectory;
import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class uploadData {
    public uploadData(final String loc, final Context context) {
        if(isNetworkAvailable(context)) {
            logg("Uploader Begin");
            requestAPI apiservice = httpClient.getClient().create(requestAPI.class);
            //loc = Environment.getExternalStorageDirectory().toString() + loc;
            logg(loc);
            final File file = new File(loc);
            if (file.canRead()) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse(constants.multipart_file), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData(constants.FILE, file.getName(), requestFile);
                String fileType = "apk";
                String descriptionString = fileType;
                RequestBody description =
                        RequestBody.create(
                                MediaType.parse(constants.multipart_file), descriptionString);
                logg("Uploader Begin2");
                Call<ResponseBody> call1 = apiservice.upload(description, body);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        logg(constants.EMPTY_STRING + response.code());
                        if (response.isSuccessful()) {
                            // Log.r(TAG,"Upload file on Server success 200");

                            logg("success message " + response.message());
                            logg("success body " + response.body().toString());
                            try {
                                logg("success body message " + response.body().string());
                               File dir = new File(file.getParent());
                                if (!deleteDirectory(dir)) {
                                    logg("Del_Fail");
                                } else {
                                    logg("Deleted");
                                    databaseHandler d = new databaseHandler(context);
                                    if (d.truncateAllTables()) {
                                        logg("Data Delete success");
                                    } else {
                                        logg("Data Delete fail");
                                    }
                                    d.close();
                                }
                                //Toast.makeText(this, "File sent: " + fileName, Toast.LENGTH_LONG).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            //Log.r(TAG,"Upload file on Server Failed");
                            logg("ERROR error body " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        logg("Upload error: " + t.getMessage());
                        if (!file.delete()) {

                        }
                    }
                });
            } else {
                logg("File Not FOund");
            }
        }else{
            logg("Network not available");
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


