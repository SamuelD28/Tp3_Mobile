package com.samdube.tp3_mobile.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Model.Location.Category;

public class SpinnerCategoryAdapter {

    private ArrayAdapter<Category> mArrayAdapter;

    public SpinnerCategoryAdapter(Context context)
    {
        mArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, Category.values());
    }

    public ArrayAdapter<Category> getArrayAdapter() {
        return mArrayAdapter;
    }

    public int getCategoryPosition(Category category)
    {
        return mArrayAdapter.getPosition(category);
    }
}
