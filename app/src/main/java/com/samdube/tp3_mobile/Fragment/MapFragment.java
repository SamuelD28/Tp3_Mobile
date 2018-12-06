package com.samdube.tp3_mobile.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;
import com.samdube.tp3_mobile.Marker.LocationClusterItem;
import com.samdube.tp3_mobile.Marker.LocationClusterRenderer;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Model.Location.Category;

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

            mMap.setOnMapClickListener(latLng -> {

            });
            PopulateMapWithMarkers();
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

    public void PopulateMapWithMarkers()
    {
        for(Location location : mLocationLog.getmLocations())
        {
            GenerateMarker(location);
        }
        mMap.setOnInfoWindowClickListener(this);
    }

     public Marker GenerateMarker(Location location)
     {
         //We create the marker options for the marker
         MarkerOptions mkOptions = GenerateMarkerOptions(location);
         //We create a new marker and add it to the map object
         Marker mk = mMap.addMarker(mkOptions);
         //We set the tag of the marker to hold the location obejct. We can access it more easily outside the function
         mk.setTag(location);
         return mk;
     }

     public MarkerOptions GenerateMarkerOptions(Location location)
     {
         //Object needed for creating marker options
         MarkerOptions mkOptions = new MarkerOptions();
         LatLng latlng = new LatLng(location.getLat(), location.getLng());

         //Sets the different options of the marker
         mkOptions.position(latlng);
         mkOptions.title(location.getName());
         mkOptions.icon(GenerateMarkerIcon(location.getCategory()));
         mkOptions.draggable(true); //This option will need to change based on the mode

         return mkOptions;
     }

     public BitmapDescriptor GenerateMarkerIcon(Category locationCategory)
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
    public void onInfoWindowClick(Marker marker) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Location location = (Location) marker.getTag();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_location_details, (ViewGroup) getActivity().findViewById(R.id.location_details_root));

        EditText locationName = view.findViewById(R.id.location_details_name);
        EditText locationDescription = view.findViewById(R.id.location_details_description);

        locationName.setText(location.getName());
        locationDescription.setText(location.getDescription());

        builder.setIcon(R.drawable.ic_hotel);
        builder.setView(view);
        builder.setCancelable(true)
                .setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}