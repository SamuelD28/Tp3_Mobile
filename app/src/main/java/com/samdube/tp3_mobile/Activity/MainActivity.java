package com.samdube.tp3_mobile.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samdube.tp3_mobile.Fragment.LocationAddFragment;
import com.samdube.tp3_mobile.Fragment.LocationEditFragment;
import com.samdube.tp3_mobile.Fragment.LocationsListFragment;
import com.samdube.tp3_mobile.Fragment.MapFragment;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.samdube.tp3_mobile.Activity.MainActivity.ButtonState.Active;
import static com.samdube.tp3_mobile.Activity.MainActivity.ButtonState.NonActive;

public  class       MainActivity
        extends     DualFragmentActivity
        implements  View.OnClickListener, IApplicationState {

    public enum ButtonState{
        Active,
        NonActive
    }

    private Button mButtonAdd;
    private Button mButtonInfo;
    private Button mButtonEdit;
    private ArrayList<Button> mModeButtons;
    private Location mSelectedLocation;
    private Mode mCurrentMode;

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

        HandleModeStateChange(Mode.INFO);
    }

    //Interface implementation
    @Override
    public void HandleModeStateChange(Mode newMode) {
        if(newMode != mCurrentMode){

            if(newMode == Mode.EDIT && mSelectedLocation == null)
                Toast.makeText(this, "Cant change to edit mode", Toast.LENGTH_SHORT).show();
            else{
                mCurrentMode = newMode;
                ChangeButtonsStyle(newMode);

                switch (newMode) {
                    case ADD: setTopFragment(new LocationAddFragment());
                        break;
                    case EDIT: setTopFragment(new LocationEditFragment());
                        break;
                    case INFO: setTopFragment(new LocationsListFragment());
                        break;
                }

                setMainFragment(new MapFragment());
            }
        }
    }
    @Override
    public Mode GetCurrentMode() {
        return mCurrentMode;
    }
    @Override
    public Location GetSelectedLocation() {
        return mSelectedLocation;
    }
    @Override
    public void SetSelectedLocation(Location location) {
        mSelectedLocation = location;
    }

    private void ChangeButtonsStyle(Mode mode)
    {
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

    private void ChangeButtonState(Button btn ,ButtonState state)
    {
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
            case R.id.main_buttonAdd: HandleModeStateChange(Mode.ADD); break;
            case R.id.main_buttonEdit: HandleModeStateChange(Mode.EDIT); break;
            case R.id.main_buttonInfo: HandleModeStateChange(Mode.INFO); break;
        }
    }
}
