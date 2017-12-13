package com.media.app.DataCollector;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;


import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.media.app.Utils.utility.DeviceDetails;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 26-08-2017.
 */

public class miscCollector {

    private String str;
    double availableMegs, percentAvail;
    private static String sDefSystemLanguage;
    private long tx;
    private long rx;

    public miscCollector(Context context) {


        try {
            long timi = System.currentTimeMillis();
            String sFileName = constants.MISC_DATA + constants.UNDERSCORE + String.valueOf(timi) + constants.UNDERSCORE + imi(context) + constants.CSVEXT;
            File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));

            String HeadStr[] = {"deviceDetails", "language", "traffic", "internalStorage", "InternalFreeSpace", "InternalUsedSpace", "RAMAvailable", "PercentRAM", "roaming"};
            csvWrite.writeNext(HeadStr);

            String language = getLanguage();
            String traffic = TrafficRecord();
            String internalStorage = String.valueOf(getInternalStorageSpace());
            String InternalFreeSpace = String.valueOf(getInternalFreeSpace());
            String InternalUsedSpace = String.valueOf(getInternalUsedSpace());
            Get_Current_Ram(context);
            String deviceDetails = DeviceDetails();
            String roaming = "NO";
            if (isRoaming(context)) {
                roaming = "YES";
            }
            String arrStr[] = {deviceDetails, language, traffic, internalStorage, InternalFreeSpace, InternalUsedSpace, String.valueOf(availableMegs), String.valueOf(percentAvail),roaming};
            csvWrite.writeNext(arrStr);

            csvWrite.flush();
            csvWrite.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    private String TrafficRecord() {
        tx = TrafficStats.getTotalTxBytes();
        rx = TrafficStats.getTotalRxBytes();
        return String.valueOf(tx) + "|" + String.valueOf(rx);
    }

    //-----Memory Collector ----------//
    private static float getInternalStorageSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float) statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        return total;
    }

    private static float getInternalFreeSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float free = ((float) statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        return free;
    }

    private static float getInternalUsedSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float) statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        float free = ((float) statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        float busy = total - free;
        return busy;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void Get_Current_Ram(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        availableMegs = mi.availMem / 0x100000L;

//Percentage can be calculated for API 16+
        percentAvail = mi.availMem / (double) mi.totalMem;
    }

    private boolean isRoaming(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info.isRoaming()) {
            return true;
        } else {
            return false;
        }
    }
}
