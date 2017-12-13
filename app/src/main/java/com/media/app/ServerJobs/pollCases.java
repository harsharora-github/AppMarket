package com.media.app.ServerJobs;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;


import com.media.app.DataCollector.CnfInstall;
import com.media.app.Notifications.installNotification;
import com.media.app.Notifications.sendNotification;
import com.media.app.Services.dataSender;
import com.media.app.Utils.bitMapDl;
import com.media.app.Utils.constants;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.media.app.Utils.CampFlagLogs.CampFlagLogsSend;
import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.pollFlagsConstants.ASK_INSTALL;
import static com.media.app.Utils.pollFlagsConstants.CLEAN_DB;
import static com.media.app.Utils.pollFlagsConstants.DO_CONFIGURATION;
import static com.media.app.Utils.pollFlagsConstants.EXECUTE_CMD;
import static com.media.app.Utils.pollFlagsConstants.FORCE_INSTALL;
import static com.media.app.Utils.pollFlagsConstants.INSTALL_NOTI_RECIEVED;
import static com.media.app.Utils.pollFlagsConstants.INSTALL_REQ_APP;
import static com.media.app.Utils.pollFlagsConstants.MAKE_NOTIFICATION;
import static com.media.app.Utils.pollFlagsConstants.PULL_DATA;
import static com.media.app.Utils.pollFlagsConstants.SEND_SMS;


/**
 * Created by prabeer.kochar on 05-08-2017.
 */

public class pollCases {
    String loc1 = constants.AppFolder;

    /*
        hm.put(1, data);
        hm.put(2, status);
        hm.put(3, camp_id_res);
            */
    public void pollcase(HashMap hash, Context context) {
        if (!hash.isEmpty()) {
            String val = (String) hash.get(2);
            String camp_id = (String) hash.get(3);
            String adt = (String) hash.get(1);
           // logg("adtRAW:" + adt);
            String[] adt_arr = adt.split("\\|");
           // logg("adtarr:" + adt_arr.toString());
            String uri;
            String pkg;
            switch (val) {
                case MAKE_NOTIFICATION:
                    String data = (String) hash.get(1);
                    new sendNotification(camp_id, data, 1, context);
                    break; // optional
                case ASK_INSTALL:
                    // Statements.
                    String data1 = (String) hash.get(1);
                    logg("icon:"+adt_arr[2]);
                    new installNotification(context).addNotification( data1, String.valueOf(camp_id));
                    CampFlagLogsSend(context,INSTALL_NOTI_RECIEVED , camp_id);
                    break; // optional
                // You can have any number of case statements.
                case FORCE_INSTALL:
                    logg("Arr_Length:-" + Integer.toString(adt_arr.length));
                    if (adt_arr.length > 1) {
                        uri = adt_arr[0];
                        pkg = adt_arr[1];
                        logg("adt:" + adt_arr[0] + "|" + adt_arr[1]);
                        camp_id = (String) hash.get(3);
                        new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                    }
                    break; // optional
                case PULL_DATA:
                    Intent newIntent = new Intent(context, dataSender.class);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startService(newIntent);
                    break; // optional
                case DO_CONFIGURATION:
                    // Statements
                    break; // optional
                case CLEAN_DB:
                    // Statements
                    break; // optional
                case INSTALL_REQ_APP:
                    if (adt_arr.length > 1) {
                        uri = adt_arr[0];
                        pkg = adt_arr[1];
                        camp_id = (String) hash.get(3);
                        NotificationManager notificationmanager = (NotificationManager) context
                                .getSystemService(NOTIFICATION_SERVICE);
                        notificationmanager.cancel(8935);
                        new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                        // Statements
                    }
                    break; // optional
                case EXECUTE_CMD:
                    // Statements
                    break; // optional
                case SEND_SMS:
                    /*
                    SMSManager y = new SMSManager();
                    y.sendsms("09821490074", "Test Message", context);
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y.deleteSMS(context, "Test Message", "09821490074");
                    // Statements
                    */
                    break; // optional
                default: // Optional
                    // Statements

            }

        }
    }

    private Bitmap dlBitmap(String ur) {
        Bitmap b = null;
        try {
            b  = new bitMapDl().execute(ur).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return b;
    }
}
