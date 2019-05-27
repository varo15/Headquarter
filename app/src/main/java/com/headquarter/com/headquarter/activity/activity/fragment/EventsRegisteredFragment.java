package com.headquarter.com.headquarter.activity.activity.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.AdapterRecycler;
import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsRegisteredFragment extends Fragment {

    ArrayList<String> listDatos;
    RecyclerView recycler;
    private ResultSet resultSet;
    private String sql;

    public EventsRegisteredFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events_registered, container, false);
        recycler = rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        listDatos = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            listDatos.add("Dato " + i);
        }

        AdapterRecycler adapter = new AdapterRecycler(listDatos);
        recycler.setAdapter(adapter);

        return rootView;
    }

    private class ProfileTask extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {


            try {

                Statement statement = BottomNavigationViewActivity.connection.createStatement();
                resultSet = statement.executeQuery(sql);
                resultSet.next();


            } catch (SQLException e) {
                System.out.println("CAGADA");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {



        }
    }

}
