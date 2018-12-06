package com.samdube.tp3_mobile.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class DualFragmentActivity extends AppCompatActivity {

    public void setTopFragment(Fragment newFragment){
        setFragment(R.id.top_container,newFragment);
    }

    public void setMainFragment(Fragment newFragment){
        setFragment(R.id.main_container,newFragment);
    }

    private void setFragment(int resId, Fragment newFragment){
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(resId);

        if (fragment == null){
            fragment = newFragment;
            fm.beginTransaction()
                    .add(resId,fragment)
                    .commit();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dual_fragment_activity);
    }

}
