package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Interface.LocationFormDialog;
import com.samdube.tp3_mobile.R;

public class LocationAddDialog extends LocationFormDialog {

    private LatLng mLatLng;

    public LocationAddDialog(Context context, Activity activity, IApplicationState applicationState, LatLng latLng) {
        super(context, activity, applicationState);
        mLatLng = latLng;
        InitiateLocationInputs();
    }

    public void InitiateLocationInputs()
    {
        mLocationFormTitle.setText(R.string.location_add_title);
        mLocationCategorySpinner.setAdapter(SpinnerCategoryAdapter.CreateAdapter(getContext()));
    }

}
