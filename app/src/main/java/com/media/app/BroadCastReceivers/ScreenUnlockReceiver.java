package com.media.app.BroadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.media.app.DataBases.databaseHandler;
import com.media.app.Services.appMonitorService;

import static com.media.app.Utils.logger.logg;


public class ScreenUnlockReceiver extends BroadcastReceiver {
    public ScreenUnlockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            SharedPreferences sharedpreferences =  context.getSharedPreferences("monitor", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("screen", "on");
            editor.commit();
            Intent service = new Intent(context, appMonitorService.class);
            context.startService(service);
            databaseHandler d = new databaseHandler(context);
            d.insertLockMonitor("UNLOCK");
            d.close();
            logg("unlock");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            SharedPreferences sharedpreferences =  context.getSharedPreferences("monitor", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("screen", "off");
            editor.commit();
            databaseHandler d = new databaseHandler(context);
            d.insertLockMonitor("LOCK");
            d.close();
            logg("lock");
        }

        if(Intent.ACTION_HEADSET_PLUG.equals(action)){
            int state = intent.getIntExtra("state", -1);
            databaseHandler d = new databaseHandler(context);
            switch (state) {
                case 0:
                    d.insertEARJACK("OUT");
                    d.close();
                    logg( "Headset unplugged");
                    break;
                case 1:
                    d.insertEARJACK("IN");
                    d.close();
                   logg( "Headset plugged");
                    break;
            }
        }
    }
}
