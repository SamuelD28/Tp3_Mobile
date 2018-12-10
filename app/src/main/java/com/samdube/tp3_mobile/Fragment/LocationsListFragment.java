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
import com.samdube.tp3_mobile.Adapter.RecyclerItemClickListener;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;
import com.samdube.tp3_mobile.View.LocationDetailDialog;

public
        class LocationsListFragment
        extends Fragment implements RecyclerItemClickListener.OnRecyclerClickListener {

    private RecyclerView mLocationsRecyclerView;
    private LocationLog mLocationLog;
    private IApplicationState mApplicationState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container.findViewById(R.id.location_list_root));

        mLocationsRecyclerView = view.findViewById(R.id.locations_list_recyclerView);
        mLocationLog = LocationLog.GetInstance();
        mApplicationState = (IApplicationState)getActivity();

        GenerateLocationsList();
        return view;
    }

    private void GenerateLocationsList() {

        //Set the layout manager for the layout view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mLocationsRecyclerView.setLayoutManager(linearLayoutManager); //Might need to acess context from activity

        mLocationsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mLocationsRecyclerView, this));
        //Set the event listener when a item in the recycler view is taped
//        mLocationsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mTaskRecyclerView, this));
        //Set the adapater for the recycler view
        LocationRecyclerViewAdapter mTaskRecyclerViewAdapter = new LocationRecyclerViewAdapter(mLocationLog.getmLocations());
        mLocationsRecyclerView.setAdapter(mTaskRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Location location = mLocationLog.getmLocations().get(position);
        new LocationDetailDialog(getContext(), getActivity(), location);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
