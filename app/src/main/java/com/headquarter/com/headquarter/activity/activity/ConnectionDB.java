package com.headquarter.com.headquarter.activity.activity;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB extends AsyncTask<Void, Void, Connection> {

    private String dbname = "headquarter";
    private String url = "jdbc:mysql://db4free.net:3306/"+dbname+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String user = "desmond";
    private String passwd = "123456789";
    private Connection conn;


    @Override
    protected Connection doInBackground(Void... params) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,passwd);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    protected void onPostExecute(Connection connection) {
        //System.out.println("Conexion jdbc: " + connection);
        BottomNavigationViewActivity.connection = connection;
    }
}