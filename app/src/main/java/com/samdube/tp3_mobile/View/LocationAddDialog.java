package com.samdube.tp3_mobile.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class LocationAddDialog extends AlertDialog {

    private Activity mActivity;
    private Context mContext;
    private LatLng mLatLng;

    public LocationAddDialog(Context context, Activity activity, LatLng latLng) {
        super(context);
        mActivity = activity;
        mContext = context;
        mLatLng = latLng;

        final Builder builder = new Builder(context);
        builder.setView(GenerateLocationDetailView(activity));
        builder.setCancelable(true)
                .setNeutralButton("Sauvegarder", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNegativeButton("Annuler", (dialog, id) ->{
                    //Suprimer la location dans la db
                    dialog.cancel();
                });

        final AlertDialog locationDetail = builder.create();
        locationDetail.show();
    }


    private View GenerateLocationDetailView(Activity activity)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_location_details, activity.findViewById(R.id.location_details_root));

        EditText locationNameInput = view.findViewById(R.id.location_details_nameInput);
        EditText locationDescriptionInput = view.findViewById(R.id.location_details_descInput);
        Spinner locationCategroyInput = view.findViewById(R.id.location_details_categoryInput);

        ArrayAdapter<Location.Category> adapter = new ArrayAdapter<Location.Category>(getContext(), R.layout.spinner_item, Location.Category.values());
        locationCategroyInput.setAdapter(adapter);

        return view;
    }
}
