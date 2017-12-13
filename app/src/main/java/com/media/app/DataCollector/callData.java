package com.media.app.DataCollector;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;


import com.media.app.Utils.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;


/**
 * Created by prabeer.kochar on 22-08-2017.
 */

public class callData {
    String sFileName;

    public callData(Context context) {
        long timi = System.currentTimeMillis();
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor managedCursor = context.getContentResolver().query(allCalls, null, null, null, null);
        sFileName = constants.callFile + String.valueOf(timi) + "_" + imi(context) + ".csv";
        logg("Call Data Create");
        writeData(managedCursor);
        managedCursor.close();

    }

    private void writeData(Cursor cursor) {

        try {
            File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));
                    // logg(cursor.getColumnNames());

            int num = cursor.getColumnIndex(CallLog.Calls.NUMBER);// for  number
            int DATE = cursor.getColumnIndex(CallLog.Calls.DATE);// for name
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);// for duration
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            logg("Call Data Start");
            String arrHead[] = {"Phnum","CallType","CallDate","CallDayTime", "CallDuration","TypeDetails"};
            csvWrite.writeNext(arrHead);
            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(num);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(DATE);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                //Which column you want to exprort
            //    logg(phNumber + "," + callType + "," + callDate + "," + String.valueOf(callDayTime) + "," + callDuration + "," + dir);

                String arrStr[] = {phNumber, callType, callDate, String.valueOf(callDayTime), callDuration, dir};
                if (file.canWrite()) {
                    csvWrite.writeNext(arrStr);
                   // logg("Writable");
                } else {
                   // logg("notWritable");
                }

            }
            csvWrite.flush();
            csvWrite.close();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
