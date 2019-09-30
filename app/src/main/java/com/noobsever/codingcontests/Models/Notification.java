package com.noobsever.codingcontests.Models;

public class Notification {


    public Notification() {
    }

    private  int id;
    private  int notiId;
    private String time,event;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Notification(int id, int notiId, String time, String event) {
        this.id = id;
        this.notiId = notiId;
        this.time = time;
        this.event = event;
    }
}
