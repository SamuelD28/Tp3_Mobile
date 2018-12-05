package com.samdube.tp3_mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samdube.tp3_mobile.Adapter.LocationRecyclerViewAdapter;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

public class LocationsListFragment extends Fragment {

    private LocationLog mLocationLog;
    private RecyclerView mLocationsRecyclerView;
    private LocationRecyclerViewAdapter mTaskRecyclerViewAdapter;   //Adapter for setting up the recycler view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        mLocationLog = LocationLog.GetInstance();

        mLocationsRecyclerView = view.findViewById(R.id.locations_list_recyclerView);
        //Set the layout manager for the layout view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mLocationsRecyclerView.setLayoutManager(linearLayoutManager); //Might need to acess context from activity
        //Set the event listener when a item in the recycler view is taped
//        mLocationsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mTaskRecyclerView, this));
        //Set the adapater for the recycler view
        mTaskRecyclerViewAdapter = new LocationRecyclerViewAdapter(mLocationLog.getmLocations());
        mLocationsRecyclerView.setAdapter(mTaskRecyclerViewAdapter);mLocationsRecyclerView = view.findViewById(R.id.locations_list_recyclerView);

        return view;
    }
}
