package com.samdube.tp3_mobile.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Activity.MainActivity.Mode;
import static com.samdube.tp3_mobile.Model.Location.Category;

public class MapFragment extends SupportMapFragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    public interface ModeState{
        void HandleModeStateChange(Mode newMode);
        Mode GetCurrentMode();
    }

    private GoogleMap mMap;
    private LocationLog mLocationLog;
    private ModeState mModeState; //This hold the parent activity

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mModeState = (ModeState)getActivity();
        mLocationLog = LocationLog.GetInstance();

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(latLng -> { AddMarker(latLng); });
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
        mMap.setOnMarkerClickListener(marker -> RemoveMarker(marker));
        PopulateMapWithMarkers();
    }

    private void AddMarker(LatLng latLng){

        if(mModeState.GetCurrentMode() == Mode.ADD){
            Location newLocation = new Location(latLng.latitude, latLng.longitude, "Japon", "Tres beau", Category.Restaurant);
            GenerateMarker(newLocation);
        }
    }

    private boolean RemoveMarker(Marker marker)
    {
        if(mModeState.GetCurrentMode() == Mode.EDIT)
        {
            marker.remove();
            return true;
        }
        return false;
    }

    private void PopulateMapWithMarkers()
    {
        for(Location location : mLocationLog.getmLocations())
        {
            GenerateMarker(location);
        }
        mMap.setOnInfoWindowClickListener(this);
    }

     private Marker GenerateMarker(Location location)
     {
         //We create the marker options for the marker
         MarkerOptions mkOptions = GenerateMarkerOptions(location);
         //We create a new marker and add it to the map object
         Marker mk = mMap.addMarker(mkOptions);
         //We set the tag of the marker to hold the location obejct. We can access it more easily outside the function
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
         mkOptions.draggable(true); //This option will need to change based on the mode

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
    public void onInfoWindowClick(Marker marker) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Location location = (Location) marker.getTag();
        builder.setView(GenerateLocationDetailView(location));
        builder.setCancelable(true)
                .setNeutralButton("Modifier", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("Supprimer", (dialog, id) -> dialog.cancel());

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private View GenerateLocationDetailView(Location location)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_location_details, (ViewGroup) getActivity().findViewById(R.id.location_details_root));

        EditText locationNameInput = view.findViewById(R.id.location_details_nameInput);
        EditText locationDescriptionInput = view.findViewById(R.id.location_details_descInput);
        Spinner  locationCategroyInput = view.findViewById(R.id.location_details_categoryInput);

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(getContext(), R.layout.spinner_item, Category.values());
        locationCategroyInput.setAdapter(adapter);

        locationNameInput.setText(location.getName());
        locationDescriptionInput.setText(location.getDescription());

        return view;
    }
}