package com.media.app.UI;

import android.util.Log;

/**
 * Created by harsh.arora on 9/7/2017.
 */

public class Logger {

    public static final boolean LogEnable = true;

    public Logger(){

    }
    private static String TAG = "cmds";
    private static boolean enable = LogEnable;
    public static void logg(String val){
        if(enable) {
            Log.d(TAG, "harsh: " + val);
        }
    }
    private String getClassName() {
        Class<?> enclosingClass = getClass().getEnclosingClass();
        if (enclosingClass != null) {
            return  enclosingClass.getName();
        } else {
            return getClass().getName();
        }
    }
}
