package com.media.app.DataBases;

/**
 * Created by prabeer.kochar on 01-09-2017.
 */

public class packageInstallCollectorDB {
    String status;
    String Pkgname;
    int id;
    String date;

    // Empty constructor
    public packageInstallCollectorDB() {

    }

    // constructor
    public packageInstallCollectorDB(String status, String pkgname) {
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
}
