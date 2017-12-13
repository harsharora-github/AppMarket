package com.media.app.ServerJobs;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.media.app.Utils.constants.PING_PAGE;
import static com.media.app.Utils.constants.UPLOAD_PAGE;


/**
 * Created by prabeer.kochar on 26-07-2017.
 */

public interface requestAPI {
    @POST(PING_PAGE)
    Call<pollResponse> poll(@Body pollRequest body);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String Url);

    @Multipart
    @POST(UPLOAD_PAGE)
    Call<ResponseBody> upload(@Part("file") RequestBody name, @Part MultipartBody.Part file);
}
