package com.media.app.UI.Models;

/**
 * Created by harsh.arora on 06-11-2017.
 */

public class DownloadModel {


    private String name_down;
    private String image_url_down;




    public DownloadModel() {
    }

    public DownloadModel(String name_down, String image_url_down) {
        this.name_down = name_down;
        this.image_url_down = image_url_down;

    }


    public String getImage_url_down() {
        return image_url_down;
    }

    public void setImage_url_down(String image_url_down) {
        this.image_url_down = image_url_down;
    }


    public String getName_down() {
        return name_down;
    }

    public void setName_down(String name_down) {
        this.name_down = name_down;
    }
}
