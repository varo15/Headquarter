package com.headquarter.com.headquarter.activity.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.headquarter.R;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity  {

    private Statement statement;
    private ResultSet resultSet;
    private Button button;
    private String [] query;
    private String sql = "SELECT * FROM jugador";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });

    }


    @Override
    protected void onDestroy() {
        try {
            BottomNavigationViewActivity.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexion en MainActivity");
        }
        super.onDestroy();
    }
}
