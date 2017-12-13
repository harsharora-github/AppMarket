package com.media.app.DataBases;

/**
 * Created by harsh.arora on 02-11-2017.
 */

public class UiDB {

    String status;
    String package_name;
    String app_url;
    String id;

    // Empty constructor
    public UiDB(){

    }
    // constructor
    public UiDB(String status, String package_name){
        this.status = status;
        this.package_name=package_name;

    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
    public void setPackage_name(String package_name){
        this.package_name = package_name;
    }
    public String getPackage_name(){
        return package_name;
    }

    public void setApp_url(String app_url){
        this.app_url = app_url;
    }
    public String getApp_url(){
        return app_url;
    }

    public void setid(String id){
        this.id = id;
    }
    public String getid(){
        return id;
    }


}
