package com.example.androidproject;


public class Location {
    private String location;
    private String modeoftransport;
    private int id;

    public Location(String location, int id){
        this.location = location;
        this.id = id;
    }

    public void setModeoftransport(String modeoftransport) {
        this.modeoftransport = modeoftransport;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getModeoftransport() {
        return modeoftransport;
    }
}
