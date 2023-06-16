package com.example.greenplants_pak.Model;

public class Progress {
private  String Customer_id = "" ;
private  String Plant_pic = "" ;
private  String Subject = "" ;
private  String Desc = "" ;
private  String P_id = "" ;

    public Progress() {
    }

    public Progress(String customer_id, String plant_pic, String subject, String desc, String p_id) {
        Customer_id = customer_id;
        Plant_pic = plant_pic;
        Subject = subject;
        Desc = desc;
        P_id = p_id;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    public String getPlant_pic() {
        return Plant_pic;
    }

    public void setPlant_pic(String plant_pic) {
        Plant_pic = plant_pic;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getP_id() {
        return P_id;
    }

    public void setP_id(String p_id) {
        P_id = p_id;
    }
}