package com.media.app.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.media.app.Utils.constants.SELF_PACKAGE;
import static com.media.app.Utils.constants.SERVICE_NAME;


/**
 * Created by prabeer.kochar on 08-09-2017.
 */

public class GeneralUtil {
    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    public static boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(SERVICE_NAME.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static String myAppVersion(Context context) {
       String ver = null;
            try {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(SELF_PACKAGE, PackageManager.GET_META_DATA);
                 ver = pInfo.versionName;
            }catch(Exception e){
                e.printStackTrace();
            }
        return ver;
    }

    public static boolean PackageExists(Context context, String pkg) {
        String ver = null;
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(pkg, PackageManager.GET_META_DATA);
            ver = pInfo.versionName;
            if((ver != null) || (pInfo != null)){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
