package com.media.app.Utils;

import android.content.Context;

import com.media.app.DataBases.databaseHandler;
import com.media.app.ServerJobs.poll;


/**
 * Created by prabeer.kochar on 09-11-2017.
 */

public class CampFlagLogs {

    public static boolean  CampFlagLogsSend(Context context, String Flag, String camp_id){
        try {
            new poll(context).Sendpoll(Flag, 1, camp_id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        try {
            new databaseHandler(context).insertCAMPDetails(camp_id, Flag);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
