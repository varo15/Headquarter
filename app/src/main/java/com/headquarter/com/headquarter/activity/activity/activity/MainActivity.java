package com.headquarter.com.headquarter.activity.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.headquarter.R;


public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });*/

    }



}
