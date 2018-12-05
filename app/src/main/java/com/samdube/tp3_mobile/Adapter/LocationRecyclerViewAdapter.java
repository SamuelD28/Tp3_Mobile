package com.samdube.tp3_mobile.Adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

import java.util.List;

/** Class used to instantiate a new recycler view to be used for displaying tasks.
 * We dont use TaskLog taskList directly in here because i want to make this class more independant.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder>{

    private List<Location> mLocations; //Property that holds the task list.

    /** Constructor for the Recycler View adapter. We need a list argument to instantiate the class
     * @param locations List to instantiate the recycler view with.
     */
    public LocationRecyclerViewAdapter(List<Location> locations) {
        this.mLocations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_location_card, parent, false);
        return new LocationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {

        if((mLocations == null) || (mLocations.size() == 0)) {
            //If nothing is ccontained inside the tasklist, we display a default card to the user
            holder.mName.setText("Aucune location Disponible");
        } else {
            Location location = mLocations.get(position); //We retrieve the corresponding task that was clicked inside the recycler view

            holder.mName.setText(location.getName());
            holder.mLatitude.setText(String.valueOf(location.getLat()));
            holder.mLongitutde.setText(String.valueOf(location.getLng()));
        }
    }

    /** Methos that returns the number of task inside the tasklist
     * @return Number of tasks
     */
    @Override
    public int getItemCount() {
        return ((mLocations != null) && (mLocations.size() != 0)? mLocations.size(): 0);
    }

    /** Class used by the recycler view to create new list item.
     *  Uses the fragment_task_card.
     */
    static class LocationViewHolder extends RecyclerView.ViewHolder{

        private TextView mName = null;
        private TextView mLatitude = null;
        private TextView mLongitutde = null;

        /** Constructor for the ViewHolder. We select item from the view here.
         * @param itemView The created view
         */
        public LocationViewHolder(View itemView){
            super(itemView);
            this.mName = itemView.findViewById(R.id.location_card_name);
            this.mLatitude = itemView.findViewById(R.id.location_card_latitude);
            this.mLongitutde = itemView.findViewById(R.id.location_card_longitude);
        }
    }

}
