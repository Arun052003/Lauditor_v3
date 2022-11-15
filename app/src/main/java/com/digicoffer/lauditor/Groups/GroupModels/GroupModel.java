package com.digicoffer.lauditor.Groups.GroupModels;

public class GroupModel {
    String name;
    String id;
    private boolean isSelected;
    boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//    public GroupModel(String name) {
//        this.name = name;
//        this.isSelected = isSelected;
//        this.isChecked = isChecked;
//    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
