package com.media.app.BroadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.media.app.DataBases.databaseHandler;

import static com.media.app.Utils.logger.logg;


public class lowBattery extends BroadcastReceiver {
    public lowBattery() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            logg("Low Battery Broadcast2");

            databaseHandler LBD = new databaseHandler(context);
            logg("Low Battery Broadcast");
            LBD.insertLBdata("LOW_BATTERY");

        }
    }

}

