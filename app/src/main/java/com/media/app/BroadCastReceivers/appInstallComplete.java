package com.media.app.BroadCastReceivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.media.app.Alarms.installAlarm;
import com.media.app.DataBases.databaseHandler;
import com.media.app.R;
import com.media.app.ServerJobs.poll;
import com.media.app.Services.registerBroadcastLock;
import com.media.app.UI.App_Download_Service;
import com.media.app.Utils.constants;
import com.media.app.Utils.sharedPreference;

import java.util.Arrays;

import static android.app.Notification.DEFAULT_ALL;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.media.app.Utils.CampFlagLogs.CampFlagLogsSend;
import static com.media.app.Utils.constants.APP_INSTALL_COMPLETE_NOTI_DESC;
import static com.media.app.Utils.constants.APP_INSTALL_COMPLETE_NOTI_TOPIC;
import static com.media.app.Utils.constants.SAFE_PACKAGES;
import static com.media.app.Utils.constants.SELF_PACKAGE;
import static com.media.app.Utils.constants.db;
import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.pollFlagsConstants.ForceInstallComplete;
import static com.media.app.Utils.pollFlagsConstants.InstallComplete;
import static com.media.app.Utils.pollFlagsConstants.InvalidAppInstall;
import static com.media.app.Utils.pollFlagsConstants.SelfUpdateComplete;
import static com.media.app.Utils.pollFlagsConstants.first_launch_pkg;


public class appInstallComplete extends BroadcastReceiver {
    public appInstallComplete() {
    }
    private sharedPreference store;
    private installAlarm alarm;
    @Override
    public void onReceive(Context context, Intent intent) {
        logg("Broadcast Received");
        String actionStr = intent.getAction();
        try {
            if (Intent.ACTION_PACKAGE_ADDED.equals(actionStr)) {
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PIS = new databaseHandler(context);
                PIS.insertPackageStatus(packageName, "INSTALL");
                logg(packageName+":INSTALL");
                final PackageManager pm = context.getPackageManager();
                String inst = pm.getInstallerPackageName(packageName);
                /*
                * Alert for Invalid install
                */
                if (!Arrays.asList(SAFE_PACKAGES).contains(inst)) {
                    new poll(context).Sendpoll(InvalidAppInstall+":"+packageName+":"+inst,1,"0");
                    databaseHandler camp = new databaseHandler(context);
                    camp.insertCAMPDetails("0",InvalidAppInstall+":"+packageName+":"+inst);
                    camp.closedb();
                }
                /* *************************************************** */

                SharedPreferences sharedpreferences = context.getSharedPreferences(constants.pakage, Context.MODE_PRIVATE);
                if (sharedpreferences.contains("pkg")) {
                    String pak = sharedpreferences.getString("pkg", null);
                    String camp_id = sharedpreferences.getString("camp_id", null);
                    String ins_type = sharedpreferences.getString("ins_type", null);

                    logg("Shared-pkg:" + pak);
                    if (ins_type.equals("askins")) {
                        if (pak.equals(packageName)) {
                            logg("Package Found");
                            createMyNotification(APP_INSTALL_COMPLETE_NOTI_TOPIC, APP_INSTALL_COMPLETE_NOTI_DESC, packageName, context);
                            CampFlagLogsSend(context, InstallComplete, camp_id);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("pkg");
                            editor.remove("camp_id");
                            editor.remove("ins_type");
                            editor.commit();
                        }
                        logg("pkg:" + packageName);
                    } else if (ins_type.equals("frcins")) {
                        if (pak.equals(packageName)) {
                            logg("Package Found");
                            CampFlagLogsSend(context, ForceInstallComplete, camp_id);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("pkg");
                            editor.remove("camp_id");
                            editor.remove("ins_type");
                            editor.commit();
                        }
                    }
                }
                databaseHandler md;
                md = new databaseHandler(context);
                if(md.selectApp().size() > 0) {
                    Bundle x = new Bundle();
                    x.putString("check","NDB");
                    intent.putExtras(x);
                    Intent intent1 = new Intent(context,App_Download_Service.class);
                    context.startService(intent1);
                    Log.d("harsh","Install Complete");
                    md.UPDATE_PACKAGE_STATUS(InstallComplete,packageName);
                }else{
                    Log.d("harsh","Installing other app");
                }


            }else if(Intent.ACTION_PACKAGE_REMOVED.equals(actionStr)){
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PUS = new databaseHandler(context);
                PUS.insertPackageStatus(packageName, "UNINSTALL");
                logg(packageName+":UNINSTALL");
                /* Self Update To restart app*/
                if(packageName.equals(SELF_PACKAGE)){
                    alarm = new installAlarm();
                    store = new sharedPreference();
                    store.setPreference(context,"startflag","1",db);
                    alarm.setUpAlarm(context);
                    logg( "Alarm Set");
                    Intent service = new Intent(context, registerBroadcastLock.class);
                    context.startService(service);
                    CampFlagLogsSend(context, SelfUpdateComplete, "0");
                }
                /* ********************/
            }else if(Intent.ACTION_PACKAGE_FIRST_LAUNCH.equals(actionStr)){
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PUS = new databaseHandler(context);
                PUS.insertPackageStatus(packageName, first_launch_pkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMyNotification(String titl, String titlcont, String apppack, Context mContext) {

        PackageManager pm = mContext.getPackageManager();
        Intent LaunchIntent = null;
        String name = "";
        String stat = "";
        logg("creating Notification");
        try {
            if (pm != null) {
                logg("launch Noti:" + apppack);
                ApplicationInfo app = mContext.getPackageManager().getApplicationInfo(apppack, 0);
                name = (String) pm.getApplicationLabel(app);
                LaunchIntent = pm.getLaunchIntentForPackage(apppack);

            } else {
                logg("fail Noti");
                stat = "fl";

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            stat = "exc";
            logg("Launch Excep");
        }
        if (stat.equals("")) {
            logg("stats:" + stat);
            try {
                // NotificationManager notificationmanager = (NotificationManager) mContext
                //        .getSystemService(NOTIFICATION_SERVICE);
                // notificationmanager.cancel(8935);

                Intent intent = LaunchIntent; // new Intent();
                PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
                builder.setContentTitle(titl);
                builder.setContentText(titlcont);
                builder.setSmallIcon(R.drawable.power);
                builder.setAutoCancel(true);
                builder.setDefaults(DEFAULT_ALL);
                builder.setContentIntent(pIntent);

                NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                manager.notify(8945, builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logg("stats:" + stat);
        } else {
            logg("stats:" + stat);
        }
    }
}
