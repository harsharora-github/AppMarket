package com.media.app.Services;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;

import com.crashlytics.android.Crashlytics;
import com.media.app.DataBases.databaseHandler;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import static android.os.Process.myUid;
import static com.media.app.Utils.logger.logg;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class appMonitorService extends IntentService {

    public appMonitorService() {
        super("appMonitorService");
    }

    Collection<String> list = new ArrayList();
    Collection<String> list2 = new ArrayList();
    Collection<String> comp_list = new ArrayList();

    @Override
    protected void onHandleIntent(Intent intent) {
        Fabric.with(this, new Crashlytics());
        if (intent != null) {
            int x = 1;
            int y = 0;
            do {
                SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences("monitor", Context.MODE_PRIVATE);
                String mt = sharedpreferences.getString("screen", null);
                if (mt.equals("on")) {
                    startCapture();
                    //   logg("list:"+String.valueOf(list));
                    // logg("list2:"+String.valueOf(list2));
                    comp_list.clear();

                    for (String str : list) {
                        if (!list2.contains(str)) {
                            comp_list.add(str);
                        }
                    }

                    for (String ls : comp_list) {
                        String[] l = ls.split(Pattern.quote("|"));
                        //logg(l[0] + "~" + l[1] + "~" + l[2]);
                        databaseHandler m = new databaseHandler(this);
                        m.insertPackageMonitor(l[0], l[1], l[2]);
                        m.close();
                    }

                    logg("list_comp:" + String.valueOf(comp_list));
                    //  list2.clear();
                } else {
                    this.stopSelf();
                    break;
                }
                y++;
                logg("Count:" + Integer.toString(y));
            } while (x == 1);
            logg("Broken");
            this.stopSelf();
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void startCapture() {

        logg("Start Capture");
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                time - 1000 * 1000, time);
        if (list.isEmpty()) {
            for (int i = 0; i < appList.size(); i++) {
                String AppStatus = "BACKGROUND";
                if (isAppForeground(appList.get(i).getPackageName())) {
                    AppStatus = "FOREGROUND";
                }
                list.add(appList.get(i).getPackageName() + "|" + AppStatus + "|" + appList.get(i).getLastTimeUsed());
            }
            //  logg(appList.get(i).getPackageName() + "," + Long.toString(appList.get(i).getTotalTimeInForeground()) + "," + AppStatus);

        } else {
            list2.clear();
            list2 = new ArrayList<String>(list);
            list.clear();
            for (int i = 0; i < appList.size(); i++) {
                String AppStatus = "BACKGROUND";
                if (isAppForeground(appList.get(i).getPackageName())) {
                    AppStatus = "FOREGROUND";
                }
                list.add(appList.get(i).getPackageName() + "|" + AppStatus + "|" + appList.get(i).getLastTimeUsed());
            }
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private boolean checkForPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, myUid(), context.getPackageName());
        return mode == MODE_ALLOWED;
    }


    private boolean isAppForeground(String packageName) {
        ActivityManager am = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);


        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        PackageInfo pInfo = null;
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            /*
            try {
                pInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            */
            //Log.i(TAG, process.processName);
            if (process.processName.startsWith(packageName)) {
                //boolean isBackground = process.importance ! = ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                /// /boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                // logg("process_priority:"+Integer.toString(process.importance));
                if ((process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {
                    return true;
                } else
                    return false;
            }
        }
        return false;

    }


}
