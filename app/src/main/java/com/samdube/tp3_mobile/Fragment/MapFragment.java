package com.samdube.tp3_mobile.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;
import com.samdube.tp3_mobile.View.LocationAddDialog;
import com.samdube.tp3_mobile.View.LocationDetailDialog;

import java.util.ArrayList;

import static com.samdube.tp3_mobile.Model.Location.Category;

public  class       MapFragment
        extends     SupportMapFragment
        implements  GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationLog mLocationLog;
    private IApplicationState mApplicationState; //This hold the parent activity
    private ArrayList<Marker> mMarkers;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mMarkers = new ArrayList<>();
        mApplicationState = (IApplicationState)getActivity();
        mLocationLog = LocationLog.GetInstance();

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        switch(mApplicationState.GetCurrentMode())
        {
            case ADD: ChangeToAddMode(); break;
            case EDIT: ChangeToEditMode(); break;
            case INFO: ChangeToInfoMode(); break;
        }
    }

    public void ChangeToAddMode()
    {
        GenerateMarkers();
        mMap.setOnMapClickListener(latLng -> { AddMarker(latLng); });
    }

    public void ChangeToInfoMode()
    {
        GenerateMarkers();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();

                mApplicationState.SetSelectedLocation((Location)marker.getTag());
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mApplicationState.SetSelectedLocation(null);
            }
        });
    }

    public void ChangeToEditMode()
    {
        GenerateMarker(mApplicationState.GetSelectedLocation()).setDraggable(true);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(getActivity(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Toast.makeText(getActivity(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getActivity(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddMarker(LatLng latLng)
    {
        new LocationAddDialog(getContext(), getActivity(), mApplicationState, latLng);
//        GenerateMarker(newLocation);
    }

    private void GenerateMarkers()
    {
        for(Location location : mLocationLog.getmLocations())
        {
            mMarkers.add(GenerateMarker(location));
        }
        mMap.setOnInfoWindowClickListener(this);
    }

     private Marker GenerateMarker(Location location)
     {
         //We create the marker options for the marker
         MarkerOptions mkOptions = GenerateMarkerOptions(location);
         //We create a new marker and add it to the map object
         Marker mk = mMap.addMarker(mkOptions);
         //We set the tag of the marker to hold the location object. We can access it more easily outside the function
         mk.setTag(location);
         return mk;
     }

     private MarkerOptions GenerateMarkerOptions(Location location)
     {
         //Object needed for creating marker options
         MarkerOptions mkOptions = new MarkerOptions();
         LatLng latlng = new LatLng(location.getLat(), location.getLng());

         //Sets the different options of the marker
         mkOptions.position(latlng);
         mkOptions.title(location.getName());
         mkOptions.icon(GenerateMarkerIcon(location.getCategory()));

         return mkOptions;
     }

     private BitmapDescriptor GenerateMarkerIcon(Category locationCategory)
     {
         //Both objects needed to generate an icon for a marker
         IconGenerator iconGenerator = new IconGenerator(getContext());
         ImageView image = new ImageView(getContext());

         //Set the appropriate image based on the locatino category
         switch(locationCategory){
             case Hotel: image.setImageResource(R.drawable.ic_hotel); break;
             case Touristic: image.setImageResource(R.drawable.ic_touristic); break;
             case Restaurant: image.setImageResource(R.drawable.ic_restaurant); break;
             case Entertainment: image.setImageResource(R.drawable.ic_entertainment); break;
         }

         //Create the icon bitmap with the image view
         iconGenerator.setContentView(image);
         Bitmap bitmap = iconGenerator.makeIcon();

         return BitmapDescriptorFactory.fromBitmap(bitmap);
     }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        Location location = (Location) marker.getTag();
        new LocationDetailDialog(getContext(), getActivity(), mApplicationState, location);
    }

}