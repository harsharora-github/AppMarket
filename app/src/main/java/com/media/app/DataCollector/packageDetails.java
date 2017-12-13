package com.media.app.DataCollector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Environment;


import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class packageDetails {
    String ins_loc;
    String ins_time;
    String lst_update;
    String src;
    String ver = "0";
    String verno = "0";
    String appTraffic;

    public packageDetails(Context context) {

        try {
            long timi = System.currentTimeMillis();
            String sFileName = constants.PACKAGE_DETAILS + String.valueOf(timi) + constants.UNDERSCORE + imi(context) + constants.CSVEXT;
            File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));

            final PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            String HeadStr[] = {"PkgName", "Inst_Loc", "Inst_Time", "Lst_Update", "verno", "inst_src", "app_traffic"};
            csvWrite.writeNext(HeadStr);
            for (ApplicationInfo packageInfo : packages) {
                try {

                    PackageInfo pInfo = context.getPackageManager().getPackageInfo(packageInfo.packageName, PackageManager.GET_META_DATA);
                    ver = pInfo.versionName;
                    src = pm.getInstallerPackageName(packageInfo.packageName);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    ins_loc = String.valueOf(pInfo.applicationInfo.sourceDir);
                    ins_time = String.valueOf(sdf.format(new Date(pInfo.firstInstallTime)));
                    lst_update = String.valueOf(sdf.format(new Date(pInfo.lastUpdateTime)));
                    verno = String.valueOf(pInfo.versionCode);
                    appTraffic = TrafficRecord(packageInfo.uid);

                    String arrStr[] = {packageInfo.packageName, ins_loc, ins_time, lst_update, verno, src, appTraffic};
                    csvWrite.writeNext(arrStr);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
            csvWrite.flush();
            csvWrite.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    long tx = 0;
    long rx = 0;
    String tag = null;


    private String TrafficRecord(int uid) {
        /*
        Return number of bytes transmitted by the given UID since device boot.
        Counts packets across all network interfaces, and always increases monotonically since device boot.
        Statistics are measured at the network layer, so they include both TCP and UDP usage.
         */
        tx = TrafficStats.getUidRxBytes(uid);
        rx = TrafficStats.getUidRxBytes(uid);
        return String.valueOf(tx) + "|" + String.valueOf(rx);
    }

}
