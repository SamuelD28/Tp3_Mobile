package com.samdube.tp3_mobile.Interface;

import com.google.android.gms.maps.model.Marker;
import com.samdube.tp3_mobile.Model.Location;

public interface IModeState {

    void HandleModeStateChange(Mode newMode);
    Mode GetCurrentMode();
    Location GetSelectedLocation();
    void SetSelectedLocation(Location location);

    enum Mode{
        ADD,
        EDIT,
        INFO
    }
}
