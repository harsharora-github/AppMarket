package com.media.app.ServerJobs;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class pollResponse {
    private String status;
    private String data;
    private String camp_id;

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
    public String getCamp_id() { return camp_id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }
}
