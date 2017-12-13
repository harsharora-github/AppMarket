package com.media.app.DataCollector;

import android.content.Context;
import android.os.Environment;


import com.media.app.DataBases.DBEssentials;
import com.media.app.DataBases.campDetailsDB;
import com.media.app.DataBases.databaseHandler;
import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 05-12-2017.
 */

public class campDetailsCollector {

    databaseHandler lwdb;
    public campDetailsCollector(Context context){
        logg("Camp Collector");
        lwdb =   new databaseHandler(context);
        writeData(lwdb.getAllCAMPDetails(),context);
        lwdb.close();
    }

    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {

            Iterator<campDetailsDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.CAMP_DETAILS+ constants.UNDERSCORE + String.valueOf(timi)+constants.UNDERSCORE+imi(context) + constants.CSVEXT;
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));
                String[] arr1 = {"ID","CAMP_ID", "STATUS","DATE_TIME"};
                csvWrite.writeNext(arr1);
                while(itr.hasNext()) {
                    campDetailsDB t = itr.next();
                    // logg(String.valueOf(t.getId())+","+ t.getStatus()+","+ t.getDate());
                    String[] arr = {String.valueOf(t.getId()), t.getcamp_id(), t.getStatus(),t.getDate()};
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
