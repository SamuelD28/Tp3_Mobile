package com.samdube.tp3_mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samdube.tp3_mobile.Interface.IApplicationState;
import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Interface.IApplicationState.*;

public class LocationEditFragment extends Fragment {

    private IApplicationState mApplicationState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_edit, getActivity().findViewById(R.id.locations_edit_root));

        mApplicationState = (IApplicationState)getActivity();

        Button cancelBtn = view.findViewById(R.id.cancel_Btn);
        cancelBtn.setOnClickListener(v -> mApplicationState.HandleModeStateChange(Mode.INFO));

        return view;
    }
}
