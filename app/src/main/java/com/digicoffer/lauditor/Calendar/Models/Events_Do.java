package com.digicoffer.lauditor.Calendar.Models;

import java.util.ArrayList;

public class Events_Do {
    String Event_Name;
    String Converted_Start_time;
    String COnverted_End_time;
    String Event_id;
    String Event_description;
    String Event_repetetion;
    String Event_Notifications;
    String Event_clients;
    String Event_team;
    boolean all_day;

    public boolean isAll_day() {
        return all_day;
    }

    public void setAll_day(boolean all_day) {
        this.all_day = all_day;
    }

    ArrayList<String> notifications;
    ArrayList<String> Team_name;
    ArrayList<String> Tm_name;

    public ArrayList<String> getTeam_name() {
        return Team_name;
    }

    public void setTeam_name(ArrayList<String> team_name) {
        Team_name = team_name;
    }

    public ArrayList<String> getTm_name() {
        return Tm_name;
    }

    public void setTm_name(ArrayList<String> tm_name) {
        Tm_name = tm_name;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    boolean isRecurring;
    ArrayList<String> attachment_name;

    public ArrayList<String> getAttachment_name() {
        return attachment_name;
    }

    public void setAttachment_name(ArrayList<String> attachment_name) {
        this.attachment_name = attachment_name;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
//    boolean isRecurring;
//
//    public boolean isRecurring() {
//        return isRecurring;
//    }
//
//    public void setRecurring(boolean recurring) {
//        isRecurring = recurring;
//    }

    public String getEvent_description() {
        return Event_description;
    }

    public void setEvent_description(String event_description) {
        Event_description = event_description;
    }

    public String getEvent_repetetion() {
        return Event_repetetion;
    }

    public void setEvent_repetetion(String event_repetetion) {
        Event_repetetion = event_repetetion;
    }

    public String getEvent_Notifications() {
        return Event_Notifications;
    }

    public void setEvent_Notifications(String event_Notifications) {
        Event_Notifications = event_Notifications;
    }

    public String getEvent_clients() {
        return Event_clients;
    }

    public void setEvent_clients(String event_clients) {
        Event_clients = event_clients;
    }

    public String getEvent_team() {
        return Event_team;
    }

    public void setEvent_team(String event_team) {
        Event_team = event_team;
    }

    public String getEvent_id() {
        return Event_id;
    }

    public void setEvent_id(String event_id) {
        Event_id = event_id;
    }

    public String getConverted_Start_time() {
        return Converted_Start_time;
    }

    public void setConverted_Start_time(String converted_Start_time) {
        Converted_Start_time = converted_Start_time;
    }

    public String getCOnverted_End_time() {
        return COnverted_End_time;
    }

    public void setCOnverted_End_time(String COnverted_End_time) {
        this.COnverted_End_time = COnverted_End_time;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    String Event_start_time;
    String Event_end_time;

    public String getEvent_start_time() {
        return Event_start_time;
    }

    public void setEvent_start_time(String event_start_time) {
        Event_start_time = event_start_time;
    }

    public String getEvent_end_time() {
        return Event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
        Event_end_time = event_end_time;
    }



}
