package com.example.jurist;
//User has to be the same as the first folder
public class event_list {
    private String Date,UID;

    public event_list() {}


    public event_list(String date, String UID) {
        this.Date = date;
        this.UID = UID;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
