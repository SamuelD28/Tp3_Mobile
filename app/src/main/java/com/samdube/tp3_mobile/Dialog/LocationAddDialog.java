package com.samdube.tp3_mobile.Dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Abstract.LocationFormDialog;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class LocationAddDialog extends LocationFormDialog {

    public LocationAddDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context, activity, mainActivityState, location);
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
                if(FormVerification()){
                    ApplyChangeToLocation();
                    mLocationLog.AddLocation(mLocation);
                    mMainActivityState.RefreshState();
                    mCurrentDialog.dismiss();
                }
            }
        };
    }

    @Override
    public View.OnClickListener HandleCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDialog.dismiss();
            }
        };
    }

}
