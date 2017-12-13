package com.media.app.DataBases;

/**
 * Created by prabeer.kochar on 04-09-2017.
 */

public class packageMonitorCollectorDB {
    String status;
    String Pkgname;
    int id;
    String date;
    String UsedTime;

    // Empty constructor
    public packageMonitorCollectorDB() {

    }

    // constructor
    public packageMonitorCollectorDB(String status, String pkgname, String UsedTime) {
        this.status = status;
        this.Pkgname = Pkgname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPkgname(String Pkgname) {
        this.Pkgname = Pkgname;
    }

    public String getPkgname() {
        return Pkgname;
    }
    public void  setUsedTime(String UsedTime){
        this.UsedTime = UsedTime;
    }

    public String getUsedTime(){return  UsedTime;}
}


