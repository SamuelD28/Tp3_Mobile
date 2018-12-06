package com.samdube.tp3_mobile.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.samdube.tp3_mobile.Marker.LocationClusterItem;
import com.samdube.tp3_mobile.Marker.LocationClusterRenderer;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import java.util.ArrayList;

/**
 * This shows how to create a simple activity with a raw MapView and add a marker to it. This
 * requires forwarding all the important lifecycle methods onto MapView.
 */
public class MapFragment extends SupportMapFragment implements GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "Map Fragment";

    private GoogleMap mMap;
    private LocationLog mLocationLog;
    private ClusterManager<LocationClusterItem> mClusterManager;
    private LocationClusterRenderer mClusterManagerRenderer;

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
            mClusterManager = new ClusterManager<>(getActivity().getApplicationContext(), mMap);
            mMap.setOnMapClickListener(latLng -> {

                Location location = new Location(latLng.longitude, latLng.latitude, "Miel");
                LocationClusterItem newClusterMarker = new LocationClusterItem(
                                                        latLng,
                                                        "Test",
                                                        "Continuer",
                                                        location
                );
                mClusterManager.addItem(newClusterMarker);
                mClusterManager.cluster();
            });
            GenerateMarker();
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
//            mMap.setOnMarkerClickListener(marker -> {
//                marker.remove();
//
//                return true;
//            });
        });


    }

    private void addMapMarkers(GoogleMap map) {

        if (map != null) {

            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new LocationClusterRenderer(getActivity(), map, mClusterManager, null, null, 50, 50);
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }
            map.setOnInfoWindowClickListener(this);

            for (Location location : mLocationLog.getmLocations()) {
//                Log.d(TAG, "addMapMarkers: location: " + userLocation.getGeo_point().toString());
                try {
                    String snippet = "Hey";

                    int avatar = R.drawable.amu_bubble_mask; // set the default avatar
                    LocationClusterItem newClusterMarker = new LocationClusterItem(
                                                            new LatLng(location.getLat(), location.getLng()),
                                                            location.getName(),
                                                            snippet,
                                                            location
                    );
                    mClusterManager.addItem(newClusterMarker);

                } catch (NullPointerException e) {
                    Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage());
                }
                mClusterManager.cluster();

//            setCameraView();
            }
        }
    }

    public void GenerateMarker()
    {
        for(Location location : mLocationLog.getmLocations())
        {
            LatLng latlng = new LatLng(location.getLat(), location.getLng());
            MarkerOptions mk = new MarkerOptions().position(latlng);
            mk.title("Japon");
            mk.draggable(true);
            mMap.addMarker(mk);
        }
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(marker.getSnippet())
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                            resetSelectedMarker();
//                            mSelectedMarker = marker;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}