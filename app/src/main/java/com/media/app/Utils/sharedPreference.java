package com.media.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prabeer.kochar on 20-07-2017.
 */

public class sharedPreference {
    private SharedPreferences sharedpreferences;
    public static final String pollflag = "pollflag" ;
    public void setPreference(Context context, String key, String data, String db){
        sharedpreferences =  context.getSharedPreferences(db, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public String getPreference(Context context, String key, String db){
        sharedpreferences =  context.getSharedPreferences(db, Context.MODE_PRIVATE);
        SharedPreferences sharedpreferences = context.getSharedPreferences(db, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, null);
    }

    public void remPreference(Context context, String key, String db){
        sharedpreferences =  context.getSharedPreferences(db, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
