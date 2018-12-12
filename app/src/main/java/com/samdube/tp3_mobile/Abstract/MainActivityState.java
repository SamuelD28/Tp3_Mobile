package com.samdube.tp3_mobile.Abstract;

import com.samdube.tp3_mobile.Activity.DualFragmentActivity;
import com.samdube.tp3_mobile.Model.Location;

/**
 * Abstract class used to interact with the application state.
 */
public abstract class MainActivityState
        extends DualFragmentActivity {

    /**
     * Enumeration use to change the current mode of the application
     */
    public enum Mode {
        ADD,
        EDIT,
        INFO
    }

    //Logic variables
    protected Mode mCurrentMode;            //Hold the current mode of the application
    protected Location mSelectedLocation;   //Hold the selected location of the application


    /**
     * Function that change the current mode of the application with a new mode
     *
     * @param newMode Mode to change the application to
     */
    public abstract void ChangeActivityMode(Mode newMode);

    /**
     * Function that reloads the current state of the application
     */
    public abstract void RefreshState();

    /**
     * Getter to retireve the current mode of the application
     *
     * @return current mode of the application
     */
    public Mode getCurrentMode() {
        return mCurrentMode;
    }

    /**
     * Return a new location based on the selected location. Used to prevent
     * unwanted modification of the original location
     *
     * @return new location based on the selected location
     */
    public Location getTemporaryLocation() {
        return new Location(mSelectedLocation);
    }

    /**
     * Set the selected locatino to the specified location parameter
     *
     * @param location Location selected
     */
    public void setSelectedLocation(Location location) {
        mSelectedLocation = location;
    }
}
