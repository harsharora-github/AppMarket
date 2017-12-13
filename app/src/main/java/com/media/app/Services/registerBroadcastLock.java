package com.media.app.Services;
/*
* Screen Lock  receiver register
* Headset Plug receiver register
* Home screen reciever register
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.crashlytics.android.Crashlytics;
import com.media.app.BroadCastReceivers.HomeWatcher;
import com.media.app.BroadCastReceivers.ScreenUnlockReceiver;
import com.media.app.DataBases.databaseHandler;


import io.fabric.sdk.android.Fabric;

import static com.media.app.Utils.logger.logg;


public class registerBroadcastLock extends Service {
    IBinder mBinder = null;
    BroadcastReceiver mReceiver;
    public registerBroadcastLock() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }
    public void onCreate() {
        logg("Create Monitor Service");
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
         mReceiver = new ScreenUnlockReceiver();
        registerReceiver(mReceiver, filter);
     //   final databaseHandler d = new databaseHandler(this);
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                logg("homekey");
                databaseHandler d = new databaseHandler(getApplicationContext());
               d.insertHOMEKEY("HOME");
                d.close();
            }
            @Override
            public void onHomeLongPressed() {
                logg("recent");
                databaseHandler d = new databaseHandler(getApplicationContext());
                d.insertHOMEKEY("RECENT");
                d.close();
            }
        });
        mHomeWatcher.startWatch();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        logg("Start Service");
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
