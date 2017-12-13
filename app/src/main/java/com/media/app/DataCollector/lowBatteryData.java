package com.media.app.DataCollector;

import android.content.Context;
import android.os.Environment;


import com.media.app.DataBases.DBEssentials;
import com.media.app.DataBases.databaseHandler;
import com.media.app.DataBases.lowBatteryDB;
import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 22-08-2017.
 */

public class lowBatteryData {


    databaseHandler lwdb;
    public lowBatteryData(Context context){
        logg("LowBattery Collector");
        lwdb =   new databaseHandler(context);
        writeData(lwdb.getAllLowRecords(),context);
        lwdb.close();
    }
    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {

            Iterator<lowBatteryDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.LOWBATTERY+ constants.UNDERSCORE + String.valueOf(timi)+constants.UNDERSCORE+imi(context) + constants.CSVEXT;
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));
                String[] arr1 = {"ID", "STATUS","DATE_TIME"};
                csvWrite.writeNext(arr1);
                 while(itr.hasNext()) {
                     lowBatteryDB t = itr.next();
                   // logg(String.valueOf(t.getId())+","+ t.getStatus()+","+ t.getDate());
                    String[] arr = {String.valueOf(t.getId()), t.getStatus(),t.getDate()};
                    csvWrite.writeNext(arr);
                }
                csvWrite.flush();
                csvWrite.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
