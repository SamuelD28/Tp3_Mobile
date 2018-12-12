package com.samdube.tp3_mobile.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

/**
 * Class used to instantiate a new recycler view to be used for displaying tasks.
 * We dont use TaskLog taskList directly in here because i want to make this class more independant.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {

    private final LocationLog mLocationLog; //Property that holds the task list.

    /**
     * Constructor for the Recycler View adapter. We need a list argument to instantiate the class
     */
    public LocationRecyclerViewAdapter(Context context) {
        this.mLocationLog = LocationLog.GetInstance(context);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_location_card, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        if ((mLocationLog.getLocations().isEmpty())) {
            //If nothing is ccontained inside the tasklist, we display a default card to the user
            holder.mName.setText(R.string.no_location_available);
        } else {
            Location location = mLocationLog.getLocations().get(position);

            holder.mName.setText(location.getName());

            switch (location.getCategory()) {
                case Entertainment:
                    holder.mCategory.setImageResource(R.drawable.ic_entertainment);
                    break;
                case Restaurant:
                    holder.mCategory.setImageResource(R.drawable.ic_restaurant);
                    break;
                case Hotel:
                    holder.mCategory.setImageResource(R.drawable.ic_hotel);
                    break;
                case Touristic:
                    holder.mCategory.setImageResource(R.drawable.ic_touristic);
                    break;
            }
        }
    }

    /**
     * Methos that returns the number of locations inside the database
     *
     * @return Number of locations
     */
    @Override
    public int getItemCount() {
        return ((mLocationLog != null) && (mLocationLog.getLocations().size() != 0) ? mLocationLog.getLocations().size() : 0);
    }

    /**
     * Class used by the recycler view to create new list item.
     */
    static class LocationViewHolder extends RecyclerView.ViewHolder {

        //UI Variable
        private TextView mName = null;
        private ImageView mCategory = null;

        /**
         * Constructor for the ViewHolder. We select item from the view here.
         *
         * @param itemView The created view
         */
        LocationViewHolder(View itemView) {
            super(itemView);
            this.mName = itemView.findViewById(R.id.location_card_name);
            this.mCategory = itemView.findViewById(R.id.location_card_category);
        }
    }

}
