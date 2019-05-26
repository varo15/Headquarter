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


    /*private class Consulta extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                System.out.println("Conection: " + connection);
                statement = connection.createStatement();
                System.out.println("Statement: " + statement);
                resultSet = statement.executeQuery(sql);
                System.out.println("Resultset: " + resultSet);
                resultSet.beforeFirst();
                while (resultSet.next()){
                    String id = resultSet.getString("nombre");
                    System.out.println("id: " + id);
                }



            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: " + connection);
            }
            return null;
        }

    }*/



}
