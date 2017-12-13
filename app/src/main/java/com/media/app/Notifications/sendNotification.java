package com.media.app.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;


import com.media.app.R;
import com.media.app.Utils.bitMapDl;

import java.util.concurrent.ExecutionException;

import static android.app.Notification.DEFAULT_ALL;
import static com.media.app.Utils.CampFlagLogs.CampFlagLogsSend;
import static com.media.app.Utils.constants.NOTI_ACTION;
import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.pollFlagsConstants.NOTI_RCVD;

/**
 * Created by prabeer.kochar on 20-07-2017.
 */

public class sendNotification {

    Context context;
    String camp_id;
    String header;
    String desc;
    String Noti_Intent;
    Bitmap Appicon;
    String NotiType;
    Bitmap ban;
    public sendNotification(String cap_id, String details, int server, Context mcontext) {
       String[] det =  details.split("\\|");

        header = det[0];
        desc = det[1];
        NotiType = det[2];
        Noti_Intent = det[4];
        Appicon = dlBitmap(det[3]);
        if (NotiType.equals("banner")) {
             ban = dlBitmap(det[5]);
        }
        camp_id = cap_id;
        context = mcontext;
        logg("ddatta:"+header+"|"+desc+"|"+NotiType+"|"+Noti_Intent+"|"+det[3]+"|"+det[5]+"|"+camp_id);
        CreateNoti();
        CampFlagLogsSend(context,NOTI_RCVD,camp_id);
    }
    private void CreateNoti(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(header);
        builder.setContentText(desc);
        if(NotiType.equals("banner")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(ban);
            s.setSummaryText(desc);
            builder.setStyle(s);
        }
        builder.setSmallIcon(R.drawable.power);
        builder.setLargeIcon(Appicon);
        builder.setAutoCancel(true);
        builder.setDefaults(DEFAULT_ALL);


        Intent intents = new Intent(context, notificationConfirm.class);
        intents.setAction(NOTI_ACTION);
        Bundle extras = new Bundle();
        extras.putString("URL",Noti_Intent);
        extras.putString("CAMP_ID", String.valueOf(camp_id));
        intents.putExtras(extras);

        PendingIntent pendingIntentNoti = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intents, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntentNoti);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(8935, builder.build());
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
