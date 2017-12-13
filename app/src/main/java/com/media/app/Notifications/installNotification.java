package com.media.app.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;


import com.media.app.R;
import com.media.app.Utils.bitMapDl;

import java.util.concurrent.ExecutionException;

import static android.app.Notification.DEFAULT_ALL;
import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 10-08-2017.
 */

public class installNotification {
    private static final String YES_ACTION = "YES_ACTION";
    private static final String NO_ACTION = "NO_ACTION";
    Context mContext;
    String header;
    String desc;
    String Noti_Intent;
    Bitmap Appicon;
    String NotiType;
    Bitmap ban;

    public installNotification(Context context) {
        mContext = context;
    }

    public void addNotification(String data1, String camp_id) {
        String[] det =  data1.split("\\|");
        header = det[0];
        desc = det[1];
        NotiType = det[2];
        Noti_Intent = det[4];
        Appicon = dlBitmap(det[3]);
        if (NotiType.equals("banner")) {
            ban = dlBitmap(det[5]);
        }


        logg("ddatta:"+header+"|"+desc+"|"+NotiType+"|"+Noti_Intent+"|"+det[3]+"|"+det[5]+"|"+camp_id);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.customnotification);
        Intent intent = new Intent(mContext, notificationConfirm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, intent, 0);
        // Set Notification Title
        String strtitle = header;
        // Set Notification Text
        String strtext = desc;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
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
        builder.setContent(remoteViews);
        builder.setDeleteIntent(pendingIntent);
        remoteViews.setImageViewResource(R.id.imagenotileft,R.drawable.power);
        remoteViews.setImageViewBitmap (R.id.imagenotiright,Appicon);

        remoteViews.setTextViewText(R.id.title,strtitle);
        remoteViews.setTextViewText(R.id.text,strtext);

        Intent intents = new Intent();
        intents.putExtra("CAMP_ID", camp_id);
        intents.setAction(YES_ACTION);
        PendingIntent pendingIntentNoti = PendingIntent.getBroadcast(mContext, (int) System.currentTimeMillis(), intents, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntentNoti);


        Intent yesReceive = new Intent();
        yesReceive.putExtra("CAMP_ID", camp_id);
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(mContext, (int) System.currentTimeMillis(), yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action InstAction = new  NotificationCompat.Action.Builder(android.R.color.transparent, "OK", pendingIntentYes).build();
        builder.addAction(InstAction);

        Intent noReceive = new Intent();
        noReceive.putExtra("CAMP_ID", camp_id);
        noReceive.setAction(NO_ACTION);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(mContext, (int) System.currentTimeMillis(), noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action CancAction = new  NotificationCompat.Action.Builder(android.R.color.transparent, "Cancel", pendingIntentNo).build();
        builder.addAction(CancAction);


        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(8935, builder.build());

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
