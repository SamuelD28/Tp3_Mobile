package com.samdube.tp3_mobile.Dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Abstract.LocationFormDialog;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Abstract.MainActivityState.*;


public class LocationDetailDialog extends LocationFormDialog {


    public LocationDetailDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context, activity, mainActivityState, location);
        InitiateLocationInputs();
    }

    @Override
    public void InitiateLocationInputs() {
        mLocationFormTitle.setText(R.string.location_edit_title);
        mLocationNameInput.setText(mLocation.getName());
        mLocationDescInput.setText(mLocation.getDescription());
        mLocationCategorySpinner.setAdapter(SpinnerCategoryAdapter.CreateAdapter(getContext()));
        mEditButton.setVisibility(View.VISIBLE);
        mEditButton.setOnClickListener(HandleEdit());

        mConfirmButton.setText(R.string.btn_save);
        mCancelButton.setText(R.string.btn_delete);
    }

    public View.OnClickListener HandleEdit()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivityState.ChangeActivityMode(Mode.EDIT);
                mCurrentDialog.dismiss();
            }
        };
    }

    @Override
    public View.OnClickListener HandleConfirm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FormVerification()){
                    ApplyChangeToLocation();
                    mLocationLog.UpdateLocation(mLocation.getId(), mLocation);
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
                mLocationLog.DeleteTask(mLocation.getId());
                mMainActivityState.RefreshState();
                mCurrentDialog.dismiss();
            }
        };
    }
}
