package com.samdube.tp3_mobile.Model;

import java.util.UUID;

/**
 * Location object used by the program to store location
 */
public class Location {

    /**
     * Category enumeration for different types of locations
     */
    public enum Category {
        Restaurant,
        Touristic,
        Hotel,
        Entertainment
    }

    //Logic Variables
    private double mLat;
    private double mLng;
    private String mName;
    private UUID mUUID;
    private String mDescription;
    private Category mCategory;

    /**
     * Empty constructor that initialise all the fields to default value
     */
    public Location() {
        this.mLat = 0;
        this.mLng = 0;
        this.mName = "";
        this.mDescription = "";
        this.mCategory = Category.Entertainment;
        this.mUUID = UUID.randomUUID();
    }

    /**
     * Constructor that creates a new location based on the same values of the location argument
     *
     * @param location Location to copy the values from
     */
    public Location(Location location) {
        this.mLat = location.getLat();
        this.mLng = location.getLng();
        this.mName = location.getName();
        this.mDescription = location.getDescription();
        this.mCategory = location.getCategory();
        this.mUUID = location.getId();
    }

    /**
     * Constructor that takes all the fields except the uuid as  arguments
     *
     * @param lat         latitude of the location
     * @param lng         longitude of the location
     * @param name        name of the location
     * @param description description of location
     * @param category    category of the location
     */
    public Location(double lat, double lng, String name, String description, Category category) {
        this.mLat = lat;
        this.mLng = lng;
        this.mName = name;
        this.mDescription = description;
        this.mCategory = category;
        this.mUUID = UUID.randomUUID();
    }

    /**
     * Constructor that takes all the fields as arguments including the uuid. To use with precaution because it
     * does not check for duplicate key
     *
     * @param lat         latitude of the location
     * @param lng         longitude fo the location
     * @param name        name of the location
     * @param description description of the location
     * @param category    category of the location
     * @param uuid        uuid of the location
     */
    public Location(double lat, double lng, String name, String description, Category category, UUID uuid) {
        this.mLat = lat;
        this.mLng = lng;
        this.mName = name;
        this.mDescription = description;
        this.mCategory = category;
        this.mUUID = uuid;
    }

    //Getters
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

    //Setters
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
