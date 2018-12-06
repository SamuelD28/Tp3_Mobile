package com.samdube.tp3_mobile.Model;

import java.util.UUID;

public class Location {

    //Propeties
    private double mLat;
    private double mLng;
    private String mName;
    private UUID mUUID;

    //Constructor

    public Location(double mLat, double mLng, String mName) {
        this.mLat = mLat;
        this.mLng = mLng;
        this.mName = mName;
        this.mUUID = UUID.randomUUID();
    }
    //Getter
    public UUID getId() {
        return mUUID;
    }
    public double getLat() {
        return mLat;
    }
    public double getLng() {
        return mLng;
    }
    public String getName() {
        return mName;
    }

    //Setter
    public void setId(UUID mUUID) {
        this.mUUID = mUUID;
    }
    public void setName(String mName) {
        this.mName = mName;
    }
    public void setLat(int mLat) {
        this.mLat = mLat;
    }
    public void setLng(int mLng) {
        this.mLng = mLng;
    }
}
