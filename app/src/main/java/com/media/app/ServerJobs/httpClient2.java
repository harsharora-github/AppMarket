package com.media.app.ServerJobs;



import com.media.app.Utils.constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prabeer.kochar on 10-08-2017.
 */

public class httpClient2 {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(constants.BASE_URL2).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
