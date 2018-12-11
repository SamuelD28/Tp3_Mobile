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


public class LocationEditFragment extends Fragment {

    public interface EditFragmentCallback{
        void UpdateCoordinateUI(LatLng lat);
    }

    private MainActivityState mMainActivityState;
    private EditFragmentCallback mUpdateCoordinate;
    private TextView mLongitudeInput;
    private TextView mLatitudeInput;
    private Location mSelectedLocation;
    private LocationLog mLocationLog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_edit, getActivity().findViewById(R.id.locations_edit_root));

        //Need to add try catch around them
        mMainActivityState = (MainActivityState)getActivity();
        mUpdateCoordinate = (EditFragmentCallback)getActivity();
        mSelectedLocation = mMainActivityState.getTemporaryLocation();
        mLocationLog = LocationLog.GetInstance(getContext());

        Button confirmButton = view.findViewById(R.id.location_edit_confirmBtn);
        confirmButton.setOnClickListener(HandleConfirm());
        Button cancelBtn = view.findViewById(R.id.location_edit_cancelBtn);
        cancelBtn.setOnClickListener(v -> mMainActivityState.ChangeActivityMode(Mode.INFO));

        mLatitudeInput = view.findViewById(R.id.location_edit_latitudeInput);
        mLongitudeInput = view.findViewById(R.id.location_edit_longitudeInput);

        return view;
    }

    private View.OnClickListener HandleConfirm()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationLog.UpdateLocation(mSelectedLocation);
                mMainActivityState.ChangeActivityMode(Mode.INFO);
            }
        };
    }

    public void UpdateLongitude(double lng) {
        mSelectedLocation.setLng(lng);
        mLongitudeInput.setText(String.valueOf(mSelectedLocation.getLng()));
    }

    public void UpdateLatitutde(double lat) {
        mSelectedLocation.setLat(lat);
        mLatitudeInput.setText(String.valueOf(mSelectedLocation.getLat()));
    }
}
