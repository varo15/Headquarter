package com.headquarter.com.headquarter.activity.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsFragment;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsRegisteredFragment;
import com.headquarter.com.headquarter.activity.activity.fragment.ProfileFragment;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

import java.sql.SQLException;
import java.sql.Statement;

public class BottomNavigationViewActivity extends AppCompatActivity {

    final Fragment fragment1 = new EventsFragment();
    final Fragment fragment2 = new EventsRegisteredFragment();
    final Fragment fragment3 = new ProfileFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public static Statement statement;
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

//        this.getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_placeholder, fragment1, "1").commit();

        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.execute();

        Toast.makeText(this, "Hackeando el servidor...espere", Toast.LENGTH_LONG).show();


    }

    public void showFragment(Fragment fragmentName) {
        fragmentManager.beginTransaction().hide(active).show(fragmentName).commit();
        active = fragmentName;
    }

    @Override
    protected void onDestroy() {
        try {
            BottomNavigationViewActivity.statement.close();
            ConnectionDB.conn.close();
            System.out.println("Conexion cerrada");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexion en BootnNav");
        }
        super.onDestroy();
    }

    boolean doubleBackToExitPressedOnce = false;


    @Override

    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Pulsa atras otra vez para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
