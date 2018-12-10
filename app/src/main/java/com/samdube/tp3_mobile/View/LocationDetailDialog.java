package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.samdube.tp3_mobile.Adapter.SpinnerCategoryAdapter;
import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class LocationDetailDialog extends AlertDialog {

    public LocationDetailDialog(Context context, Activity activity, Location location, IApplicationState modeState) {
        super(context);

        Builder builder = new Builder(context);
        builder.setView(GenerateLocationDetailView(location, activity));
        builder.setCancelable(true);
        builder.create().show();
    }

    //This need to get extracted
    private View GenerateLocationDetailView(Location location, Activity activity)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_location_details, activity.findViewById(R.id.location_details_root));

        EditText locationNameInput = view.findViewById(R.id.location_details_nameInput);
        EditText locationDescriptionInput = view.findViewById(R.id.location_details_descInput);
        Spinner  locationCategroyInput = view.findViewById(R.id.location_details_categoryInput);

        locationCategroyInput.setAdapter(SpinnerCategoryAdapter.CreateAdapter(getContext()));

        locationNameInput.setText(location.getName());
        locationDescriptionInput.setText(location.getDescription());

        return view;
    }
}
