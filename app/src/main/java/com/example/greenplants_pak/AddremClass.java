package com.example.greenplants_pak;


public  class AddremClass{
    private String Pkey = "";
    private  String name ="";
    private  String Sdate ="";
    private  String dosage ="";
    private  String Rdate ="";
    private  String Note ="";
    private  String DoesUnit ="";

    public AddremClass() {
    }

    public AddremClass(String name, String sdate, String dosage, String rdate, String note, String doesUnit , String pkey) {
        this.name = name;
        Pkey = pkey;
        Sdate = sdate;
        this.dosage = dosage;
        Rdate = rdate;
        Note = note;
        DoesUnit = doesUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdate() {
        return Sdate;
    }

    public void setSdate(String sdate) {
        Sdate = sdate;
    }

    public String getDosage() {
        return dosage;
    }

    public String getPkey() {
        return Pkey;
    }

    public void setPkey(String pkey) {
        Pkey = pkey;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getRdate() {
        return Rdate;
    }

    public void setRdate(String rdate) {
        Rdate = rdate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getDoesUnit() {
        return DoesUnit;
    }

    public void setDoesUnit(String doesUnit) {
        DoesUnit = doesUnit;
    }
}