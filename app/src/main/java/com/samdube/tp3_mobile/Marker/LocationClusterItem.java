package com.samdube.tp3_mobile.Marker;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.samdube.tp3_mobile.Model.Location;

public class LocationClusterItem implements ClusterItem {

    //Properties
    private LatLng position;
    private String title;
    private String snippet;
    private Location location;



    //Constructors
    public LocationClusterItem() {
    }

    public LocationClusterItem(LatLng position, String title, String snippet, Location location) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.location = location;
    }

    //Getter
    @Override
    public LatLng getPosition() {
        return position;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getSnippet() {
        return snippet;
    }
    public Location getLocation() {
        return location;
    }

    //Setter
    public void setPosition(LatLng position) {
        this.position = position;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

}
