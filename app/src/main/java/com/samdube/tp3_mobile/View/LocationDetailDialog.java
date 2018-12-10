package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.content.Context;

import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Interface.LocationFormDialog;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class LocationDetailDialog extends LocationFormDialog {

    private Location mLocation;

    public LocationDetailDialog(Context context, Activity activity, IApplicationState applicationState, Location location) {
        super(context, activity, applicationState);
        mLocation = location;
        InitiateLocationInputs();
    }

    @Override
    public void InitiateLocationInputs() {
        mLocationFormTitle.setText(R.string.location_edit_title);
        mLocationNameInput.setText(mLocation.getName());
        mLocationDescInput.setText(mLocation.getDescription());
        mLocationCategorySpinner.setAdapter(SpinnerCategoryAdapter.CreateAdapter(getContext()));
    }
}
