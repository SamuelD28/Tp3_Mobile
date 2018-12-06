package com.samdube.tp3_mobile.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.samdube.tp3_mobile.R;

public abstract class DualFragmentActivity extends AppCompatActivity {

    public void setTopFragment(Fragment newFragment){
        setFragment(R.id.top_fragment,newFragment);
    }

    public void setMainFragment(Fragment newFragment){
        setFragment(R.id.main_fragment,newFragment);
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
        setContentView(R.layout.activity_main);
    }

}
