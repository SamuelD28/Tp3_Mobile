package com.samdube.tp3_mobile.Abstract;


import com.samdube.tp3_mobile.Activity.DualFragmentActivity;
import com.samdube.tp3_mobile.Model.Location;

public abstract
        class MainActivityState
        extends DualFragmentActivity{

    public enum Mode{
        ADD,
        EDIT,
        INFO
    }
    protected Mode mCurrentMode;
    protected Location mSelectedLocation;
    public abstract void ChangeActivityMode(Mode newMode);
    public abstract void RefreshState();
    public Mode getCurrentMode() {
        return mCurrentMode;
    }
    public Location getTemporaryLocation() {
        return new Location(mSelectedLocation);
    }
    public void setSelectedLocation(Location location) {
        mSelectedLocation = location;
    }
}
