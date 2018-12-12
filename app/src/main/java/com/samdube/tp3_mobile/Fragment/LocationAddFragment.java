package com.samdube.tp3_mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samdube.tp3_mobile.R;

/**
 * Fragment that display a message to the user
 * for choosing a location on the map
 */
public class LocationAddFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_add, getActivity().findViewById(R.id.locations_add_root));
    }
}
