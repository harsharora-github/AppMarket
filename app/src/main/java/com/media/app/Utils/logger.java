package com.media.app.Utils;

import android.util.Log;

import static com.media.app.Utils.constants.LogEnable;


/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class logger {
    public logger(){

    }
    private static String TAG = "cmds";
    private static boolean enable = LogEnable;
    public static void logg(String val){
        if(enable) {
            Log.d(TAG, ":btt: " + val);
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
