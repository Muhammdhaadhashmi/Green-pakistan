package com.example.greenplants_pak.Model;

public class Botonical_infomation {
private  String B_id ="";
private  String subject ="";
private  String Description ="";

    public Botonical_infomation() {
    }

    public Botonical_infomation(String b_id, String subject, String description) {
        B_id = b_id;
        this.subject = subject;
        Description = description;
    }

    public String getB_id() {
        return B_id;
    }

    public void setB_id(String b_id) {
        B_id = b_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

