package com.samdube.tp3_mobile.Interface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.samdube.tp3_mobile.R;

public abstract class LocationFormDialog extends AlertDialog {

    //ui
    protected TextView mLocationFormTitle;
    protected EditText mLocationNameInput;
    protected EditText mLocationDescInput;
    protected Spinner mLocationCategorySpinner;

    //vars
    protected Activity mActivity;
    protected Context mContext;
    protected IApplicationState mApplicationState;

    public LocationFormDialog(Context context, Activity activity, IApplicationState applicationState) {
        super(context);
        mActivity = activity;
        mContext = context;
        mApplicationState = applicationState;

        ShowLocationDialog(context, activity);
    }

    private void ShowLocationDialog(Context context, Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(CreateLocationForm(activity));
        builder.setCancelable(true);
        builder.create().show();
    }

    private View CreateLocationForm(Activity activity)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_location_form, activity.findViewById(R.id.location_form_root));

        mLocationFormTitle = view.findViewById(R.id.location_form_title);
        mLocationNameInput = view.findViewById(R.id.location_form_nameInput);
        mLocationDescInput = view.findViewById(R.id.location_form_descInput);
        mLocationCategorySpinner = view.findViewById(R.id.location_form_categoryInput);

        return view;
    }

    public abstract void InitiateLocationInputs();

}
