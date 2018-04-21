package com.example.pallaksingh.androidsmarttraveler;

public class Places {
    private String name;
    private String distance;
    private String place_type;
    private String shortDesc;


//    public Places(String name, String distance, String place_type, String shortDesc) {
//        this.name = name;
//        this.distance = distance;
//        this.place_type = place_type;
//        this.shortDesc = shortDesc;
//    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getPlace_type() {
        return place_type;
    }

    public String getShortDesc() {
        return shortDesc;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
}
