package com.digicoffer.lauditor.Matter.Models;

public class DocumentsModel {
    String description;
    String docid;
    String doctype;
    String name;
    String tags;
    String user_id;
    private boolean isSelected;
    boolean isChecked;
    boolean isenabled;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isIsenabled() {
        return isenabled;
    }

    public void setIsenabled(boolean isenabled) {
        this.isenabled = isenabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
