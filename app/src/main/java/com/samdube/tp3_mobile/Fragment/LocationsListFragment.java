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
import com.samdube.tp3_mobile.Abstract.MainActivityState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;
import com.samdube.tp3_mobile.Dialog.LocationDetailDialog;

/**
 * Fragment used for displaying a list of all the location inside the database
 */
public class LocationsListFragment
        extends Fragment implements RecyclerItemClickListener.OnRecyclerClickListener {

    //Logic Variable
    private RecyclerView mLocationsRecyclerView;    //Recycler view for displaying the list
    private LocationLog mLocationLog;               //Location log for interacting with the database
    private MainActivityState mMainActivityState;   //Application state

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container.findViewById(R.id.location_list_root));

        //Find the recycler view
        mLocationsRecyclerView = view.findViewById(R.id.locations_list_recyclerView);
        mLocationLog = LocationLog.GetInstance(getContext());
        mMainActivityState = (MainActivityState) getActivity();

        //Generate the recycler with its required components
        GenerateRecyclerView();
        return view;
    }

    /**
     * Function that regenerate the recycler view
     */
    public void RefreshLocationsList() {
        GenerateRecyclerView();
    }

    /**
     * Function that generate the recycler view and its adapter for displaying data
     */
    private void GenerateRecyclerView() {
        //Set the layout manager for the layout view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mLocationsRecyclerView.setLayoutManager(linearLayoutManager);
        //Set the item touch listener
        mLocationsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mLocationsRecyclerView, this));
        //Set the recycler view adapter
        LocationRecyclerViewAdapter mTaskRecyclerViewAdapter = new LocationRecyclerViewAdapter(getContext());
        mLocationsRecyclerView.setAdapter(mTaskRecyclerViewAdapter);
    }

    /**
     * Event listener when a view holder inside the item listener is clicked
     *
     * @param view     View object that triggered the event
     * @param position postion of the item that was clicked
     */
    @Override
    public void onItemClick(View view, int position) {
        Location location = mLocationLog.getLocations().get(position);
        mMainActivityState.setSelectedLocation(location);
        new LocationDetailDialog(getContext(), getActivity(), mMainActivityState, location);
    }

    /**
     * Function that handle a long click on a view holder inside the recycler view. NOT IMPLEMENTED
     *
     * @param view     View that triggered the event
     * @param position Position of the view holder that was clicked
     */
    @Override
    public void onItemLongClick(View view, int position) {
        //Not implemented
    }
}
