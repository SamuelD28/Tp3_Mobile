package com.samdube.tp3_mobile.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.samdube.tp3_mobile.R;

/**
 * Activity used to handle the interaction between two fragments
 */
public abstract class DualFragmentActivity extends AppCompatActivity {

    /**
     * Function that sets the current top fragment to a new fragment
     *
     * @param newFragment New Fragment used to set the top fragment
     */
    protected void setTopFragment(Fragment newFragment) {
        setFragment(R.id.top_fragment, newFragment);
    }

    /**
     * Function that sets the current main fragment to a new fragment
     *
     * @param newFragment New fragment used to set the main fragment
     */
    protected void setMainFragment(Fragment newFragment) {
        setFragment(R.id.main_fragment, newFragment);
    }

    /**
     * Function that sets a targeted ressource id with a new fragment
     *
     * @param resId       Target ressource id to set the fragment
     * @param newFragment New fragment used to set the fragment
     */
    private void setFragment(int resId, Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(resId);
        if (fragment == null) {
            fragment = newFragment;
            fm.beginTransaction()
                    .add(resId, fragment)
                    .commit();
        } else {
            fm.beginTransaction()
                    .replace(resId, newFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
