package com.media.app.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 24-08-2017.
 */

public class databaseHandler extends SQLiteOpenHelper {

    private static final String COLUMN_ID = "id";
    private static final String STATUS = "status";
    private static final String DATETIME = "date_time";
    private static final String PACKAGE_NAME = "package_name";
    private static final String PACKAGE_STATUS = "package_status";
    private static final String PACKAGE_MONITOR_LASTUSED = "package_last_used";
    private static final String NETWORKTYPE = "network_type";
    private static final String APP_LOCATION = "app_location";
    private static final String APP_URL = "APP_URL";


    private static final String CAMP_ID = "CAMP_ID";


    public databaseHandler(Context context) {
        super(context, DBEssentials.DB, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DBEssentials.BLUETOOTH_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.LOWBATTERY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPINSTALL + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPMONITOR + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.PHONELOCK + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.EAR_JACK + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.HOME_KEY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.NETWORK_CHANGE_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + NETWORKTYPE + " text," + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.CAMP_DETAILS + " " +
                        "(" + COLUMN_ID + " integer primary key, " + CAMP_ID + " text," + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.UI_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP," + PACKAGE_NAME + " text," + APP_URL + " text," + APP_LOCATION + " text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.BLUETOOTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.LOWBATTERY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPINSTALL);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPMONITOR);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.PHONELOCK);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.EAR_JACK);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.HOME_KEY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.NETWORK_CHANGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.CAMP_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.UI_TABLE);
        onCreate(db);
    }

    /*
************************** HOME KEY DATA ******************
 */
    public boolean insertHOMEKEY(String status) {
        if (checkTable(DBEssentials.HOME_KEY)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Insert");
                db.insert(DBEssentials.HOME_KEY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.HOME_KEY + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.HOME_KEY, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<homekeyDB> getAllHOMEKEY() {

        List<homekeyDB> homekeyDBList = new ArrayList<homekeyDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.HOME_KEY, null);
            if (res.moveToFirst()) {
                do {
                    homekeyDB btdb = new homekeyDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    homekeyDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return homekeyDBList;
    }

    public boolean deleteRecordsHOMEKEY(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete( DBEssentials.HOME_KEY, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
           // db.rawQuery("delete from " + DBEssentials.HOME_KEY + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return true;
    }

//---------------------------------//----------------------------------

    /*
    ************************** EAR JACK DATA ******************
     */
    public boolean insertEARJACK(String status) {
        if (checkTable(DBEssentials.EAR_JACK)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Insert");
                db.insert(DBEssentials.EAR_JACK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.EAR_JACK + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");

                db.insert(DBEssentials.EAR_JACK, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<earjackDB> getAllEARJACK() {

        List<earjackDB> earjackDBList = new ArrayList<earjackDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.EAR_JACK, null);
            if (res.moveToFirst()) {
                do {
                    earjackDB btdb = new earjackDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    earjackDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return earjackDBList;
    }

    public boolean deleteRecordsEARJACK(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete( DBEssentials.EAR_JACK, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
          //  db.rawQuery("delete from " + DBEssentials.EAR_JACK + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return true;
    }

//---------------------------------//----------------------------------

    /*
************************** LOCK MONITOR DATA ******************
 */
    public boolean insertLockMonitor(String status) {


        if (checkTable(DBEssentials.PHONELOCK)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.PHONELOCK + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }
        return true;
    }

    public List<lockUnlockDB> getAllLockMonitor() {

        List<lockUnlockDB> lockUnlockDBList = new ArrayList<lockUnlockDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.PHONELOCK, null);
            if (res.moveToFirst()) {
                do {
                    lockUnlockDB btdb = new lockUnlockDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    lockUnlockDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lockUnlockDBList;
    }

    public boolean deleteRecordsLockMonitor(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete(DBEssentials.PHONELOCK, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
           // db.rawQuery("delete from " + DBEssentials.PHONELOCK + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    /*
    ***************************Package Monitor *********************
     */
    public boolean insertPackageMonitor(String pkg, String status, String last_used_time) {
        if (checkTable(DBEssentials.APPMONITOR)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                contentValues.put(PACKAGE_MONITOR_LASTUSED, last_used_time);

                if (db != null) {
                    long chk = db.insert(DBEssentials.APPMONITOR, null, contentValues);
                    if (chk != 0) {
                        logg("insert success:" + Long.toString(chk));
                    } else {
                        logg("insert fail:" + Long.toString(chk));
                    }
                } else {
                    logg("db not accessible");
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            logg("APPMONITOR Create Table");
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                contentValues.put(PACKAGE_MONITOR_LASTUSED, last_used_time);
                db.execSQL(
                        "create table " + DBEssentials.APPMONITOR + " " +
                                "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.APPMONITOR, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<packageMonitorCollectorDB> getAllPackageMonitorStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();

        List<packageMonitorCollectorDB> packageMonitorCollectorDBList = new ArrayList<packageMonitorCollectorDB>();
        //hp = new HashMap();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.APPMONITOR, null);
            if (res.moveToFirst()) {
                do {
                    packageMonitorCollectorDB PMDB = new packageMonitorCollectorDB();
                    PMDB.setStatus(res.getString(2));
                    PMDB.setId(Integer.parseInt(res.getString(0)));
                    PMDB.setDate(res.getString(4));
                    PMDB.setPkgname(res.getString(1));
                    PMDB.setUsedTime(res.getString(3));
                    // Adding contact to list
                    packageMonitorCollectorDBList.add(PMDB);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return packageMonitorCollectorDBList;
    }

    public boolean deleteRecordspackageMonitor(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete(DBEssentials.APPMONITOR, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
          //  db.rawQuery("delete from " + DBEssentials.APPMONITOR + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////

    public boolean insertPackageStatus(String pkg, String status) {
        if (checkTable(DBEssentials.APPINSTALL)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                db.insert(DBEssentials.APPINSTALL, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {

                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                db.insert(DBEssentials.APPINSTALL, null, contentValues);
                db.execSQL(
                        "create table " + DBEssentials.APPINSTALL + " " +
                                "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }

        return true;
    }

    public List<packageInstallCollectorDB> getAllPackageStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<packageInstallCollectorDB> packageInstallCollectorDBList = new ArrayList<packageInstallCollectorDB>();
        //hp = new HashMap();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.APPINSTALL, null);
            if (res.moveToFirst()) {
                do {
                    packageInstallCollectorDB PIDB = new packageInstallCollectorDB();
                    PIDB.setStatus(res.getString(2));
                    PIDB.setPkgname(res.getString(1));
                    PIDB.setId(Integer.parseInt(res.getString(0)));
                    PIDB.setDate(res.getString(3));
                    // Adding contact to list
                    packageInstallCollectorDBList.add(PIDB);
                } while (res.moveToNext());

            }
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return packageInstallCollectorDBList;
    }

    public boolean deleteRecordpackageInstall(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete( DBEssentials.APPINSTALL , DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
          //  db.rawQuery("delete from " + DBEssentials.APPINSTALL + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////
    /*
    ******************************** LOW BATTERY STATUS *************************
     */
    public boolean insertLBdata(String status) {
        if (checkTable(DBEssentials.LOWBATTERY)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.LOWBATTERY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.LOWBATTERY + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.LOWBATTERY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        }
        return true;
    }


    public List<lowBatteryDB> getAllLowRecords() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<lowBatteryDB> LowstatusList = new ArrayList<lowBatteryDB>();
        //hp = new HashMap();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.LOWBATTERY, null);
            if (res.moveToFirst()) {
                do {
                    lowBatteryDB lowdb = new lowBatteryDB();
                    lowdb.setStatus(res.getString(1));
                    lowdb.setId(Integer.parseInt(res.getString(0)));
                    lowdb.setDate(res.getString(2));
                    // Adding contact to list
                    LowstatusList.add(lowdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return LowstatusList;
    }

    public boolean deleteRecordLowBattery(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete(DBEssentials.LOWBATTERY , DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
           // db.rawQuery("delete from " + DBEssentials.LOWBATTERY + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

   /*
    ******************************** BLUETOOTH DATA **************************************
     */

    public boolean insertBTdata(String status) {
        if (checkTable(DBEssentials.BLUETOOTH_TABLE)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.BLUETOOTH_TABLE + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        }
        return true;
    }

    public List<bluetoothDB> getAllBTRecords() {

        List<bluetoothDB> BTstatusList = new ArrayList<bluetoothDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.BLUETOOTH_TABLE, null);
            if (res.moveToFirst()) {
                do {
                    bluetoothDB btdb = new bluetoothDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    BTstatusList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return BTstatusList;
    }


    public boolean deleteRecordBTRecords(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete(DBEssentials.BLUETOOTH_TABLE, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
            //db.rawQuery("delete from " + DBEssentials.BLUETOOTH_TABLE + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    /*
************************** CAMP_DETAILS DATA ******************
*/
    public boolean insertCAMPDetails(String camp_id, String status) {
        if (checkTable(DBEssentials.CAMP_DETAILS)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                contentValues.put(CAMP_ID, camp_id);
                // logg("APPMONITOR Insert");
                db.insert(DBEssentials.CAMP_DETAILS, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                contentValues.put(CAMP_ID, status);
                // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.CAMP_DETAILS + " " +
                                "(" + COLUMN_ID + " integer primary key, " + CAMP_ID + " text," + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.CAMP_DETAILS, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<campDetailsDB> getAllCAMPDetails() {

        List<campDetailsDB> campDetailsDBList = new ArrayList<campDetailsDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.CAMP_DETAILS, null);
            if (res.moveToFirst()) {
                do {
                    campDetailsDB btdb = new campDetailsDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(2));
                    btdb.setcamp_id(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(3));
                    // Adding contact to list
                    campDetailsDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return campDetailsDBList;
    }

    public boolean deleteRecordscampDetails(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.delete( DBEssentials.CAMP_DETAILS, DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
            // db.rawQuery("delete from " + DBEssentials.HOME_KEY + " where " + DATETIME + " < DATETIME('now', '-" + hr + " hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return true;
    }

//---------------------------------//----------------------------------

        /*
************************** UI_DETAILS DATA ******************
*/
        public boolean insertIntoUI(String status, String package_name, String app_url, String app_loc) {
            if (checkTable(DBEssentials.UI_TABLE)) {
                SQLiteDatabase db;
                db = this.getWritableDatabase();
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(STATUS, status);

                    contentValues.put(PACKAGE_NAME, package_name);
                    contentValues.put(APP_URL, app_url);
                    contentValues.put(APP_LOCATION, app_loc);

                    db.insert(DBEssentials.UI_TABLE, null, contentValues);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    db.close();
                }

            } else {
                SQLiteDatabase db;
                db = this.getWritableDatabase();
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(STATUS, status);

                    db.execSQL(
                            "create table " + DBEssentials.UI_TABLE + " " +
                                    "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP," + PACKAGE_NAME + " package_name," + APP_URL + " text," + APP_LOCATION + " text)"
                    );
                    db.insert(DBEssentials.UI_TABLE, null, contentValues);
                } catch (SQLiteException e) {

                    e.printStackTrace();
                    return false;
                } finally {
                    db.close();
                }

            }
            return true;
        }

    public boolean UPDATE_PACKAGE_STATUS(String status, String pkg) {
        if (checkTable(DBEssentials.UI_TABLE)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            Log.d("harsh","UpdateStatus;"+status);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                int x =  db.update(DBEssentials.UI_TABLE, contentValues, PACKAGE_NAME + "='"+pkg+"'",null);
                Log.d("harsh","UpdateCount;"+ String.valueOf(x));

            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);

                db.execSQL(
                        "create table " + DBEssentials.UI_TABLE + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP," + PACKAGE_NAME + " package_name," + APP_URL + " text," + APP_LOCATION + " text)"
                );
                db.insert(DBEssentials.UI_TABLE, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public boolean UPDATE_PACKAGE_LOCATION(String app_loc, String pkg) {
        if (checkTable(DBEssentials.UI_TABLE)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APP_LOCATION, app_loc);
                db.update(DBEssentials.UI_TABLE, contentValues, PACKAGE_NAME + "='"+pkg+"'",null);
                // Log.d("harsh","Updatelocation;"+String.valueOf(x));
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APP_LOCATION, app_loc);

                db.execSQL(
                        "create table " + DBEssentials.UI_TABLE + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP," + PACKAGE_NAME + " package_name," + APP_URL + " text," + APP_LOCATION + " text)"
                );
                db.update(DBEssentials.UI_TABLE, contentValues, PACKAGE_NAME + "='"+pkg+"'",null);
                // Log.d("harsh","Updatelocation;"+String.valueOf(x));
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }
    public ArrayList<UiDB> selectApp () {
        SQLiteDatabase db;

        db = this.getReadableDatabase();
        ArrayList<UiDB> mArrayList1 = null;
        try {
            Cursor c = db.rawQuery("select id, status, package_name, app_url from " + DBEssentials.UI_TABLE + " WHERE status is null or status = '' limit 1", null);

            mArrayList1 = new ArrayList<>();
            UiDB ins = new UiDB();
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                ins.setid(c.getString(c.getColumnIndex(COLUMN_ID)));
                ins.setPackage_name(c.getString(c.getColumnIndex(PACKAGE_NAME)));
                ins.setStatus(c.getString(c.getColumnIndex(STATUS)));
                ins.setApp_url(c.getString(c.getColumnIndex(APP_URL)));
                mArrayList1.add(ins);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            return mArrayList1;
        }


    }
//---------------------------------//----------------------------------

    public boolean truncateAllTables() {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            logg("data delete");
            db.delete(DBEssentials.BLUETOOTH_TABLE, null, null);
            db.delete(DBEssentials.APPINSTALL, null, null);
            db.delete(DBEssentials.LOWBATTERY, null, null);
            db.delete(DBEssentials.APPMONITOR, null, null);
            db.delete(DBEssentials.PHONELOCK, null, null);
            db.delete(DBEssentials.EAR_JACK, null, null);
            db.delete(DBEssentials.NETWORK_CHANGE_TABLE, null, null);
            db.delete(DBEssentials.HOME_KEY, null, null);
            db.delete(DBEssentials.CAMP_DETAILS, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }

        return true;
    }

    public void closedb() {
        this.close();
    }

    private boolean checkTable(String table_name) {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + table_name + "'", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }

    }
}
