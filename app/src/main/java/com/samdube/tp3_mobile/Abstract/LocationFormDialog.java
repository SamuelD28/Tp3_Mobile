package com.samdube.tp3_mobile.Abstract;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.Model.LocationLog;
import com.samdube.tp3_mobile.R;

import static android.support.v4.content.ContextCompat.getColor;
import static com.samdube.tp3_mobile.Model.Location.Category;
import static com.samdube.tp3_mobile.Utils.Validation.Between;


/**
 * Astract class that holds the common methods and properties for all
 * the location dialog form (Add, Edit).
 * This class used the layout fragment_location_form for his UI.
 */
public abstract class LocationFormDialog extends AlertDialog {

    //UI Variable
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
    protected ImageButton mCloseButton;

    //Logic Variable
    protected MainActivityState mMainActivityState; //Activity state used to modify the current application state
    protected LocationLog mLocationLog;             //Location log to interact with the location in the database
    protected AlertDialog mCurrentDialog;           //Hold the current alert dialog.
    protected Location mLocation;                   //Location used to populate the form.

    /**
     * Base constructtor for a location form dialog
     *
     * @param context           Context of the application
     * @param activity          Activity that instiated the Dialog
     * @param mainActivityState Activity State used for manipulating the application state
     * @param location          Location used for populating fields. Create an empty location for a Add form.
     */
    public LocationFormDialog(Context context, Activity activity, MainActivityState mainActivityState, Location location) {
        super(context);
        mMainActivityState = mainActivityState;
        mLocationLog = LocationLog.GetInstance(getContext());
        mLocation = location;
        mMainActivityState.setSelectedLocation(location);
        ShowLocationDialog(context, activity);
    }


    /**
     * Function that display the alert dialog inside the application
     *
     * @param context  Context of the activity
     * @param activity Activity that instantiated the dialog
     */
    private void ShowLocationDialog(Context context, Activity activity) {
        //Dialog builder used to instantiate the dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(CreateLocationForm(activity));
        builder.setCancelable(true);
        mCurrentDialog = builder.create();
        mCurrentDialog.show();
    }

    /**
     * Function that selects all the required UI elements from the view
     * needed to handle the logic of the form
     *
     * @param activity Activity that instantiated the dialog
     * @return the populated form dialog
     */
    private View CreateLocationForm(Activity activity) {
        //Inflation of the view inside the application
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_location_form, activity.findViewById(R.id.location_form_root));

        //Selection of all the UI Elements
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
        mCloseButton = view.findViewById(R.id.location_form_closeBtn);

        //Creation of all the needed listeners
        mEditButton.setVisibility(View.GONE);
        mConfirmButton.setOnClickListener(HandleConfirm());
        mCancelButton.setOnClickListener(HandleCancel());
        mCloseButton.setOnClickListener(HandleClose());

        return view;
    }

    /**
     * Function that handle the close button click inside the form
     *
     * @return the created listener
     */
    private View.OnClickListener HandleClose() {
        return v -> {
            mCurrentDialog.dismiss();
        };
    }

    /**
     * Function that handles the verification of the form inputs
     * before submitting them.
     *
     * @return true if all the inputs pass the verification
     */
    protected boolean FormVerification() {
        //Raw values extracted from the inputs
        String locationName = mLocationNameInput.getText().toString();
        String locationDescription = mLocationDescInput.getText().toString();
        Category locationCategory = (Category) mLocationCategorySpinner.getSelectedItem();

        //Verification of the inputs
        if (!VerifyTextInput(mLocationNameInput, mLocationNameLabel, "Name", 5, 30))
            return false;
        else if (!VerifyTextInput(mLocationDescInput, mLocationDescLabel, "Description", 10, 150))
            return false;
        else if (locationCategory == null) {
            String errorMessage = "Category* Pick One";
            mLocationCategoryLabel.setText(errorMessage);
            mLocationCategoryLabel.setTextColor(getColor(getContext(), R.color.Crimson));
            return false;
        }

        return true;
    }

    /**
     * Function that handle the verification of a single EditText Input
     *
     * @param textInput EditText input to verify
     * @param textLabel Matching TextView label used for displaying errors
     * @param textName  Name of the input. *Need to find a way to remove this
     * @param min       Minimum length of the text input
     * @param max       Maximum length of the text input
     * @return true if the verification passed
     */
    private boolean VerifyTextInput(EditText textInput, TextView textLabel, String textName, int min, int max) {
        //Raw input value
        String textValue = textInput.getText().toString();

        //Verification of the raw input value
        if (!Between(textValue, max, min)) {
            //Feedback of the errors to the user using the label associated with EditText
            String errorMessage = textName + "*Must be between " + String.valueOf(min) + " and " + String.valueOf(max) + " characters.";
            textLabel.setText(errorMessage);
            textLabel.setTextColor(getColor(getContext(), R.color.Crimson));
            return false;
        } else {
            textLabel.setText("Name");
            textLabel.setTextColor(getColor(getContext(), R.color.GreyDarker));
            return true;
        }
    }

    /**
     * Function that apply the changes made inside the form
     * to the current mLocation.
     */
    protected void ApplyChangeToLocation() {
        mLocation.setName(mLocationNameInput.getText().toString());
        mLocation.setDescription(mLocationDescInput.getText().toString());
        mLocation.setCategory((Category) mLocationCategorySpinner.getSelectedItem());
    }

    /**
     * Function that initiate all the inputs with the proper data.
     */
    protected abstract void InitiateLocationInputs();

    /**
     * Function that creates a handler for the confirm click
     * on the form. Attaches to mButtonConfirm
     *
     * @return the created click listener
     */
    protected abstract View.OnClickListener HandleConfirm();

    /**
     * Function that creates a handler for the cancel click
     * on the form. Attaches to mButtonCancel
     *
     * @return the created click listener
     */
    protected abstract View.OnClickListener HandleCancel();

}
