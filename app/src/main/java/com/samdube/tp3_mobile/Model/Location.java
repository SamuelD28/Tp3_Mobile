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
        this.mLat = 0;
        this.mLng = 0;
        this.mName = "";
        this.mDescription = "";
        this.mCategory = Category.Entertainment;
        this.mUUID = UUID.randomUUID();
    }
    public Location(Location location)
    {
        this.mLat = location.getLat();
        this.mLng = location.getLng();
        this.mName = location.getName();
        this.mDescription = location.getDescription();
        this.mCategory = location.getCategory();
        this.mUUID = location.getId();
    }
    public Location(double lat, double lng, String name, String description, Category category) {
        this.mLat = lat;
        this.mLng = lng;
        this.mName = name;
        this.mDescription = description;
        this.mCategory = category;
        this.mUUID = UUID.randomUUID();
    }
    public Location(double lat, double lng, String name, String description, Category category, UUID uuid) {
        this.mLat = lat;
        this.mLng = lng;
        this.mName = name;
        this.mDescription = description;
        this.mCategory = category;
        this.mUUID = uuid;
    }

    public void UpdateLocation(Location newLocation)
    {
        this.mLat = newLocation.getLat();
        this.mLng = newLocation.getLng();
        this.mName = newLocation.getName();
        this.mDescription = newLocation.getDescription();
        this.mCategory = newLocation.getCategory();
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
    public void setLat(double mLat) {
        this.mLat = mLat;
    }
    public void setLng(double mLng) {
        this.mLng = mLng;
    }
}
