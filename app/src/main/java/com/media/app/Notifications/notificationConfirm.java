package com.media.app.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.media.app.Utils.CampFlagLogs.CampFlagLogsSend;
import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.pollFlagsConstants.INSTALL_BAN_YES;
import static com.media.app.Utils.pollFlagsConstants.INSTALL_NO;
import static com.media.app.Utils.pollFlagsConstants.INSTALL_YES;
import static com.media.app.Utils.utility.imi;


public class notificationConfirm extends BroadcastReceiver {
    public notificationConfirm() {
    }
    private static final String YES_ACTION = "YES_ACTION";
    private static final String NO_ACTION = "NO_ACTION";
    private static final String NOTI_ACTION = "NOTI_ACTION";
    private static String IMEI = "";
    private static int server = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        String camp_id;
        String url;
        if(extras == null) {
            camp_id= null;
            url = null;
        } else {
            camp_id= extras.getString("CAMP_ID");
            url = extras.getString("URL");
        }

        IMEI = imi(context);

        if(YES_ACTION.equals(action)) {
            CampFlagLogsSend(context,INSTALL_YES , camp_id);
            logg("Pressed YES");
        } else if(NO_ACTION.equals(action)) {
            CampFlagLogsSend(context,INSTALL_NO , camp_id);
            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(NOTIFICATION_SERVICE);
            notificationmanager.cancel(8935);
            logg("Pressed No");
        }else if(NOTI_ACTION.equals(action)){
            Intent intents = new Intent(Intent.ACTION_VIEW);
            intents.setData(Uri.parse(url));
            PendingIntent pi = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intents, 0);
            try {
                pi.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            CampFlagLogsSend(context,INSTALL_BAN_YES , camp_id);
            logg("Pressed Noti"+"|url:"+url+"|campid:"+camp_id);
        }
    }
}
