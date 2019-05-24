package com.headquarter.com.headquarter.activity.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsFragment;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsRegisteredFragment;
import com.headquarter.com.headquarter.activity.activity.fragment.ProfileFragment;

public class BottomNavigationViewActivity extends AppCompatActivity {

    final Fragment fragment1 = new EventsFragment();
    final Fragment fragment2 = new EventsRegisteredFragment();
    final Fragment fragment3 = new ProfileFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = fragment1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(fragment1);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(fragment2);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(fragment3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navView = findViewById(R.id.mTextMessage);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new ConnectionDB().execute();

        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment1, "1").commit();

    }

    public void showFragment(Fragment fragmentName) {
        fragmentManager.beginTransaction().hide(active).show(fragmentName).commit();
        active = fragmentName;
    }
}