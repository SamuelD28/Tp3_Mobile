package com.samdube.tp3_mobile.Model;

import java.util.UUID;

public class Location {

    //Propeties
    private int mLat;
    private int mLng;
    private String mName;
    private UUID mUUID;

    //Constructor

    public Location(int mLat, int mLng, String mName) {
        this.mLat = mLat;
        this.mLng = mLng;
        this.mName = mName;
        this.mUUID = UUID.randomUUID();
    }
    //Getter
    public UUID getId() {
        return mUUID;
    }
    public int getLat() {
        return mLat;
    }
    public int getLng() {
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
