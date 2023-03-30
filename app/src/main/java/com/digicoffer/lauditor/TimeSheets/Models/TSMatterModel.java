package com.digicoffer.lauditor.TimeSheets.Models;

public class TSMatterModel {
    String Mattername;
    String Matterid;
    String Matter_type;

    public String getMatter_type() {
        return Matter_type;
    }

    public void setMatter_type(String matter_type) {
        Matter_type = matter_type;
    }

    public String getMattername() {
        return Mattername;
    }

    public void setMattername(String mattername) {
        Mattername = mattername;
    }

    public String getMatterid() {
        return Matterid;
    }

    public void setMatterid(String matterid) {
        Matterid = matterid;
    }
}
