package com.headquarter.com.headquarter.activity.activity;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectionDB extends AsyncTask<Void, Void, Void> {

    private String dbname = "headquarter";
    private String url = "jdbc:mysql://db4free.net:3306/"+dbname+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String user = "desmond";
    private String passwd = "123456789";

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,passwd);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM campo");

            rs.next();
            System.out.println(rs.getString(2));
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
