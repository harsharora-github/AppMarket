package com.media.app.DataCollector;

import android.content.Context;
import android.os.Environment;


import com.media.app.DataBases.DBEssentials;
import com.media.app.DataBases.databaseHandler;
import com.media.app.DataBases.homekeyDB;
import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 06-09-2017.
 */

public class homeKeyCollector {
    databaseHandler hk;
    public homeKeyCollector(Context context) {
        logg("Bluetooth Collector");
        hk = new databaseHandler(context);
        logg("List Size:"+hk.getAllHOMEKEY().size());
     //   logg(hk.getAllHOMEKEY().get(0).getStatus());
        writeData(hk.getAllHOMEKEY(),context);
        hk.close();

    }

    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {
            Iterator<homekeyDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.HOME_KEY+ constants.UNDERSCORE + String.valueOf(timi)+ constants.UNDERSCORE+imi(context) + constants.CSVEXT;
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
                    homekeyDB t = itr.next();
                    // logg(String.valueOf(t.getId())+","+ t.getStatus()+","+ t.getDate());
                    String[] arr = {String.valueOf(t.getId()), t.getStatus(),t.getDate()};
                    csvWrite.writeNext(arr);
                }
                csvWrite.flush();
                csvWrite.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logg("cursor:"+cursor.size());
        }
    }
}
