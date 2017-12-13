package com.media.app.DataBases;

/**
 * Created by prabeer.kochar on 02-11-2017.
 */

public class campDetailsDB {

    String status;
    int id;
    String date;
    String camp_id;

    // Empty constructor
    public campDetailsDB(){

    }
    // constructor
    public campDetailsDB(String status, String camp_id){
        this.status = status;
        this.camp_id = camp_id;
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
    public void setcamp_id(String camp_id){
        this.camp_id = date;
    }
    public String getcamp_id(){
        return camp_id;
    }
}
