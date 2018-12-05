package com.samdube.tp3_mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samdube.tp3_mobile.R;

public class LocationsListFragment extends Fragment {

    private RecyclerView mLocationsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);
        mLocationsRecyclerView = view.findViewById(R.id.locations_list_recyclerView);
        return view;
    }
}
