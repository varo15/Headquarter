package com.headquarter.com.headquarter.activity.activity.others;

import android.os.AsyncTask;

import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB extends AsyncTask<Void, Void, Statement> {

    private String dbname = "headquarter";
    private String url = "jdbc:mysql://db4free.net:3306/"+dbname+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String user = "desmond";
    private String passwd = "123456789";
    public static Connection conn;
    private Statement statement;


    @Override
    protected Statement doInBackground(Void... params) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,passwd);
            statement = conn.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected void onPostExecute(Statement statement) {
        //System.out.println("Conexion jdbc: " + connection);
        BottomNavigationViewActivity.statement = statement;
    }
}