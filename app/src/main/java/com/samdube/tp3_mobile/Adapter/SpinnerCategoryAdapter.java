package com.samdube.tp3_mobile.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.samdube.tp3_mobile.Model.Location;
import com.samdube.tp3_mobile.R;

public class SpinnerCategoryAdapter {

    public static ArrayAdapter<Location.Category> CreateAdapter(Context context)
    {
        return new ArrayAdapter<>(context, R.layout.spinner_item, Location.Category.values());
    }

}
