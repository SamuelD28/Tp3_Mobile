package com.samdube.tp3_mobile.Abstract;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import static android.support.v4.content.ContextCompat.getColor;
import static com.samdube.tp3_mobile.Model.Location.Category;
import static com.samdube.tp3_mobile.Utils.Validation.Entre;

public abstract class LocationFormDialog extends AlertDialog {

    //ui
    protected TextView mLocationFormTitle;
    protected TextView mLocationNameLabel;
    protected EditText mLocationNameInput;
    protected TextView mLocationDescLabel;
    protected EditText mLocationDescInput;
    protected TextView mLocationCategoryLabel;
    protected Spinner mLocationCategorySpinner;
    protected Button mConfirmButton;
    protected Button mCancelButton;
    protected Button mEditButton;

    //vars
    protected Activity mActivity;
    protected Context mContext;
    protected MainActivityState mMainActivityState;
    protected LocationLog mLocationLog;
    protected AlertDialog mCurrentDialog;
    protected Location mLocation;

    public LocationFormDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context);
        mActivity = activity;
        mContext = context;
        mMainActivityState = mainActivityState;
        mLocationLog = LocationLog.GetInstance();
        mLocation = location;
        mMainActivityState.setSelectedLocation(location);
        ShowLocationDialog(context, activity);
    }

    private void ShowLocationDialog(Context context, Activity activity)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(CreateLocationForm(activity));
        builder.setCancelable(true);
        mCurrentDialog =  builder.create();
        mCurrentDialog.show();
    }

    private View CreateLocationForm(Activity activity)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_location_form, activity.findViewById(R.id.location_form_root));

        mEditButton = view.findViewById(R.id.location_form_editCoord);
        mLocationFormTitle = view.findViewById(R.id.location_form_title);
        mLocationNameInput = view.findViewById(R.id.location_form_nameInput);
        mLocationNameLabel = view.findViewById(R.id.location_form_nameLabel);
        mLocationDescInput = view.findViewById(R.id.location_form_descInput);
        mLocationDescLabel = view.findViewById(R.id.location_form_descLabel);
        mLocationCategorySpinner = view.findViewById(R.id.location_form_categoryInput);
        mLocationCategoryLabel = view.findViewById((R.id.location_form_categoryLabel));
        mCancelButton = view.findViewById(R.id.location_form_cancelBtn);
        mConfirmButton = view.findViewById(R.id.location_form_confirmBtn);

        mEditButton.setVisibility(View.GONE);
        mConfirmButton.setOnClickListener(HandleConfirm());
        mCancelButton.setOnClickListener(HandleCancel());

        return view;
    }

    protected boolean FormVerification()
    {
        String locationName =  mLocationNameInput.getText().toString();
        String locationDescription =  mLocationDescInput.getText().toString();
        Category locationCategory =  (Category)mLocationCategorySpinner.getSelectedItem();

        if(!VerifyTextInput(mLocationNameInput, mLocationNameLabel, "Name", 5,30))
            return false;
        else if(!VerifyTextInput(mLocationDescInput, mLocationDescLabel,"Description", 10,150))
            return false;
       else if(locationCategory == null){
            String errorMessage =  "Category* Pick One";
            mLocationCategoryLabel.setText(errorMessage);
            mLocationCategoryLabel.setTextColor(getColor(getContext(), R.color.Crimson));
            return false;
        }

        return true;
    }

    public boolean VerifyTextInput(EditText textInput, TextView textLabel, String textName, int min, int max)
    {
        String textValue = textInput.getText().toString();

        if(!Entre(textValue, max, min)){
            String errorMessage = textName + "*Must be between " + String.valueOf(min) +" and "+ String.valueOf(max) +" characters.";
            textLabel.setText(errorMessage);
            textLabel.setTextColor(getColor(getContext(),R.color.Crimson));
            return false;
        }else{
            textLabel.setText("Name");
            textLabel.setTextColor(getColor(getContext(),R.color.GreyDarker));
            return true;
        }
    }

    public void ApplyChangeToLocation()
    {
        mLocation.setName(mLocationNameInput.getText().toString());
        mLocation.setDescription(mLocationDescInput.getText().toString());
        mLocation.setCategory((Category)mLocationCategorySpinner.getSelectedItem());
    }

    public abstract void InitiateLocationInputs();

    public abstract View.OnClickListener HandleConfirm();

    public abstract View.OnClickListener HandleCancel();

}
