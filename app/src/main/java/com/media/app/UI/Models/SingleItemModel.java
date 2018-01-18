package com.media.app.UI.Models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class SingleItemModel {


    private String name;
    private String image_url;
    private String description;
    private String app_url;
    private String app_package;




    public SingleItemModel() {
    }

    public SingleItemModel(String name, String image_url, String app_url, String app_package,String description) {
        this.name = name;
        this.image_url = image_url;
        this.app_url=app_url;
        this.app_package=app_package;
        this.description=description;
    }


    public String getUrl() {
        return image_url;
    }

    public void setUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppURL() {
        return app_url;
    }

    public void setAppURL(String app_url) {
        this.app_url = app_url;
    }

    public String getAppPackage(){
        return app_package;
    }

    public void setApp_package(String app_package){
        this.app_package = app_package;
    }

}
