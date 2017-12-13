package com.media.app.ServerJobs;

/**
 * Created by prabeer.kochar on 18-07-2017.
 */

public class pollRequest {
    private String IM;
    private String st;
    private String loc;
    private String mcc;
    private String cel;
    private String dev;
    private String camp_id;
    private String ver;

    public pollRequest(String IM, String st, String loc, String mcc, String cel, String dev, String camp_id, String ver){
        this.IM = IM;
        this.st = st;
        this.loc = loc;
        this.mcc = mcc;
        this.cel = cel;
        this.dev = dev;
        this.camp_id = camp_id;
        this.ver = ver;

    }
    public void setIM(String IM){
        this.IM = IM;
    }
    public void setst(String st){
        this.st = st;
    }
    public void setloc(String loc){this.loc = loc;}
    public void setmcc(String mcc){
        this.mcc = mcc;
    }
    public void setcel(String cel){
        this.cel = cel;
    }
    public void setDev(String dev){ this.dev = dev; }
    public void setCamp_id(String camp_id){
        this.camp_id = camp_id;
    }
    public void setVer(String ver){this.ver = ver;}

    public String getIM(){
        return IM;
    }
    public String getst(){
        return st;
    }
    public String getloc(){
        return loc;
    }
    public String getmcc(){
        return mcc;
    }
    public String getcel(){
        return cel;
    }
    public String getDev(){
        return dev;
    }
    public String setCamp_id(){return camp_id; }
    public String getVer() {return ver;}
}
