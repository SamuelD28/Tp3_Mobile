package com.samdube.tp3_mobile.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;

import java.util.ArrayList;

/**
 * This shows how to create a simple activity with a raw MapView and add a marker to it. This
 * requires forwarding all the important lifecycle methods onto MapView.
 */
public class MapFragment extends SupportMapFragment {

    private GoogleMap mMap;
    private LocationLog mLocationLog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mLocationLog = LocationLog.GetInstance();

        getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.setOnMapClickListener(latLng -> {
                MarkerOptions marker = new MarkerOptions().position(latLng);
                marker.draggable(true);
                mMap.addMarker(marker);
            });
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
            mMap.setOnMarkerClickListener(marker -> {
                marker.remove();

                return true;
            });
            GenerateMarker();
        });


    }

    public void GenerateMarker()
    {
        for(Location location : mLocationLog.getmLocations())
        {
            LatLng latlng = new LatLng(location.getLat(), location.getLng());
            MarkerOptions mk = new MarkerOptions().position(latlng);
            mk.draggable(true);
            mMap.addMarker(mk);
        }
    }
}