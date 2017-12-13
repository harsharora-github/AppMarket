package com.media.app.DataBases;

/**
 * Created by prabeer.kochar on 04-09-2017.
 */

public class lockUnlockDB {

    String status;
    int id;
    String date;

    // Empty constructor
    public lockUnlockDB(){

    }
    // constructor
    public lockUnlockDB(String status){
        this.status = status;

    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }
}
