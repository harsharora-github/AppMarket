package com.media.app.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.media.app.Utils.sharedPreference;

import static com.media.app.Utils.constants.AlarmDelay;
import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 20-07-2017.
 */

public class installAlarm {
   // SharedPreferences sharedpreferences;
    private sharedPreference store;
    public void setUpAlarm(Context context) {
        //store = new sharedPreference();
        logg("setUpAlarm: Alarm2");
       // store.setPreference(context,"startflag","1",db);
        Intent intent = new Intent(context, setAlarm.class);
        PendingIntent pending_intent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarm_mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), AlarmDelay, pending_intent);
    }
}
