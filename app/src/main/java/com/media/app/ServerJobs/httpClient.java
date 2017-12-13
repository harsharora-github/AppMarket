package com.media.app.ServerJobs;



import com.media.app.Utils.constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class httpClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
