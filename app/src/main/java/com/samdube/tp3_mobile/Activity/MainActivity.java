package com.samdube.tp3_mobile.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samdube.tp3_mobile.Fragment.LocationsListFragment;
import com.samdube.tp3_mobile.Fragment.MapFragment;
import com.samdube.tp3_mobile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager(); //Fragment transactino to add the fragment inside the home view pager.
        fragmentManager.beginTransaction().replace(R.id.main_fragment, new MapFragment()).commit(); //The default fragment for the home fragment is the task list
        fragmentManager.beginTransaction().replace(R.id.top_fragment, new LocationsListFragment()).commit();
    }
}
