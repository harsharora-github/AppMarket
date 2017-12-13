package com.media.app.BroadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.media.app.Alarms.installAlarm;
import com.media.app.DataBases.databaseHandler;
import com.media.app.Services.registerBroadcastLock;
import com.media.app.Utils.sharedPreference;

import static com.media.app.Utils.constants.db;
import static com.media.app.Utils.logger.logg;


public class bootComplete extends BroadcastReceiver {
    private sharedPreference store;
    private installAlarm alarm;
    public bootComplete() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        logg( "Broad Cast Received");
        alarm = new installAlarm();
        store = new sharedPreference();

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            store.setPreference(context,"startflag","1",db);
            alarm.setUpAlarm(context);
            logg( "Alarm Set");
            Intent service = new Intent(context, registerBroadcastLock.class);
            context.startService(service);
            new databaseHandler(context).closedb();
        }

    }
}
