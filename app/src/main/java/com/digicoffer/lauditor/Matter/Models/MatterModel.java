package com.digicoffer.lauditor.Matter.Models;

import org.json.JSONArray;

public class MatterModel {
    String matter_title;

    String case_number;
    String case_type;
    String description;
    String date_of_filing;
    String court;
    String judge;
    String case_priority;
    String status;
    JSONArray opponent_advocate;

    public String getMatter_title() {
        return matter_title;
    }

    public void setMatter_title(String matter_title) {
        this.matter_title = matter_title;
    }

    public String getCase_number() {
        return case_number;
    }

    public void setCase_number(String case_number) {
        this.case_number = case_number;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_of_filing() {
        return date_of_filing;
    }

    public void setDate_of_filing(String date_of_filing) {
        this.date_of_filing = date_of_filing;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getCase_priority() {
        return case_priority;
    }

    public void setCase_priority(String case_priority) {
        this.case_priority = case_priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getOpponent_advocate() {
        return opponent_advocate;
    }

    public void setOpponent_advocate(JSONArray opponent_advocate) {
        this.opponent_advocate = opponent_advocate;
    }
}
