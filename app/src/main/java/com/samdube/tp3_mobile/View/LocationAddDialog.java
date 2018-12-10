package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Interface.LocationFormDialog;
import com.samdube.tp3_mobile.Model.Location;
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

        mConfirmButton.setText(R.string.btn_confirm);
        mCancelButton.setText(R.string.btn_cancel);
    }

    @Override
    public View.OnClickListener HandleConfirm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Confirm", Toast.LENGTH_SHORT).show();

                //Ajotuer la verification
                Location location = new Location(mLatLng.latitude,
                                                mLatLng.longitude,
                                                mLocationNameInput.getText().toString(),
                                                mLocationDescInput.getText().toString(),
                        (Location.Category)mLocationCategorySpinner.getSelectedItem());


                mLocationLog.AddLocation(location);
                mCurrentDialog.dismiss();
                mApplicationState.HandleModeStateChange(IApplicationState.Mode.INFO);
            }
        };
    }

    @Override
    public View.OnClickListener HandleCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Cancel", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
