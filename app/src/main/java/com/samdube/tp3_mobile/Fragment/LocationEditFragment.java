package com.samdube.tp3_mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Abstract.MainActivityState.*;


/**
 * Fragment used to display a feedback to the user when editing a location coordinate
 * and provide the logic for saving the coordinate in the appropriate location
 */
public class LocationEditFragment extends Fragment {

    /**
     * Contract interface that the activity needs to implement.
     * Provide a function to update the view inputs coordinate
     * when the user moves a marker location
     */
    public interface EditFragmentCallback {
        void UpdateCoordinateUI(LatLng lat);
    }

    //UI Variables
    private TextView mLongitudeInput;
    private TextView mLatitudeInput;

    //Logic Variables
    private MainActivityState mMainActivityState;   //Application State
    private Location mSelectedLocation;             //Selected location inside the application
    private LocationLog mLocationLog;               //Location log for interacting with the database

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_edit, getActivity().findViewById(R.id.locations_edit_root));

        //Verify that the activity implemented the interface
        EditFragmentCallback activity = (EditFragmentCallback) getActivity();

        //Need to add try catch around them
        mMainActivityState = (MainActivityState) getActivity();
        mSelectedLocation = mMainActivityState.getTemporaryLocation();
        mLocationLog = LocationLog.GetInstance(getContext());

        //Selection of the UI elements
        Button confirmButton = view.findViewById(R.id.location_edit_confirmBtn);
        Button cancelBtn = view.findViewById(R.id.location_edit_cancelBtn);
        mLatitudeInput = view.findViewById(R.id.location_edit_latitudeInput);
        mLongitudeInput = view.findViewById(R.id.location_edit_longitudeInput);

        //Creation fo the listeners
        confirmButton.setOnClickListener(HandleConfirm());
        cancelBtn.setOnClickListener(v -> mMainActivityState.ChangeActivityMode(Mode.INFO));

        return view;
    }

    /**
     * Function that handles the confirm button click in the fragment
     *
     * @return the created listener
     */
    private View.OnClickListener HandleConfirm() {
        return v -> {
            mLocationLog.UpdateLocation(mMainActivityState.getTemporaryLocation());
            mMainActivityState.ChangeActivityMode(Mode.INFO);
        };
    }

    /**
     * Function that update the longitude input
     *
     * @param lng new longitude input value
     */
    public void UpdateLongitude(double lng) {
        mSelectedLocation.setLng(lng);
        mLongitudeInput.setText(String.valueOf(mSelectedLocation.getLng()));
    }

    /**
     * Function that update the latitude input
     *
     * @param lat new latitude input value
     */
    public void UpdateLatitude(double lat) {
        mSelectedLocation.setLat(lat);
        mLatitudeInput.setText(String.valueOf(mSelectedLocation.getLat()));
    }
}
