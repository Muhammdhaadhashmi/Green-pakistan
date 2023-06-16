package com.example.greenplants_pak.Model;

public class Complain {
    private String C_id = "" ;
    private  String S_id = "" ;
    private  String P_id  = "" ;
    private  String Cus_id  = "" ;
    private  String Subject = "" ;
    private  String Description = "";

    public Complain() {
    }


    public Complain(String c_id, String s_id, String p_id, String cus_id, String subject, String description) {
        C_id = c_id;
        S_id = s_id;
        P_id = p_id;
        Cus_id = cus_id;
        Subject = subject;
        Description = description;
    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public String getS_id() {
        return S_id;
    }

    public void setS_id(String s_id) {
        S_id = s_id;
    }

    public String getP_id() {
        return P_id;
    }

    public void setP_id(String p_id) {
        P_id = p_id;
    }

    public String getCus_id() {
        return Cus_id;
    }

    public void setCus_id(String cus_id) {
        Cus_id = cus_id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
