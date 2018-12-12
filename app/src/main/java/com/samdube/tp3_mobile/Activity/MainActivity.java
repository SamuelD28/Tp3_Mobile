package com.samdube.tp3_mobile.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.samdube.tp3_mobile.Fragment.LocationAddFragment;
import com.samdube.tp3_mobile.Fragment.LocationEditFragment;
import com.samdube.tp3_mobile.Fragment.LocationsListFragment;
import com.samdube.tp3_mobile.Fragment.MapFragment;
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.samdube.tp3_mobile.Activity.MainActivity.ButtonState.Active;
import static com.samdube.tp3_mobile.Activity.MainActivity.ButtonState.NonActive;

/**
 * Main activity that handles the interaction with the maps
 * and the selected location.
 */
public class MainActivity
        extends MainActivityState
        implements View.OnClickListener, LocationEditFragment.EditFragmentCallback {

    /**
     * Enumeration used for styling purpose for the buttons
     */
    public enum ButtonState {
        Active,
        NonActive
    }

    //UI Variables
    private Button mButtonAdd;
    private Button mButtonInfo;
    private Button mButtonEdit;
    private ArrayList<Button> mModeButtons;

    //Logic Variables
    private LocationEditFragment mLocationEditFragment;     //Contains the current location edit fragment
    private LocationAddFragment mLocationAddFragment;       //Contains the current location add fragment
    private LocationsListFragment mLocationListFragment;    //Contains the current location list fragment
    private MapFragment mMapFragment;                       //Contains the current map fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Selection of the UI elements
        mButtonAdd = findViewById(R.id.main_buttonAdd);
        mButtonInfo = findViewById(R.id.main_buttonInfo);
        mButtonEdit = findViewById(R.id.main_buttonEdit);

        //Creation of the needed listeners
        mButtonAdd.setOnClickListener(this);
        mButtonInfo.setOnClickListener(this);
        mButtonEdit.setOnClickListener(this);

        //Add all the buttons to an array to simplify operations
        mModeButtons = new ArrayList<>();
        mModeButtons.addAll(Arrays.asList(mButtonAdd, mButtonInfo, mButtonEdit));

        //Initilaise the current mode of the application to info
        ChangeActivityMode(Mode.INFO);
    }

    /**
     * Function that change the current state of the application with a new mode
     *
     * @param newMode Mode to change the application to
     */
    @Override
    public void ChangeActivityMode(Mode newMode) {
        //Verify if the requested mode is different from the current mode
        if (newMode != mCurrentMode) {
            //A selected location is needed to go in edit mode
            if (newMode == Mode.EDIT && mSelectedLocation == null)
                Toast.makeText(this, "Select a location", Toast.LENGTH_SHORT).show();
            else {
                //Sets the current mode to the new mode
                mCurrentMode = newMode;

                //Change the buttons style to reflect the mode change
                ChangeButtonsStyle(newMode);

                //Change top fragment to the requested mode
                switch (newMode) {
                    case ADD:
                        mLocationAddFragment = new LocationAddFragment();
                        setTopFragment(mLocationAddFragment);
                        break;
                    case EDIT:
                        mLocationEditFragment = new LocationEditFragment();
                        setTopFragment(mLocationEditFragment);
                        break;
                    case INFO:
                        mLocationListFragment = new LocationsListFragment();
                        setTopFragment(mLocationListFragment);
                        break;
                }

                //Change the main fragment to the requested mode
                mMapFragment = new MapFragment();
                setMainFragment(mMapFragment);
            }
        }
    }

    /**
     * Function that refreshes the current state of the application.
     */
    @Override
    public void RefreshState() {
        //Refresh the recycler view dataset
        if (mLocationListFragment != null)
            mLocationListFragment.RefreshLocationsList();

        //Refresh the map markers
        if (mMapFragment != null)
            mMapFragment.RefreshMarkers();
    }

    /**
     * Function that updates the coordinate inside the edit location fragments.
     *
     * @param lat New Coordinates
     */
    @Override
    public void UpdateCoordinateUI(LatLng lat) {
        mLocationEditFragment.UpdateLongitude(lat.longitude);
        mLocationEditFragment.UpdateLatitude(lat.latitude);
    }

    /**
     * Function that changes the button style to reflect the current mode
     *
     * @param mode
     */
    private void ChangeButtonsStyle(Mode mode) {
        //Remove the active styles of all the buttons
        for (Button btn : mModeButtons) {
            ChangeButtonState(btn, NonActive);
        }
        //Change the active style of the selected mode button
        switch (mode) {
            case ADD:
                ChangeButtonState(mButtonAdd, Active);
                break;
            case EDIT:
                ChangeButtonState(mButtonEdit, Active);
                break;
            case INFO:
                ChangeButtonState(mButtonInfo, Active);
                break;
        }
    }

    /**
     * Function that change the style of the button based on the passed state
     *
     * @param btn   Button to change the style
     * @param state State to change the button to
     */
    private void ChangeButtonState(Button btn, ButtonState state) {
        //Change to the active state
        if (state == Active) {
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            btn.setTextColor(ContextCompat.getColor(this, R.color.White));
        }
        //Change to the non active state
        else if (state == NonActive) {
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.White));
            btn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    /**
     * On click listener used the mode buttons. Changes the activty to the
     * requested mode
     *
     * @param v View that triggered the events
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_buttonAdd:
                ChangeActivityMode(Mode.ADD);
                break;
            case R.id.main_buttonEdit:
                ChangeActivityMode(Mode.EDIT);
                break;
            case R.id.main_buttonInfo:
                ChangeActivityMode(Mode.INFO);
                break;
        }
    }
}
