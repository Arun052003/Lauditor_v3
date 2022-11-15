package com.digicoffer.lauditor.Groups.GroupModels;

public class ViewGroupModel {
    String id;
    String name;
    String description;
    String created;
    String User_type;
    String owner_name;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
//    public ViewGroupModel(String user_type, String owner_name, String date) {
//        User_type = user_type;
//        this.owner_name = owner_name;
//        this.date = date;
//    }

    public String getUser_type() {
        return User_type;
    }

    public void setUser_type(String user_type) {
        User_type = user_type;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
