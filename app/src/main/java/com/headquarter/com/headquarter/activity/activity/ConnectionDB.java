package com.headquarter.com.headquarter.activity.activity;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionDB extends AsyncTask<Void, Void, Void> {

    private String dbname = "headquarter";
    private String url = "jdbc:mysql://db4free.net:3306/"+dbname+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String user = "desmond";
    private String passwd = "123456789";
    private Connection conn = null;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,passwd);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection closeConnection() throws SQLException {
        conn.close();
        return conn;
    }

    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(url, user, passwd);
        return conn;
    }

}