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

public  class       MainActivity
        extends     MainActivityState
        implements  View.OnClickListener, LocationEditFragment.EditFragmentCallback {

    //Should extract
    public enum ButtonState{
        Active,
        NonActive
    }

    private Button mButtonAdd;
    private Button mButtonInfo;
    private Button mButtonEdit;
    private ArrayList<Button> mModeButtons;

    private LocationEditFragment mLocationEditFragment;
    private LocationAddFragment mLocationAddFragment;
    private LocationsListFragment mLocationListFragment;
    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonAdd = findViewById(R.id.main_buttonAdd);
        mButtonInfo = findViewById(R.id.main_buttonInfo);
        mButtonEdit = findViewById(R.id.main_buttonEdit);

        mButtonAdd.setOnClickListener(this);
        mButtonInfo.setOnClickListener(this);
        mButtonEdit.setOnClickListener(this);

        mModeButtons = new ArrayList<>();
        mModeButtons.addAll(Arrays.asList(mButtonAdd, mButtonInfo, mButtonEdit));

        ChangeActivityMode(Mode.INFO);
    }

    @Override
    public void ChangeActivityMode(Mode newMode) {
        if(newMode != mCurrentMode){

            if(newMode == Mode.EDIT && mSelectedLocation == null)
                Toast.makeText(this, "Select a location", Toast.LENGTH_SHORT).show();
            else{
                mCurrentMode = newMode;
                ChangeButtonsStyle(newMode);

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

                mMapFragment = new MapFragment();
                setMainFragment(mMapFragment);
            }
        }
    }

    @Override
    public void RefreshState() {
        if(mLocationListFragment != null)
            mLocationListFragment.RefreshLocationsList();

        if(mMapFragment != null)
            mMapFragment.RefreshMarkers();
    }

    @Override
    public void UpdateCoordinateUI(LatLng lat) {
        mLocationEditFragment.UpdateLongitude(lat.longitude);
        mLocationEditFragment.UpdateLatitutde(lat.latitude);
    }

    private void ChangeButtonsStyle(Mode mode) {
        for(Button btn : mModeButtons)
        {
            ChangeButtonState(btn , NonActive);
        }

        switch(mode)
        {
            case ADD: ChangeButtonState( mButtonAdd, Active); break;
            case EDIT: ChangeButtonState( mButtonEdit, Active); break;
            case INFO: ChangeButtonState( mButtonInfo, Active); break;
        }
    }

    private void ChangeButtonState(Button btn ,ButtonState state) {
        if(state == Active){
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            btn.setTextColor(ContextCompat.getColor(this,R.color.White));
        }
        else if(state == NonActive){
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.White));
            btn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_buttonAdd: ChangeActivityMode(Mode.ADD); break;
            case R.id.main_buttonEdit: ChangeActivityMode(Mode.EDIT); break;
            case R.id.main_buttonInfo: ChangeActivityMode(Mode.INFO); break;
        }
    }
}
