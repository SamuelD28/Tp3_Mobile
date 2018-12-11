package com.samdube.tp3_mobile.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;
import com.samdube.tp3_mobile.Dialog.LocationAddDialog;
import com.samdube.tp3_mobile.Dialog.LocationDetailDialog;

import java.util.ArrayList;

import static com.samdube.tp3_mobile.Fragment.LocationEditFragment.EditFragmentCallback;
import static com.samdube.tp3_mobile.Model.Location.Category;

public  class       MapFragment
        extends     SupportMapFragment
        implements  GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationLog mLocationLog;
    private MainActivityState mMainActivityState; //This hold the parent activity
    private ArrayList<Marker> mMarkers;
    private EditFragmentCallback mEditFragmentCallback;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mMarkers = new ArrayList<>();
        mMainActivityState = (MainActivityState)getActivity();
        mEditFragmentCallback = (EditFragmentCallback)getActivity();
        mLocationLog = LocationLog.GetInstance(getContext());

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        switch(mMainActivityState.getCurrentMode())
        {
            case ADD: ChangeToAddMode(); break;
            case EDIT: ChangeToEditMode(); break;
            case INFO: ChangeToInfoMode(); break;
        }
    }

    public void ChangeToAddMode()
    {
        GenerateMarkers();
        mMap.setOnMapClickListener(latLng -> {
            mMainActivityState.setSelectedLocation(null);
            AddMarker(latLng);
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                mMainActivityState.setSelectedLocation((Location)marker.getTag());
                return true;
            }
        });
    }

    public void ChangeToInfoMode()
    {
        GenerateMarkers();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                mMainActivityState.setSelectedLocation((Location)marker.getTag());
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMainActivityState.setSelectedLocation(null);
            }
        });
        mMap.setOnInfoWindowClickListener(this);
    }

    public void RefreshMarkers()
    {
        mMarkers.clear();
        mMap.clear();
        GenerateMarkers();
    }

    public void ChangeToEditMode()
    {
        Marker selectedMarker = GenerateMarker(mMainActivityState.getTemporaryLocation());
        selectedMarker.setDraggable(true);
        mEditFragmentCallback.UpdateCoordinateUI(selectedMarker.getPosition());

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                mEditFragmentCallback.UpdateCoordinateUI(marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                mEditFragmentCallback.UpdateCoordinateUI(marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mEditFragmentCallback.UpdateCoordinateUI(marker.getPosition());
            }
        });
    }

    private void AddMarker(LatLng latLng)
    {
        Location newLocation = new Location();
        newLocation.setLat(latLng.latitude);
        newLocation.setLng(latLng.longitude);
        new LocationAddDialog(getContext(), getActivity(), mMainActivityState, newLocation);
    }

    private void GenerateMarkers()
    {
        for(Location location : mLocationLog.getLocations())
        {
            mMarkers.add(GenerateMarker(location));
        }
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
        Location location = mMainActivityState.getTemporaryLocation();
        new LocationDetailDialog(getContext(), getActivity(), mMainActivityState, location);
    }

}