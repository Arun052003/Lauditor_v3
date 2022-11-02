package com.digicoffer.lauditor.DahboardModels.MydayModels;

public class MeetingModel {
    int count;
    String time;
    String subject;

    public MeetingModel(int count, String time, String subject) {
        this.count = count;
        this.time = time;
        this.subject = subject;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
