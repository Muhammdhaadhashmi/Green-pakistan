package com.example.greenplants_pak.Model;

public class ItemClass implements  Comparable<ItemClass> {

private String Item_ID = "" ;
private  String Item_Name ="";
private  String Cheff_id = "";
private  String item_Address = "" ;
private  String Lattitude = "" ;
private  String Longitude = "" ;
private  String Time = "" ;
private  String Date = "" ;
private  String DishType = "" ;
private  String Price = "" ;
private  String Uri = "" ;
private  String Rating = "" ;
private  String Cata = "" ;

    public ItemClass() {
    }

    public ItemClass(String item_ID, String item_Name, String cheff_id, String item_Address, String lattitude, String longitude, String time, String date, String dishType, String price , String uri , String rating
    ,String cata) {
        Cata = cata ;
        Item_ID = item_ID;
        Rating =rating;
        Item_Name = item_Name;
        Uri = uri ;
        Cheff_id = cheff_id;
        this.item_Address = item_Address;
        Lattitude = lattitude;
        Longitude = longitude;
        Time = time;
        Date = date;
        DishType = dishType;
        Price = price;
    }

    public String getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(String item_ID) {
        Item_ID = item_ID;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getCheff_id() {
        return Cheff_id;
    }

    public String getUri() {
        return Uri;
    }

    public String getCata() {
        return Cata;
    }

    public void setCata(String cata) {
        Cata = cata;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public void setCheff_id(String cheff_id) {
        Cheff_id = cheff_id;
    }

    public String getItem_Address() {
        return item_Address;
    }

    public void setItem_Address(String item_Address) {
        this.item_Address = item_Address;
    }

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDishType() {
        return DishType;
    }

    public void setDishType(String dishType) {
        DishType = dishType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


    @Override
    public int compareTo(ItemClass o) {
        return this.Rating.compareTo(o.Rating);
    }



}
