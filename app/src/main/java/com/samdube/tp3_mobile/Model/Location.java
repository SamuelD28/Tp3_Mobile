package com.samdube.tp3_mobile.Model;

import java.util.UUID;

public class Location {

    //Category of the location
    public enum Category{
        Restaurant,
        Touristic,
        Hotel,
        Entertainment
    }

    //Propeties
    private double mLat;
    private double mLng;
    private String mName;
    private UUID mUUID;
    private String mDescription;
    private Category mCategory;

    //Constructors
    public Location()
    {

    }
    public Location(double lat, double lng, String name, String description, Category category) {
        this.mLat = lat;
        this.mLng = lng;
        this.mName = name;
        this.mDescription = description;
        this.mCategory = category;
        this.mUUID = UUID.randomUUID();
    }

    //Getter
    public String getDescription() {
        return mDescription;
    }
    public Category getCategory() {
        return mCategory;
    }
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
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public void setCategory(Category mCategory) {
        this.mCategory = mCategory;
    }
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
