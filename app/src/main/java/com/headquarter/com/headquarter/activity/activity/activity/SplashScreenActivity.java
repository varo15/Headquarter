package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

public class SplashScreenActivity extends AppCompatActivity {
    public static FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new ConnectionDB().execute();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        hideVirtualButtons();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (firebaseUser != null) {
                    Intent menuIntent = new Intent(SplashScreenActivity.this, BottomNavigationViewActivity.class);
                    startActivity(menuIntent);
                } else {
                    Intent loginIntent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                    startActivity(loginIntent);
                }

                finish();
            }
        }, 3000);


    }

    private void hideVirtualButtons() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}