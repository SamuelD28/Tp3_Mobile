package com.samdube.tp3_mobile.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.samdube.tp3_mobile.R;

import static com.samdube.tp3_mobile.Model.Location.Category;

/**
 * Class that create a new spinner adapter for the category enumeration
 */
public class SpinnerCategoryAdapter {

    //Logic Variables
    private final ArrayAdapter<Category> mArrayAdapter; //Contains the list of all the category enumeration

    /**
     * Constructor for a new spinner category adapter
     *
     * @param context Context of the application that triggered it
     */
    public SpinnerCategoryAdapter(Context context) {
        mArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, Category.values());
    }


    /**
     * @return the spinner adapter
     */
    public ArrayAdapter<Category> getArrayAdapter() {
        return mArrayAdapter;
    }

    /**
     * Function that retrieve an index from the spinner based the category passed
     *
     * @param category Category to find the index
     * @return index of the category inside the adapter
     */
    public int getCategoryPosition(Category category) {
        return mArrayAdapter.getPosition(category);
    }
}
