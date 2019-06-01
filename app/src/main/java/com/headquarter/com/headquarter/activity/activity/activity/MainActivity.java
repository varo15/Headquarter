package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsFragment;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity  {

    private Statement statement;
    private ResultSet resultSet;
    private Button button;
    private String [] query;
    private String sql = "SELECT * FROM jugador";

    private FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ConnectionDB().execute();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Intent menuIntent = new Intent(this, BottomNavigationViewActivity.class);
            startActivity(menuIntent);
        }else {
            Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
            MainActivity.this.startActivity(loginIntent);
        }
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


    @Override
    protected void onDestroy() {
        try {
            BottomNavigationViewActivity.statement.close();
            ConnectionDB.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexion en MainActivity");
        }
        super.onDestroy();
    }
}
