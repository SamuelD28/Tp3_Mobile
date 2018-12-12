package com.samdube.tp3_mobile.Dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Abstract.LocationFormDialog;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

/**
 * Class that handles the logic needed for the Add location form. Extend
 * the abstract class Location form dialog
 */
public class LocationAddDialog extends LocationFormDialog {

    /**
     * Constructor for the add location dialog
     *
     * @param context           Context of the application
     * @param activity          Activity that instantied the dialog
     * @param mainActivityState Application state
     * @param location          Location used to populate the form
     */
    public LocationAddDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context, activity, mainActivityState, location);
        InitiateLocationInputs();
    }

    /**
     * Function that initialise the inputs value inside the form
     */
    public void InitiateLocationInputs() {
        //Set the spinner adapater
        SpinnerCategoryAdapter adapter = new SpinnerCategoryAdapter(getContext());
        mLocationCategorySpinner.setAdapter(adapter.getArrayAdapter());
        //Sets the text inputs
        mLocationFormTitle.setText(R.string.location_add_title);
        mConfirmButton.setText(R.string.btn_confirm);
        mCancelButton.setText(R.string.btn_cancel);
    }

    /** Function that handle the confirm button click inside the form
     * @return the created listener
     */
    @Override
    protected View.OnClickListener HandleConfirm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the form verification passed, we add the location to the database
                if (FormVerification()) {
                    //Apply the changes made in the form to mLocation
                    ApplyChangeToLocation();
                    //Add mLocation inside the database
                    mLocationLog.AddLocation(mLocation);
                    //Refresh the application state to display the changes
                    mMainActivityState.RefreshState();
                    //Close the dialog
                    mCurrentDialog.dismiss();
                }
            }
        };
    }

    /** Function that handles the cancel click inside the form
     * @return the created listener
     */
    @Override
    protected View.OnClickListener HandleCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove the selected location
                mMainActivityState.setSelectedLocation(null);
                //Close the dialog
                mCurrentDialog.dismiss();
            }
        };
    }

}
