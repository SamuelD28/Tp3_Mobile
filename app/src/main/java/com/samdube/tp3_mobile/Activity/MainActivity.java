package com.samdube.tp3_mobile.Activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samdube.tp3_mobile.Fragment.LocationsListFragment;
import com.samdube.tp3_mobile.Fragment.MapFragment;
import com.samdube.tp3_mobile.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.samdube.tp3_mobile.Activity.MainActivity.ButtonState.*;

public class MainActivity extends DualFragmentActivity implements View.OnClickListener, MapFragment.ModeState {

    public enum ButtonState{
        Active,
        NonActive
    }

    public enum Mode{
        ADD,
        EDIT,
        INFO
    }

    private Button mButtonAdd;
    private Button mButtonInfo;
    private Button mButtonEdit;
    private ArrayList<Button> mModeButtons;

    private LocationsListFragment mLocationListFragment;
    private MapFragment mMapFragment;

    private Mode mCurrentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationListFragment = new LocationsListFragment();
        mMapFragment = new MapFragment();

        setTopFragment(mLocationListFragment);
        setMainFragment(mMapFragment);

        mButtonAdd = findViewById(R.id.main_buttonAdd);
        mButtonInfo = findViewById(R.id.main_buttonInfo);
        mButtonEdit = findViewById(R.id.main_buttonEdit);

        mButtonAdd.setOnClickListener(this);
        mButtonInfo.setOnClickListener(this);
        mButtonEdit.setOnClickListener(this);

        mModeButtons = new ArrayList<>();
        mModeButtons.addAll(Arrays.asList(mButtonAdd, mButtonInfo, mButtonEdit));

        mCurrentMode = Mode.INFO;
        HandleModeStateChange(mCurrentMode);
    }

    @Override
    public void HandleModeStateChange(Mode newMode)
    {
        if(newMode != mCurrentMode){
            mCurrentMode = newMode;
            Toast.makeText(this, "Mode Changed to " + newMode.toString(), Toast.LENGTH_SHORT).show();
            ChangeButtonsStyle(newMode);
        }
    }

    @Override
    public Mode GetCurrentMode() {
        return mCurrentMode;
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
