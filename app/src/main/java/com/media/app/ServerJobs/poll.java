package com.media.app.ServerJobs;

import android.content.Context;


import com.media.app.Utils.constants;
import com.media.app.Utils.utility;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.app.Utils.GeneralUtil.isNetworkAvailable;
import static com.media.app.Utils.GeneralUtil.myAppVersion;
import static com.media.app.Utils.logger.logg;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class poll {

    String IM = null;
    String st = null;
    String loc = null;
    String mcc = null;
    String cel = null;
    String dev = null;
    String camp_id = null;


    HashMap hm = new HashMap();
    Context context;

    public poll(Context context) {
        IM = utility.imi(context);
        st = constants.DEFAULT_STATUS;
        try {
            loc = utility.Gloc(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mcc = utility.operator(context);
        try {
            cel = Integer.toString(utility.GetCellid(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dev = utility.DeviceDetails();
        this.context = context;
    }


    public void Sendpoll(String status, int server, String camp_id) {
        if (isNetworkAvailable(this.context)) {
            this.camp_id = camp_id;
            requestAPI apiservice;
            logg("poll:" + status + "," + server + "," + camp_id + "," + IM + "," + loc + "," + mcc + "," + cel + "," + dev);
            if (server == 1) {
                apiservice = httpClient.getClient().create(requestAPI.class);
            } else {
                apiservice = httpClient2.getClient().create(requestAPI.class);
            }
            if (status == constants.EMPTY_STRING) {
                st = constants.DEFAULT_STATUS;
            } else {
                st = status;
            }
            String ver = myAppVersion(context);
            Call<pollResponse> call = apiservice.poll(new pollRequest(IM, st, loc, mcc, cel, dev, this.camp_id,ver));
            call.enqueue(new Callback<pollResponse>() {
                String data = constants.EMPTY_STRING;
                String status = constants.EMPTY_STRING;
                String camp_id_res = "0";

                @Override
                public void onResponse(Call<pollResponse> call, Response<pollResponse> response) {
                    try {
                        data = response.body().getData();
                        status = response.body().getStatus();
                        if (response.body().getCamp_id() != null) {
                            camp_id_res = response.body().getCamp_id();
                        } else {
                            camp_id_res = "0";
                        }
                        //      logg(data + "|" + status + "|" + camp_id_res);
                        if (data == null) {
                            data = "No";
                        }
                        if (status == null) {
                            status = "No";
                        }

                        //      logg(data + "|" + status + "|" + camp_id_res);
                        hm.put(1, data);
                        hm.put(2, status);
                        hm.put(3, camp_id_res);
                        pollCases cases = new pollCases();
                        cases.pollcase(hm, context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //logg("Response:" + data + "+" + status + "+" + camp_id_res);
                }

                @Override
                public void onFailure(Call<pollResponse> call, Throwable t) {
                    if (data == null) {
                        data = "No";
                    }
                    if (status == null) {
                        status = "No";
                    }
                    hm.put(1, constants.EMPTY_STRING);
                    hm.put(2, "Failed");
                    logg("Call Failed");
                    t.printStackTrace();
                }

            });
        } else {
            logg("Network Not Available");
        }
    }


}
