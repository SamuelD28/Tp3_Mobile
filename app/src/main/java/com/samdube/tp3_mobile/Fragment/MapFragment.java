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
import com.google.android.gms.maps.model.MapStyleOptions;
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

/**
 * Fragment used to interact with the location inside the database
 */
public class MapFragment
        extends SupportMapFragment
        implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    //Logic Variable
    private GoogleMap mMap;                             //Google map
    private LocationLog mLocationLog;                   //Location log for interacting with the database
    private MainActivityState mMainActivityState;       //Application State
    private ArrayList<Marker> mMarkers;                 //List of all the markers inside the map
    private EditFragmentCallback mEditFragmentCallback; //Callback of the locatino edit fragment

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mMarkers = new ArrayList<>();
        //Cast the activity to required interface. We don't catch the error because
        // we want to make sure the parent activity implements the interface.
        mMainActivityState = (MainActivityState) getActivity();
        mEditFragmentCallback = (EditFragmentCallback) getActivity();

        mLocationLog = LocationLog.GetInstance(getContext());

        //Retrieves the google map
        getMapAsync(this);
    }

    /**
     * Function that retrieve the google map.
     *
     * @param googleMap Google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_map));
        //Change the map mode to the application current mode
        switch (mMainActivityState.getCurrentMode()) {
            case ADD:
                ChangeToAddMode();
                break;
            case EDIT:
                ChangeToEditMode();
                break;
            case INFO:
                ChangeToInfoMode();
                break;
        }
    }

    /**
     * Function that change the map to add mode
     */
    private void ChangeToAddMode() {
        //Generate all the markers
        GenerateMarkers();
        //Set the required listeners
        mMap.setOnMarkerClickListener(HandleMarkerClick(false));
        mMap.setOnMapClickListener(HandleMapClick(true));
    }

    /**
     * Function that change the map to info mode
     */
    private void ChangeToInfoMode() {
        //Generate all the markers
        GenerateMarkers();
        //Set the required listeners
        mMap.setOnMarkerClickListener(HandleMarkerClick(true));
        mMap.setOnMapClickListener(HandleMapClick(false));
    }

    /**
     * Function that change the map to edit mode
     */
    private void ChangeToEditMode() {
        //Generate the selected location marker
        Marker selectedMarker = GenerateMarker(mMainActivityState.getTemporaryLocation());
        selectedMarker.setDraggable(true);
        //Set the required listeners
        mMap.setOnMarkerClickListener(HandleMarkerClick(false));
        mMap.setOnMarkerDragListener(HandleMakerDrag());
        //Update the edit fragment coordinate to reflect the change
        mEditFragmentCallback.UpdateCoordinateUI(selectedMarker.getPosition());
    }

    /**
     * Function that handle the map click listener
     *
     * @param addNewMarker boolean specifying if we add a new marker to the tapped location
     * @return the created listener
     */
    private GoogleMap.OnMapClickListener HandleMapClick(boolean addNewMarker) {
        return latLng -> {
            //Add a new marker if specified
            if (addNewMarker)
                AddLocation(latLng);

            //Remvoe the selected location
            mMainActivityState.setSelectedLocation(null);
        };
    }

    /**
     * Function that handle the marker click listener.
     *
     * @param showInfoWindow boolean to indicate if we show the info window when we click marker
     * @return the created listener
     */
    private GoogleMap.OnMarkerClickListener HandleMarkerClick(boolean showInfoWindow) {
        return marker -> {
            //Show the info window if specified
            if (showInfoWindow) {
                marker.showInfoWindow();
                mMap.setOnInfoWindowClickListener(this);
            }
            //Set the selected location
            mMainActivityState.setSelectedLocation((Location) marker.getTag());
            return true;
        };
    }

    /**
     * Function that handles the marker drag listener
     *
     * @return the create listener
     */
    private GoogleMap.OnMarkerDragListener HandleMakerDrag() {
        return new GoogleMap.OnMarkerDragListener() {

            //We update the edit fragment coordinate whenever we move the marker
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
        };
    }

    /**
     * Function that refreshes all the marker on the map
     */
    public void RefreshMarkers() {
        mMarkers.clear();
        mMap.clear();
        GenerateMarkers();
    }

    /**
     * Function that adds a new location on the map. Creates a new dialog
     * prompting the user to enter the required information for adding
     * a new location
     *
     * @param latLng Coordinate of marker
     */
    private void AddLocation(LatLng latLng) {
        Location newLocation = new Location();
        newLocation.setLat(latLng.latitude);
        newLocation.setLng(latLng.longitude);
        new LocationAddDialog(getContext(), getActivity(), mMainActivityState, newLocation);
    }

    /**
     * Function that generate all the markers based on all
     * the locations inside the database. Displays them on
     * the map.
     */
    private void GenerateMarkers() {
        for (Location location : mLocationLog.getLocations()) {
            mMarkers.add(GenerateMarker(location));
        }
    }


    /**
     * Function that generate one marker based on a location object.
     * Displays it in the map
     *
     * @param location Location used to generate a marker
     * @return the create marker
     */
    private Marker GenerateMarker(Location location) {
        //We create the marker options for the marker
        MarkerOptions mkOptions = GenerateMarkerOptions(location);
        //We create a new marker and add it to the map object
        Marker mk = mMap.addMarker(mkOptions);
        //We set the tag of the marker to hold the location object. We can access it more easily outside the function
        mk.setTag(location);
        return mk;
    }

    /**
     * Function that generate the markeroptions used to
     * create a new marker for the map
     *
     * @param location Location used to generate options
     * @return the create marker options
     */
    private MarkerOptions GenerateMarkerOptions(Location location) {
        //Object needed for creating marker options
        MarkerOptions mkOptions = new MarkerOptions();
        LatLng latlng = new LatLng(location.getLat(), location.getLng());

        //Sets the different options of the marker
        mkOptions.position(latlng);
        mkOptions.title(location.getName());
        mkOptions.icon(GenerateMarkerIcon(location.getCategory()));

        return mkOptions;
    }

    /**
     * Function that creates a icon that will be displayed inside t
     * the marker
     *
     * @param locationCategory Location category used to determine which image to display
     * @return the bitmapdescriptor needed by the marker
     */
    private BitmapDescriptor GenerateMarkerIcon(Category locationCategory) {
        //Both objects needed to generate an icon for a marker
        IconGenerator iconGenerator = new IconGenerator(getContext());
        ImageView image = new ImageView(getContext());

        //Set the appropriate image based on the location category
        switch (locationCategory) {
            case Hotel:
                image.setImageResource(R.drawable.ic_hotel);
                break;
            case Touristic:
                image.setImageResource(R.drawable.ic_touristic);
                break;
            case Restaurant:
                image.setImageResource(R.drawable.ic_restaurant);
                break;
            case Entertainment:
                image.setImageResource(R.drawable.ic_entertainment);
                break;
        }

        //Create the icon bitmap with the image view
        iconGenerator.setContentView(image);
        Bitmap bitmap = iconGenerator.makeIcon();

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /** Function that handle when the marker info window is clicked. Display
     *  a location detail form
     * @param marker marker clicked
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Location location = mMainActivityState.getTemporaryLocation();
        new LocationDetailDialog(getContext(), getActivity(), mMainActivityState, location);
    }

}