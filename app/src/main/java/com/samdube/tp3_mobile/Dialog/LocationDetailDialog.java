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


/**
 * Class used to handle the logic for a detail location form
 * Extends location form dialog
 */
public class LocationDetailDialog extends LocationFormDialog {


    /**
     * Constructor for a new edit form location
     *
     * @param context           Context of the application
     * @param activity          Activity that instantiated the dialog
     * @param mainActivityState Application state
     * @param location          Location used to populate the form inputs
     */
    public LocationDetailDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context, activity, mainActivityState, location);
        InitiateLocationInputs();
    }

    /**
     * Function that initiate the form inputs with the proper dat
     */
    @Override
    public void InitiateLocationInputs() {
        //Sets all the text inputs
        mLocationFormTitle.setText(R.string.location_edit_title);
        mLocationNameInput.setText(mLocation.getName());
        mLocationDescInput.setText(mLocation.getDescription());
        mConfirmButton.setText(R.string.btn_save);
        mCancelButton.setText(R.string.btn_delete);

        //Set the category spinner
        SpinnerCategoryAdapter adapter = new SpinnerCategoryAdapter(getContext());
        mLocationCategorySpinner.setAdapter(adapter.getArrayAdapter());
        mLocationCategorySpinner.setSelection(adapter.getCategoryPosition(mLocation.getCategory()));

        //Show the edit coordinate button
        mEditButton.setVisibility(View.VISIBLE);
        mEditButton.setOnClickListener(HandleEdit());

    }

    /**
     * Function that handle the logic for editing the coordinate of a location
     *
     * @return the created listener
     */
    public View.OnClickListener HandleEdit() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change the application state to edit mode
                mMainActivityState.ChangeActivityMode(Mode.EDIT);
                //Close the current dialog
                mCurrentDialog.dismiss();
            }
        };
    }

    /**
     * Function that handles the confirm button click inside the form
     *
     * @return the created listener
     */
    @Override
    public View.OnClickListener HandleConfirm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the form verification succeeded, we process the form
                if (FormVerification()) {
                    //Apply the form changes to mLocation
                    ApplyChangeToLocation();
                    //Update the selected location inside the database
                    mLocationLog.UpdateLocation(mLocation);
                    //Refresh the activity state to show the changes
                    mMainActivityState.RefreshState();
                    //Close the dialog
                    mCurrentDialog.dismiss();
                }
            }
        };
    }

    /**
     * Function that handle the cancel button click inside the form
     *
     * @return the created listener
     */
    @Override
    public View.OnClickListener HandleCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete the selected location inside the database
                mLocationLog.DeleteTask(mLocation.getId());
                //Remove the selected location
                mMainActivityState.setSelectedLocation(null);
                //Refresh the application state to show the changes
                mMainActivityState.RefreshState();
                //Close the dialog
                mCurrentDialog.dismiss();
            }
        };
    }
}
