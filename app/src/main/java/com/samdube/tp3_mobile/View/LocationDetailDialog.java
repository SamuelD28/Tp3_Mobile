package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.samdube.tp3_mobile.Interface.IModeState;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class LocationDetailDialog extends AlertDialog {

    private Context mContext;
    private Activity mActivity;
    private Location mLocation;
    private IModeState mModeState;

    public LocationDetailDialog(Context context, Activity activity, Location location, IModeState modeState) {
        super(context);

        mModeState = modeState;
        mActivity = activity;
        mContext = context;
        mLocation = location;

        final Builder builder = new Builder(context);
        builder.setView(GenerateLocationDetailView(location, activity));
        builder.setCancelable(true)
                .setNeutralButton("Modifier Coordonnée", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNegativeButton("Supprimer", (dialog, id) ->{
                    //Suprimer la location dans la db
                    dialog.cancel();
                });

        final AlertDialog locationDetail = builder.create();
        locationDetail.show();
    }

    //This need to get extracted
    private View GenerateLocationDetailView(Location location, Activity activity)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_location_details, activity.findViewById(R.id.location_details_root));

        EditText locationNameInput = view.findViewById(R.id.location_details_nameInput);
        EditText locationDescriptionInput = view.findViewById(R.id.location_details_descInput);
        Spinner  locationCategroyInput = view.findViewById(R.id.location_details_categoryInput);

        ArrayAdapter<Location.Category> adapter = new ArrayAdapter<Location.Category>(getContext(), R.layout.spinner_item, Location.Category.values());
        locationCategroyInput.setAdapter(adapter);

        locationNameInput.setText(location.getName());
        locationDescriptionInput.setText(location.getDescription());

        return view;
    }
}
